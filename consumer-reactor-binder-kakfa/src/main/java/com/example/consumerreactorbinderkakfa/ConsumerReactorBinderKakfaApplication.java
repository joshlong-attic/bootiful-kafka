package com.example.consumerreactorbinderkakfa;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@EnableBinding(Sink.class)
@SpringBootApplication
public class ConsumerReactorBinderKakfaApplication {

	@Log
	@Component
	public static class Consumer {

		@StreamListener
		public void process(@Input(Sink.INPUT) Flux<String> greetings) {
			greetings
					.subscribe(x -> log.info("processing incoming message " + x ));
		}
	}


	public static void main(String[] args) {
		SpringApplication.run(ConsumerReactorBinderKakfaApplication.class, args);
	}
}
