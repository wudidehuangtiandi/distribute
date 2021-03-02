package com.example.springboot_kafka_demo.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName : KafkaInitialConfiguration
 * @Description : 自定义配置
 * @Author : GC
 * @Date: 2020-08-24 14:51
 */
@Configuration
public class KafkaInitialConfiguration {

    //这个 项目以启动就会创建,这个配置也能用来修改topic分区数量，不过只能扩大不能减小，方法是第二个参数直接扩大 重启即可
    //创建TopicName为topic.quick.initial的Topic并设置分区数为8以及副本数为1
    @Bean
    public NewTopic initialTopic() {
        return new NewTopic("topic.quick.initial",8, (short) 1 );
    }



    //以下两个本质上是配置个AdminClient，可以用来创建topic,代码见测试类2
    //注册KafkaAdmin和AdminClient两个Bean
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> props = new HashMap<>();
        //配置Kafka实例的连接地址
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        KafkaAdmin admin = new KafkaAdmin(props);
        return admin;
    }

    @Bean
    public AdminClient adminClient() {
        return AdminClient.create(kafkaAdmin().getConfig());
    }
}