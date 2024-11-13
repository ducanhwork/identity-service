package dev.pdanh.hello_spring.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorCode {
    USER_NOT_FOUND(1001,"User not found", HttpStatus.NOT_FOUND),
    USER_EXISTED(1002,"User already existed", HttpStatus.BAD_REQUEST),



    INVALID_USERNAME(1003,"Invalid username",HttpStatus.BAD_REQUEST),
    INVALID_LENGTH_PASSWORD(1004,"Password must be at least 8 characters.",HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1005,"Minimum eight characters, at least one letter, one number and one special character.",HttpStatus.BAD_REQUEST),
    INVALID_PHONENUMBER(1006,"Invalid phone number!",HttpStatus.BAD_REQUEST),
    INVALID_KEY(1007,"Invalid key!", HttpStatus.BAD_REQUEST),


    UNCATEGORIZED(9999,"Uncategorized error",HttpStatus.INTERNAL_SERVER_ERROR),
    PARSE_EXCEPTION(9998,"Parse exception",HttpStatus.BAD_REQUEST),


    DATETIME_PARSE_EXCEPTION(2000,"Date and time parse error",HttpStatus.BAD_REQUEST),


    UNAUTHORIZED(4000,"Unauthorized",HttpStatus.FORBIDDEN),
    UNAUTHENTICATED(3000,"Unauthenticated error",HttpStatus.UNAUTHORIZED),
    ;


    ErrorCode(int code,String message,HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = statusCode;
    }
    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }
    public int getCode() {
        return code;
    }
    public String getMessage() { return message; }
}
