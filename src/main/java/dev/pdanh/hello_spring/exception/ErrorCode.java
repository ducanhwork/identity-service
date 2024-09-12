package dev.pdanh.hello_spring.exception;



public enum ErrorCode {
    USER_NOT_FOUND(1001,"User not found"),
    USER_EXISTED(1002,"User already existed"),



    INVALID_USERNAME(1003,"Invalid username"),
    INVALID_LENGTH_PASSWORD(1004,"Password must be at least 8 characters."),
    INVALID_PASSWORD(1005,"Minimum eight characters, at least one letter, one number and one special character."),
    INVALID_PHONENUMBER(1006,"Invalid phone number!"),
    INVALID_KEY(1007,"Invalid key!"),


    UNCATEGORIZED(9999,"Uncategorized error"),
    PARSE_EXCEPTION(9998,"Parse exception"),


    DATETIME_PARSE_EXCEPTION(2000,"Date and time parse error"),



    UNAUTHENTICATED(3000,"Unauthenticated error"),
    ;


    ErrorCode(int code,String message) {
        this.code = code;
        this.message = message;
    }
    private int code;
    private String message;
    public int getCode() {
        return code;
    }
    public String getMessage() { return message; }
}
