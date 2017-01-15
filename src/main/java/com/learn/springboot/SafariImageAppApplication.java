package com.learn.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.endpoint.MetricReaderPublicMetrics;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.repository.InMemoryMetricRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SafariImageAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafariImageAppApplication.class, args);

	}

	@Bean
	public InMemoryMetricRepository inMemoryMetricRepository(){
		return new InMemoryMetricRepository();
	}

	@Bean
	public PublicMetrics publicMetrics(InMemoryMetricRepository inMemoryMetricRepository){
		return new MetricReaderPublicMetrics(inMemoryMetricRepository);
	}
}
