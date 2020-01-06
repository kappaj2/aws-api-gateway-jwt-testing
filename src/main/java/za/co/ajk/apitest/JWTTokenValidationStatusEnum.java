package za.co.ajk.apitest;

public enum JWTTokenValidationStatusEnum {

    NOT_VALID_JSON_WEB_TOKEN("Not a valid json web token"),
    TOKEN_EXPIRED("Token has expired");

    private String validationMessage;

    JWTTokenValidationStatusEnum(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    public String getValidationMessage() {
        return validationMessage;
    }
}
