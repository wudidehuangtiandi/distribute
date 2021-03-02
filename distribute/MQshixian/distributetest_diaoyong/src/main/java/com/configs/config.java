package com.configs;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

/**
 * 静态内部类注入
 *
 * @author GC
 * @date 2020年 01月28日 17:03:25
 */
public class config {

   private  GenericObjectPoolConfig genericObjectPoolConfig=null;

    public void setGenericObjectPoolConfig(GenericObjectPoolConfig genericObjectPoolConfig) {
        this.genericObjectPoolConfig = genericObjectPoolConfig;
    }

    public  LettucePoolingClientConfiguration getLettucePoolingClientConfiguration(){
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder lettucePoolingClientConfigurationBuilder = builder.poolConfig(genericObjectPoolConfig);
        LettucePoolingClientConfiguration build = lettucePoolingClientConfigurationBuilder.build();
        return build;
    }

}
