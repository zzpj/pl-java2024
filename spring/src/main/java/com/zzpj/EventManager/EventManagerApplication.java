package com.zzpj.EventManager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class EventManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EventManagerApplication.class, args);
    }
}

@RestController
class TestController {

    @Value("${spring.application.name}")
    private String applicationName;


    @Operation(
            summary = "Get Service Name and say hello to user",
            description = "Get Service Name and say hello to user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error") })
    @GetMapping("/hello/{name}")
    public String getHelloMessage(@PathVariable String name) {
        return "Hello World" + name + " from app " + applicationName;
    }
}