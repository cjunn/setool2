package com.cjunn.setool.dao.fluent.support.mybatis;

import com.cjunn.setool.dao.mybatis.intercept.OptimisticLockInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MybsGlobalCfg
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/12 16:18
 * @Version
 */
@Configuration
@ConditionalOnClass({MybatisAutoConfiguration.class})
public class MybsGlobalCfg {
    @Bean
    MybsFluentSupport mybsFluentSupport(SqlSessionTemplate sqlSessionTemplate){
        sqlSessionTemplate.getConfiguration().addInterceptor(new MybsResultMap());
        sqlSessionTemplate.getConfiguration().addMapper(MybsMapper.class);
        return new MybsFluentSupport(sqlSessionTemplate.getMapper(MybsMapper.class));
    }
}
