package com.example.consumerbinderkakfa;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@EnableBinding(Sink.class)
@SpringBootApplication
public class ConsumerBinderKakfaApplication {

	@Log
	@Component
	public static class Consumer {

		@StreamListener(Sink.INPUT)
		public void process(String greetings) {
			log.info("processing incoming message " + greetings + ".");
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(ConsumerBinderKakfaApplication.class, args);
	}
}
