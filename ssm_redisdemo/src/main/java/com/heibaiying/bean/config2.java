package com.heibaiying.bean;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * 用来生工厂
 *
 * @author GC
 * @date 2020年 01月28日 18:10:25
 */
public class config2 extends LettuceConnectionFactory {
    private RedisSentinelConfiguration  redisSentinelConfiguration;
    private LettuceClientConfiguration  lettuceClientConfiguration;

    public void setRedisSentinelConfiguration(RedisSentinelConfiguration redisSentinelConfiguration) {
        this.redisSentinelConfiguration = redisSentinelConfiguration;
    }

    public void setLettuceClientConfiguration(LettuceClientConfiguration lettuceClientConfiguration) {
        this.lettuceClientConfiguration = lettuceClientConfiguration;
    }

    public LettuceConnectionFactory getLettuceConnectionFactory(){
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisSentinelConfiguration, lettuceClientConfiguration);
        return lettuceConnectionFactory;
    }
}
