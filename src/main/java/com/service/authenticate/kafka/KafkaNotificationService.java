package com.service.authenticate.kafka;

import com.service.authenticate.dto.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaNotificationService {

    private static final String TOPIC = "ms-notification";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, LoginRequest> kafkaLoginTemplate;

    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC, message);
    }

    public void sendMessage(LoginRequest message) {
        kafkaLoginTemplate.send(TOPIC, message);
    }
}
