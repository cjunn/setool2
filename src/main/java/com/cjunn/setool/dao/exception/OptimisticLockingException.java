package com.cjunn.setool.dao.exception;

public class OptimisticLockingException extends RuntimeException {
    public OptimisticLockingException() {
    }

    public OptimisticLockingException(String msg) {
        super(msg);
    }

    public OptimisticLockingException(String msg, Throwable e) {
        super(msg, e);
    }
}
