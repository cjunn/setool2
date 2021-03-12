package com.cjunn.setool.dao.mybatis.intercept;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.Properties;

/**
 * @ClassName OptimisticLockInterceptor
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/8 21:50
 * @Version
 */
@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}
), @Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
), @Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
)})
public class OptimisticLockInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (InterceptorUtils.isRoutingStatementHandlerProxy(invocation.getTarget())) {
            return invocation.proceed();
        } else if (!InterceptorUtils.isExecutorHandlerProxy(invocation.getTarget())) {
            return invocation.proceed();
        } else {

        }
        return null;
    }

    @Override
    public Object plugin(Object o) {
        return !InterceptorUtils.isExecutorHandlerProxy(o) && !InterceptorUtils.isRoutingStatementHandlerProxy(o) ?
                o :
                Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
