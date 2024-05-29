package com.zzpj.TrainTripEcoSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class TrainTripEcoSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainTripEcoSystemApplication.class, args);
	}

}
