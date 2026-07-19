package com.dsy2206.kafkavitalsignsproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling 
public class KafkaVitalSignsProducerMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaVitalSignsProducerMsApplication.class, args);
	}

}
