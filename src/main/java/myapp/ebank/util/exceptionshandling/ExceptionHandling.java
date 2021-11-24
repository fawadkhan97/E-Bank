package myapp.ebank.util.exceptionshandling;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import myapp.ebank.util.ResponseMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Objects;

@ControllerAdvice
public class ExceptionHandling {
    private static final Logger log = LogManager.getLogger(ExceptionHandling.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public static ResponseEntity<Object> handleException(MethodArgumentNotValidException e, HttpServletRequest request) throws ParseException {
        String errorResult = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorDefaultMessage = error.getDefaultMessage();
            log.info("field is {}, error is {}", fieldName, errorDefaultMessage);
        });
        log.info("an error has occured .{} , {}", e.getClass(), errorResult);
        return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.BAD_REQUEST, errorResult, request.getRequestURI(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ParseException.class, AuthenticationException.class})
    public static ResponseEntity<Object> parsingException(Exception e, HttpServletRequest request) throws ParseException {
        log.info("some error has occurred see logs for more details ....parsing exception is {}..{}..{}", e.getClass(), e.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.BAD_REQUEST, e.getMessage(), request.getRequestURI(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TokenExpiredException.class, JWTDecodeException.class, JWTVerificationException.class})
    public static ResponseEntity<Object> jwtException(Exception e, HttpServletRequest request) throws ParseException {
        log.info("some error has occurred see logs for more details ....parsing exception is {} : {} uri is {} ", e.getClass(), e.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.BAD_REQUEST, e.getMessage(), request.getRequestURI(), null), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({RuntimeException.class, MissingRequestValueException.class, InputMismatchException.class, NonUniqueResultException.class})
    public static ResponseEntity<Object> inputValidationException(Exception e, HttpServletRequest request) throws ParseException {
        log.info("some error has occurred see logs for more details ....general exception is {} : {} uri is {} ", e.getClass(), e.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.BAD_REQUEST, e.getMessage(), request.getRequestURI(), null), HttpStatus.BAD_REQUEST);
    }

}

