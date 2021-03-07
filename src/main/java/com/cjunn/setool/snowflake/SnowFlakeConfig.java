package com.cjunn.setool.snowflake;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.beans.ConstructorProperties;

/**
 * @ClassName SnowFlakeConfig
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/7 16:23
 * @Version
 */
@Configuration
public class SnowFlakeConfig {
   @Bean
   @ConditionalOnProperty(name="setool.snowflake.node.type",havingValue = "property")
   PropertyNodeGetter propertyNodeGetter(Environment environment){
      final String PROPERTY_KEY = "setool.snowflake.nodenum";
      Integer requiredProperty = environment.getProperty(PROPERTY_KEY, Integer.class);
      if(requiredProperty==null){
         throw new IllegalStateException("请设置 "+PROPERTY_KEY+" 属性");
      }
      return new PropertyNodeGetter(requiredProperty);
   }

   @Bean
   @ConditionalOnBean(NodeGetter.class)
   SnowFlakeGenerator snowFlakeGenerator(NodeGetter nodeGetter){
      return new SnowFlakeGenerator(nodeGetter);
   }


}
