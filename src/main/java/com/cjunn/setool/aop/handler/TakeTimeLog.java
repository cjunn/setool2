package com.cjunn.setool.aop.handler;

import com.cjunn.setool.aop.Aop;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Aop(handler=TakeTimeLogHandler.class)
public @interface TakeTimeLog {
}
