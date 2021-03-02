package com.example.springboot_kafka_demo;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Arrays;

@SpringBootTest
class SpringbootKafkaDemoApplicationTests {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    /*我们从到到尾都没有创建过"topic.quick.demo"这个Topic，这是因为KafkaTemplate在发送的时候就已经帮我们完成了创建的操作，
    所以我们不需要主动创建"topic.quick.demo"这个Topic，而是交由KafkaTemplate去完成。但这样也出现了问题，
    这种情况创建出来的Topic的Partition(分区)数是默认值（3），副本数也是默认值（1）*/
    @Test
    void contextLoads() {
        kafkaTemplate.send("test", 0,"12","1222");
    }


    //手动创建topic，也可以用来获取topic,用adminClient.describeTopics,结果.all()即可获得个map
    @Autowired
    private AdminClient adminClient;

    @Test
    public void testCreateTopic() throws InterruptedException {
        NewTopic topic = new NewTopic("topic.quick.initial2", 1, (short) 1);
        adminClient.createTopics(Arrays.asList(topic));
        Thread.sleep(1000);
    }

}
