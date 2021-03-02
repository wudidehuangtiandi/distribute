package com.example.springboot_kafka_demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName : ConsumerDemo
 * @Description : 消费者示范
 * @Author : GC
 * @Date: 2020-08-24 14:30
 */
@Component
public class ConsumerDemo {
    //指定监听的topic，当前消费者组id
    @KafkaListener(topics = {"test"}, groupId = "receiver")
    public void registryReceiver(ConsumerRecord<Integer, String> integerStringConsumerRecords) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++==");
        System.out.println(integerStringConsumerRecords.value());
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++==");

    }
}
