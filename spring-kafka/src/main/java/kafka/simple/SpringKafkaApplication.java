package kafka.simple;

import lombok.Data;
import lombok.extern.java.Log;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableConfigurationProperties(SpringKafkaApplication.KafkaDemoProperties.class)
public class SpringKafkaApplication {


	@Data
	@ConfigurationProperties("demo")
	public static class KafkaDemoProperties {

		private String topic;
	}

	@Component
	public static class SimpleKafkaRunner implements ApplicationRunner {

		private final KafkaDemoProperties properties;
		private final KafkaTemplate<String, String> kafkaTemplate;

		public SimpleKafkaRunner(
				KafkaDemoProperties properties,
				KafkaTemplate<String, String> kafkaTemplate) {
			this.properties = properties;
			this.kafkaTemplate = kafkaTemplate;
		}

		@Override
		public void run(ApplicationArguments args) throws Exception {
			this.kafkaTemplate.send(this.properties.getTopic(), "Hello, " + System.currentTimeMillis());
		}
	}

	@Log
	@Component
	public static class SimpleKakfkaListener {

		@KafkaListener(topics = "${demo.topic}")
		public void process(ConsumerRecord<String, String> request) {
			log.info("new message: " + request.key() + ":" + request.value());
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaApplication.class, args);
	}
}
