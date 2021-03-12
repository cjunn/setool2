package com.cjunn.setool.dao.fluent.support.mybatis;

import com.cjunn.setool.core.model.BaseModel;
import com.cjunn.setool.core.model.JpaAnnoModeInfo;
import com.cjunn.setool.dao.fluent.builder.frame.TableFrame;
import com.cjunn.setool.dao.fluent.support.JpaInfoUtils;
import com.cjunn.setool.dao.mybatis.intercept.ModelSQLProvider;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName MybsResultMap
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/12 15:45
 * @Version
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
})
public class MybsResultMap implements Interceptor {
    private static final String FIELD_NAME ="resultMaps";
    private static final Map<String,ResultMap> RESULT_MAPPING_CACHE=new ConcurrentHashMap<>();
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        if (!(invocation.getTarget() instanceof Executor)) {
            return invocation.proceed();
        }
        if(!isMatchIds(ms.getId())){
            return invocation.proceed();
        }
        String rMapId = ms.getResultMaps().iterator().next().getId();
        ResultMap cacheRMap = RESULT_MAPPING_CACHE.get(rMapId);
        if(cacheRMap!=null){
            return invocation.proceed();
        }
        TableFrame tableFrame = ((TableFrame) ((Map) invocation.getArgs()[1]).get("param"));
        rMapId = new StringBuilder(rMapId).append(" ").append(tableFrame.getClz().getName()).toString();
        ResultMap newRm = RESULT_MAPPING_CACHE.computeIfAbsent(rMapId, (id) -> {
            List<ResultMapping> resultMappings = new ArrayList<>();
            JpaInfoUtils.of(tableFrame.getClz(), (fieldClz, propertyName, columnName) -> {
                resultMappings.add(new ResultMapping.Builder(ms.getConfiguration(), propertyName, columnName, fieldClz).build());
            });
            return new ResultMap.Builder(ms.getConfiguration(), id, tableFrame.getClz(), resultMappings).build();
        });
        Field field = ReflectionUtils.findField(MappedStatement.class,FIELD_NAME);
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, ms, Collections.singletonList(newRm));
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private final static Set<String> MAPPER_IDS=new HashSet<>();
    static{
        StringBuffer stringBuffer=new StringBuffer();
        Method[] methods = MybsMapper.class.getMethods();
        for(Method method:methods){
            stringBuffer.append(method.getDeclaringClass().getName());
            stringBuffer.append(".");
            stringBuffer.append(method.getName());
            MAPPER_IDS.add(stringBuffer.toString());
            stringBuffer.setLength(0);
        }
        System.err.println(MAPPER_IDS);
    }
    private boolean isMatchIds(String id){
        return MAPPER_IDS.contains(id);
    }




}
