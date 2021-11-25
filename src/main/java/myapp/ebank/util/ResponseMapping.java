package myapp.ebank.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.time.LocalDateTime;

/**
 * The type Response mapping.
 */
public class ResponseMapping {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private static final ObjectMapper jsonMapper = new ObjectMapper();


    /**
     * Api response object node.
     *
     * @param httpStatus the http status
     * @param message    the message
     * @param path       the path
     * @param data       the data
     * @return the object node
     * @throws ParseException the parse exception
     */
    public static ObjectNode apiResponse(HttpStatus httpStatus, String message, String path, Object data) throws ParseException {
        ObjectNode responseJson = jsonMapper.createObjectNode();
        responseJson.put("timestamp", String.valueOf(LocalDateTime.now()));
        responseJson.put("httpStatus", String.valueOf(httpStatus));
        responseJson.put("message:", message);
        responseJson.put("path", path);
        if (null != data) {
            responseJson.putPOJO("data", data);
        }
        return responseJson;
    }


}

