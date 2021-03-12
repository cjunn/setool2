package com.cjunn.setool.core.monitor;

import com.cjunn.setool.core.user.IUserLoader;
import com.cjunn.setool.core.user.UserContextHolder;
import com.cjunn.setool.core.util.ContextHolder;
import com.cjunn.setool.utils.ConcurrentFixQueue;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName UserMonitorFactory
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/8 14:17
 * @Version
 */
public class UserMonitorFactory {
    private static final int MAX_FIXQUEUE_SIZE = 1000;
    private static final Map<String, Map<Class, ConcurrentFixQueue>> GLOBAL_MONITOR_DATA=new ConcurrentHashMap<>();
    private static IUserLoader iUserLoader;
    public static void setUserLoader(IUserLoader iUserLoader){
        UserMonitorFactory.iUserLoader=iUserLoader;
    }

    protected static String getUserCode(){
        if(iUserLoader!=null){
            return iUserLoader.getUser().getUserCode();
        }
        return UserContextHolder.getUser().getUserCode();
    }

    protected static <R> void addMonitorInfo(Class<?> clz, String userCode , R r){
        GLOBAL_MONITOR_DATA
                .computeIfAbsent(userCode,c->new ConcurrentHashMap<>())
                .computeIfAbsent(clz, (u) -> new ConcurrentFixQueue<R>(MAX_FIXQUEUE_SIZE))
                .add(r);
    }

    protected static <R> List<R> getDisplayBoard(Class clz, String userCode){
        Map<Class, ConcurrentFixQueue> userFixQueue = GLOBAL_MONITOR_DATA.get(userCode);
        if(userFixQueue==null){
            return Collections.emptyList();
        }
        return new LinkedList<>(userFixQueue.get(clz));
    }
    protected static Map<Class, ConcurrentFixQueue> getDisplayBoard(String userCode){
        Map<Class, ConcurrentFixQueue> map = GLOBAL_MONITOR_DATA.get(userCode);
        if(map==null){
            return Collections.emptyMap();
        }
        return new HashMap<>(map);
    }


    public static <I,T> UserMonitor<I> getUserMonitor(Class<T> clz){
        return new UserMonitor(clz);
    }

}
