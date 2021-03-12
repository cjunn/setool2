package com.cjunn.setool.core.monitor;

/**
 * @ClassName IUserMonitorGetter
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/8 15:04
 * @Version
 */
public interface IUserMonitorGetter<I> {
    UserMonitor<I> getUserMonitor();
}
