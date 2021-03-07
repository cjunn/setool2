package com.cjunn.setool.dao.handler;

import com.cjunn.setool.core.model.Handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName @{NAME}
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/7 15:31
 * @Version
 */

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Handler(CreateTimeRecordHandler.class)
public @interface CreateTimeRecord {
}
