package com.elleined.rt_messaging_api;

import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import com.elleined.rt_messaging_api.service.message.MessageService;
import net.datafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableCaching
public class RtMessagingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RtMessagingApiApplication.class, args);
	}

	@Bean
	public Faker faker() {
		return new Faker();
	}
}
