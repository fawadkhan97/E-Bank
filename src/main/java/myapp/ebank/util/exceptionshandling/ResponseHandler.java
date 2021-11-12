package myapp.ebank.util.exceptionshandling;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ResponseHandler {
    // customizing timestamp serialization format
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private int code;

    private HttpStatus status;

    private String message;

    private String path;

    private Object data;


    public ResponseHandler(HttpStatus httpStatus, String message, String path) {
        timestamp = LocalDateTime.now();
        this.code = httpStatus.value();
        this.status = httpStatus;
        this.path = path;
        this.message = message;
    }

    public ResponseHandler(HttpStatus httpStatus, Object data, String path) {
        timestamp = LocalDateTime.now();
        this.code = httpStatus.value();
        this.status = httpStatus;
        this.path = path;
        this.data = data;
    }




}