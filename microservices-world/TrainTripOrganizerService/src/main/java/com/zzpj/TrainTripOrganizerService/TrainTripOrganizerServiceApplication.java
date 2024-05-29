package com.zzpj.TrainTripOrganizerService;

import com.zzpj.openapi.api.StationsApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import java.util.List;

@EnableDiscoveryClient
@SpringBootApplication
public class TrainTripOrganizerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainTripOrganizerServiceApplication.class, args);
    }

    @Bean
    public StationsApi stationsApi() {
        return new StationsApi();
    }

    @Bean
    public CommandLineRunner commandLineRunner(StationsApi stationsApi) {
        return args -> {
            System.out.println(stationsApi.getApiClient().getBasePath());
            stationsApi.getStations().forEach(System.out::println);
        };
    }

    @Bean
    public ServiceInstanceListSupplier serviceInstanceListSupplier() {
        return new TrainTripsManagerSupplier("train-trips-service");
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

class TrainTripsManagerSupplier implements ServiceInstanceListSupplier {

    private final String serviceId;

    TrainTripsManagerSupplier(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String getServiceId() {
        return serviceId;
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return Flux.just(
                List.of(
                        new DefaultServiceInstance("001", serviceId, "localhost", 8021, false),
                        new DefaultServiceInstance("002", serviceId, "localhost", 8022, false),
                        new DefaultServiceInstance("003", serviceId, "localhost", 8023, false)
                )
        );
    }
}

@RestController
class TrainTripsOrganizerController {
    private final RestTemplate restTemplate;

    @Value("${spring.application.name}")
    private String applicationName;

    TrainTripsOrganizerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/info")
    public String getHello() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://train-trips-service/hello/" + applicationName, String.class);
        return forEntity.getBody();
    }

    @Value("${config.server.demo}")
    private String message;

    @GetMapping("/getMessage")
    public String getMessage() {
        return "Property value: " + message;
    }
}