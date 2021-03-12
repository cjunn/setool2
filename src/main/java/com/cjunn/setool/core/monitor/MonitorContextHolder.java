package com.cjunn.setool.core.monitor;

import com.cjunn.setool.utils.ConcurrentFixQueue;

import java.util.List;
import java.util.Map;

/**
 * @ClassName UserMonitorContextHolder
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/8 13:30
 * @Version
 */
public class MonitorContextHolder {
    public static <I> List<I> getDisplayBoard(Class<?> clz){
        return UserMonitorFactory.getDisplayBoard(clz, UserMonitorFactory.getUserCode());
    }

    public static Map<Class, ConcurrentFixQueue> getDisplayBoard(){
        return UserMonitorFactory.getDisplayBoard(UserMonitorFactory.getUserCode());
    }

}
