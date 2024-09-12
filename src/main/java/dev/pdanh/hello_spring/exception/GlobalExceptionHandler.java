package dev.pdanh.hello_spring.exception;

import dev.pdanh.hello_spring.dto.response.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;


@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    //catch theo loai exception

    //catch theo loai exception
    @ExceptionHandler(AppException.class)
    ResponseEntity<APIResponse> appExceptionHandler(AppException e) {
        // tra ve bad req voi body la message cua error
        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(e.getErrorCode().getMessage());
        apiResponse.setCode(e.getErrorCode().getCode());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<APIResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        APIResponse apiResponse = new APIResponse();
        String enumKey = e.getFieldError().getDefaultMessage();
        System.out.println(enumKey);
        ErrorCode errorCode = ErrorCode.INVALID_KEY;

        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException exception) {

        }

        apiResponse.setMessage(errorCode.getMessage());
        apiResponse.setCode(errorCode.getCode());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(DateTimeParseException.class)
    ResponseEntity<APIResponse> dateTimeParseExceptionHandler(DateTimeParseException e) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(ErrorCode.DATETIME_PARSE_EXCEPTION.getMessage());
        apiResponse.setCode(ErrorCode.DATETIME_PARSE_EXCEPTION.getCode());
        return ResponseEntity.badRequest().body(apiResponse);
    }
//    @ExceptionHandler(Exception.class)
//    ResponseEntity<APIResponse> runtimeExceptionHandler(Exception e) {
//        // tra ve bad req voi body la message cua error
//        System.out.println("Exception: " + e.getMessage());
//        APIResponse apiResponse = new APIResponse();
//        apiResponse.setMessage(ErrorCode.UNCATEGORIZED.getMessage());
//        apiResponse.setCode(ErrorCode.UNCATEGORIZED.getCode());
//        return ResponseEntity.badRequest().body(apiResponse);
//    }

}
