package com.roncoo.eshop.cache.kafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName KafkaConsumer
 * @Description TODO
 * @Author wuwenxiang
 * @Date 2019-07-16 20:20
 * @Version 1.0
 **/
public class KafkaConsumer implements Runnable{

    private ConsumerConnector consumerConnector;

    private String topic;

    public KafkaConsumer(String topic) {
        this.topic = topic;
        this.consumerConnector = Consumer.createJavaConsumerConnector(createConsumerConfig());
    }



    private static ConsumerConfig createConsumerConfig(){
        Properties properties = new Properties();
        properties.put("zookeeper.connect","192.168.1.10:2181,192.168.1.11:2181,192.168.1.12:2181");
        properties.put("group.id","eshop-cache-group");
        properties.put("zookeeper.session.timeout.ms","40000");
        properties.put("zookeeper.sync.time.ms","200");
        properties.put("auto.commit.interval.ms","1000");
        return new ConsumerConfig(properties);
    }

    @Override
    public void run() {
        Map<String,Integer> topicCountMap = new HashMap<>();
        //设置几个线程
        topicCountMap.put(topic,1);
        //每个topic对应多个kafkastream
        Map<String, List<KafkaStream<byte[],byte[]>>> consumerMap = consumerConnector.createMessageStreams(topicCountMap);

        List<KafkaStream<byte[],byte[]>> streams = consumerMap.get(topic);
        //针对每个kafkastream创建一个线程去处理里面的数据
        for(final KafkaStream stream:streams){
            new Thread(new KafkaMessageProcessor(stream)).start();
        }

    }
}
