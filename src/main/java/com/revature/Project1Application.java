package com.revature;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;

@SpringBootApplication
public class Project1Application {

	@Bean
	public TimedAspect timedAspect(MeterRegistry registry) {
		return new TimedAspect(registry);
	}
	
	@Bean
	public MeterFilter customizeDistributionConfig() {
	    return new MeterFilter() {
	        @Override
	        public DistributionStatisticConfig configure(Meter.Id id, DistributionStatisticConfig config) {
	            return DistributionStatisticConfig.builder().expiry(Duration.ofHours(1)).build().merge(config);
	        }
	    };
	}

	public static void main(String[] args) {

		SpringApplication.run(Project1Application.class, args);

	}

}
