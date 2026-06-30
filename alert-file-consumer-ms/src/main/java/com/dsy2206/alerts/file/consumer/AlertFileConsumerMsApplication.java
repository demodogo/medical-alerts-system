package com.dsy2206.alerts.file.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(excludeName = {
        "org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration",
        "org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration",
        "org.springframework.boot.jdbc.autoconfigure.DataSourceInitializationAutoConfiguration"
})
public class AlertFileConsumerMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlertFileConsumerMsApplication.class, args);
	}

}
