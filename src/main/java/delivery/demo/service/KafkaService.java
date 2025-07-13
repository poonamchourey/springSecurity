package delivery.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private Logger logger = LoggerFactory.getLogger(KafkaService.class);

    public boolean updateLocation(String Location){
        this.kafkaTemplate.send("topicname", Location);
        this.logger.info("poonam message produced");
        return true;

    }
}
