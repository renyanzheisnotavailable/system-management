package com.example.kafka;



import com.example.api.domain.OperLog;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class KafkaService {
    @Resource
    private  KafkaTemplate<String, OperLog> kafkaTemplate;

    public  void send(String id, OperLog operLog) {
        kafkaTemplate.send("log", id,operLog);
    }



}
