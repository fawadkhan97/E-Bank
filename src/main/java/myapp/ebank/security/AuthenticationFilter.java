package myapp.ebank.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import myapp.ebank.model.entity.Roles;
import myapp.ebank.model.entity.Users;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static myapp.ebank.constant.SecurityConstants.EXPIRATION_TIME;
import static myapp.ebank.constant.SecurityConstants.SECRET;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final Logger log = LogManager.getLogger(AuthenticationFilter.class);
    private final AuthenticationManager authenticationManager;


    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        //  setFilterProcessesUrl("/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            Users userCredential = new ObjectMapper()
                    .readValue(req.getInputStream(), Users.class);
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            for (Roles role : userCredential.getRoles()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            }
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userCredential.getUserName(),
                            userCredential.getPassword(),
                            grantedAuthorities
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));

        String body = ((User) auth.getPrincipal()).getUsername() + " " + token;

        log.info("token generated is : {}", body);
        res.getWriter().write(body);
        res.getWriter().flush();
    }
}