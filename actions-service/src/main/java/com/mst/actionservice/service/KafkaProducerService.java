
package com.mst.actionservice.service;

import com.mst.actionservice.kafka.ActionEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final String TOPIC = "actions_to_process";

    @Autowired
    private KafkaTemplate<String, ActionEvent> kafkaTemplate;

    public void sendAction(ActionEvent actionEvent){

        String key=actionEvent.ownerId()!=null ? actionEvent.ownerId().toString() : null;
        kafkaTemplate.send(TOPIC,key,actionEvent);

    }
}
