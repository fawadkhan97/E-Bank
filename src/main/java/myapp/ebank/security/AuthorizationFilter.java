package myapp.ebank.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.SneakyThrows;
import myapp.ebank.model.entity.Users;
import myapp.ebank.service.UserService;
import myapp.ebank.util.ResponseMapping;
import myapp.ebank.util.exceptionshandling.ExceptionHandling;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import static myapp.ebank.constant.SecurityConstants.*;

public class AuthorizationFilter extends BasicAuthenticationFilter {

   @Autowired
    UserService userService;
    public AuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    private static final Logger log = LogManager.getLogger(AuthenticationFilter.class);

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws Exception {
        String token = request.getHeader(HEADER_STRING);
        try {
            if (token != null) {
                // parse the token.
                String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                        .build()
                        .verify(token.replace(TOKEN_PREFIX, ""))
                        .getSubject();

                if (user != null) {
                    UserDetails userDetails = userService.loadUserByUsername(user);
                    // new arraylist means authorities
                    log.info("user is {}", user);
                    return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                }

                return null;
            }

            return null;
        } catch (Exception e) {

            log.info("exception thrown {}...{}..{}", e.getClass(), e.getMessage(), e.getCause());

            ExceptionHandling.jwtException(e, request);
            return null;

        }

    }

    @ExceptionHandler({TokenExpiredException.class, JWTDecodeException.class, JWTVerificationException.class})
    public ResponseEntity<Object> jwtException(Exception e, HttpServletRequest request) throws ParseException {
        log.info("some error has occurred see logs for more details ....parsing exception is {}....{} ", e.toString(), request.getRequestURI());
        return new ResponseEntity<>(ResponseMapping.ApiReponse(HttpStatus.BAD_REQUEST, e.getMessage(), request.getRequestURI(), null), HttpStatus.BAD_REQUEST);
    }
}