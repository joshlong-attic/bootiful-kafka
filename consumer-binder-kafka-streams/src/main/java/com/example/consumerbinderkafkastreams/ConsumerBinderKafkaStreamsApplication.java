package com.example.consumerbinderkafkastreams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.kstream.annotations.KStreamProcessor;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.Arrays;

@SpringBootApplication
@EnableBinding(KStreamProcessor.class)
public class ConsumerBinderKafkaStreamsApplication {

	@SendTo("output")
	@StreamListener("input")
	public KStream<?, String> process(KStream<?, String> input) {
		return input
				.flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
				.map((key, word) -> new KeyValue<>(word, word))
				.groupByKey(Serdes.String(), Serdes.String())
				.count(TimeWindows.of(5000), "store-name")
				.toStream()
				.map((w, c) -> new KeyValue<>(null, "Count for " + w.key() + ": " + c));
	}

	public static void main(String[] args) {
		SpringApplication.run(ConsumerBinderKafkaStreamsApplication.class, args);
	}
}
