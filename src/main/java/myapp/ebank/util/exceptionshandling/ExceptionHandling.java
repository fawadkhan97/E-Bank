package myapp.ebank.util.exceptionshandling;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandling {
    private static final Logger log = LogManager.getLogger(ExceptionHandling.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({RuntimeException.class, MissingRequestValueException.class})
    public ResponseEntity<ErrorResponse> inputValidationException(Exception e, HttpServletRequest request) {
        log.info("some error has occurred see logs for more details ....\n " + e.toString() + request.getRequestURI());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }


}

