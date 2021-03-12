package com.cjunn.setool.dao.mybatis.intercept;

import com.cjunn.setool.utils.Reflections;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Plugin;

import java.lang.reflect.Proxy;

/**
 * @ClassName InterceptorUtils
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/8 21:54
 * @Version
 */
public class InterceptorUtils {
    private InterceptorUtils() {
    }

    public static boolean isRoutingStatementHandlerProxy(Object target) {
        return target instanceof RoutingStatementHandler || Proxy.isProxyClass(target.getClass()) && Reflections.getFieldValue(Proxy.getInvocationHandler(target), "target") instanceof RoutingStatementHandler;
    }

    public static boolean isExecutorHandlerProxy(Object target) {
        return target instanceof Executor || Proxy.isProxyClass(target.getClass()) && Reflections.getFieldValue(Proxy.getInvocationHandler(target), "target") instanceof Executor;
    }

    public static boolean isPreparedStatementHandler(Object target) {
        return target instanceof PreparedStatementHandler || Proxy.isProxyClass(target.getClass()) && Reflections.getFieldValue(Proxy.getInvocationHandler(target), "target") instanceof PreparedStatementHandler;
    }

    public static boolean isCachingExecutor(Object target) {
        return target instanceof CachingExecutor || Proxy.isProxyClass(target.getClass()) && Reflections.getFieldValue(Proxy.getInvocationHandler(target), "target") instanceof CachingExecutor;
    }

    public static RoutingStatementHandler getRoutingStatementHandler(Object target) {
        if (target instanceof RoutingStatementHandler) {
            return (RoutingStatementHandler)target;
        } else if (target instanceof StatementHandler && Proxy.isProxyClass(target.getClass())) {
            Plugin var1 = (Plugin) Reflections.getFieldValue(target, "h");
            return (RoutingStatementHandler)Reflections.getFieldValue(var1, "target");
        } else {
            return null;
        }
    }
}
