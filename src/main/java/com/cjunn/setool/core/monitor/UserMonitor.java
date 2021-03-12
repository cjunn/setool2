package com.cjunn.setool.core.monitor;

import com.cjunn.setool.core.user.UserContextHolder;
import com.cjunn.setool.utils.ConcurrentFixQueue;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName UserMonitor
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/8 14:17
 * @Version
 */
public class UserMonitor<I> {
    private Class tClz;
    protected UserMonitor(Class tClz){
        this.tClz=tClz;
    }
    public void addMonitorInfo(I i){
        UserMonitorFactory.addMonitorInfo(tClz,UserMonitorFactory.getUserCode(),i);
    }
    public <I> List<I> getDisplayBoard(){
        return UserMonitorFactory.getDisplayBoard(tClz,UserMonitorFactory.getUserCode());
    }


}
