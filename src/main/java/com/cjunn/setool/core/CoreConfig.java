package com.cjunn.setool.core;

import com.cjunn.setool.core.user.IUserLoader;
import com.cjunn.setool.core.user.UserContextHolder;
import com.cjunn.setool.core.util.ContextHolder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {
    @Bean
    ContextHolder contextHolder(){
        return new ContextHolder();
    }

    @Bean
    @ConditionalOnBean(IUserLoader.class)
    UserContextHolder userContextHolder(IUserLoader iUserLoader){
        return new UserContextHolder(iUserLoader);
    }

}
