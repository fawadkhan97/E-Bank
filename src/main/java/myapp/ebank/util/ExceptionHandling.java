package myapp.ebank.util;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import myapp.ebank.service.CurrencyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandling {
    private static final Logger log = LogManager.getLogger(ExceptionHandling.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({javax.validation.ConstraintViolationException.class, InvalidFormatException.class, HttpMessageNotReadableException.class, MissingRequestHeaderException.class, MissingPathVariableException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<Object> inputValidationException(Exception e) {

        log.info("some error has occurred see logs for more details ....\n ", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

    }


}

