package com.cjunn.setool.dao.exception;

public class NoTableException extends RuntimeException {
    public NoTableException() {
    }

    public NoTableException(String msg) {
        super(msg);
    }

    public NoTableException(String msg, Throwable e) {
        super(msg, e);
    }
}
