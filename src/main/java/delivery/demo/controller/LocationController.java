package delivery.demo.controller;

import delivery.demo.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class LocationController {
    @Autowired
    private KafkaService kafkaService;
    @GetMapping("/hello")
    public String home()
    {
        String str
                = "<html><body><font color=\"green\">"
                + "<h1>WELCOME To GeeksForGeeks</h1>"
                + "</font></body></html>";
        return str;
    }
    @PostMapping("/update")
    public ResponseEntity<?> updateLocation(){
       // kafkaService.updateLocation("2x location");
        return new ResponseEntity<>(Map.of("location updated","succesfully"), HttpStatus.OK);
    }
}
