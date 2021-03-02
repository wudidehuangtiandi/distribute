package com.heibaiying.jedis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sun.tools.jconsole.JConsole;

import java.util.concurrent.TimeUnit;

/**
 * 测试SpringDataRedis（根据springApplication中的配置切换jedis和lettuce集成）
 *
 * @author GC
 * @date 2020年 01月28日 09:00:37
 */
@RunWith(SpringRunner.class)
@ContextConfiguration({"classpath:springApplication.xml"})
public class SpringDataRedisTest {

    @Autowired
    private RedisTemplate stringRedisTemplate;

    @Test
    public void Set() {

        stringRedisTemplate.boundValueOps("name").set("哈哈");
        stringRedisTemplate.boundValueOps("hobby").set("eat");


        //设置K,V，失效时间（要Long类型），时间单位枚举
       //stringRedisTemplate.opsForValue().set("gender","man",10L, TimeUnit.SECONDS);

        Object name = stringRedisTemplate.boundValueOps("name").get();
        System.out.println(name.toString());
        Object hobby = stringRedisTemplate.boundValueOps("hobby").get();
        System.out.println(hobby.toString());
        //查不到会报错java.lang.NullPointerException
      /*  Object gender = stringRedisTemplate.boundValueOps("gender").get();
        System.out.println(gender.toString());*/



    }

}
