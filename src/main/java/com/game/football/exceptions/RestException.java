package com.game.football.exceptions;

public class RestException extends RuntimeException {
    private Integer code;
    private Object data;

    public RestException(Integer code, String message, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    public RestException(String message) {
        super(message);
    }

    public Integer getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }
}
