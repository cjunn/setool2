package com.cjunn.setool.dao.exception;

public class NoIdException  extends RuntimeException {
    public NoIdException() {
    }

    public NoIdException(String msg) {
        super(msg);
    }

    public NoIdException(String msg, Throwable e) {
        super(msg, e);
    }
}

