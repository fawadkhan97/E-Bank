package myapp.ebank.util;


import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private List<ErrorModel> errorMessage;

}

/**
 * This class describe the error object model, which is a simple POJO that contains the rejected filedName, rejectedValue
 * and a messageError.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
class ErrorModel {
    private String fieldName;
    private Object rejectedValue;
    private String messageError;

}
