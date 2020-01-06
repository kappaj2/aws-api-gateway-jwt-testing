package za.co.ajk.apitest.exceptions;

import za.co.ajk.apitest.JWTTokenValidationStatusEnum;

public class JWTTokenException extends Exception {

    private int httpStatus;

    public JWTTokenException(int httpStatus, JWTTokenValidationStatusEnum errorReason, String message) {
        super(errorReason.getValidationMessage() + message);
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}