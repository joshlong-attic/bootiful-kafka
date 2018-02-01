package com.example.producerbinderkakfa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableBinding(Source.class)
public class ProducerBinderKakfaApplication {

	@RestController
	public static class ProducerRestController {

		private final Source source;

		public ProducerRestController(Source source) {
			this.source = source;
		}

		@GetMapping("/hello/{name}")
		public void greet(@PathVariable String name) {
			this.source
					.output()
					.send(MessageBuilder.withPayload("Hello, " + name + "!").build());
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(ProducerBinderKakfaApplication.class, args);
	}
}

