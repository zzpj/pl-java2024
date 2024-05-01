package com.zzpj.EventManager;

import com.zzpj.EventManager.model.Event;
import com.zzpj.EventManager.service.EventService;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class EventConfig {

//    @Bean
//    public EventService eventService() {
//        List<Event> eventList = new ArrayList<>();
//        eventList.add(new Event(1L, "event-name", "desc", 120.0d,
//                LocalDateTime.of(2023, 12, 12, 20, 00)));
//        eventList.add(new Event(2L, "second-event-name", "desc", 100.0d,
//                LocalDateTime.of(2023, 12, 24, 10, 00)));
//        return new EventService(eventList);
//    }

    @Value("${info.app.name}")
    private String appName;
    @Value("${info.app.description}")
    private String appDescription;
    @Value("${info.app.version}")
    private String appVersion;


    @Bean
    @Profile("prod")
    public OpenAPI openAPI() {

        Contact contact = new Contact();
        contact.name("Zbyszko on PROD");
        contact.email("your@email.com");

        Info info = new Info();
        info.setTitle(appName);
        info.setDescription(appDescription);
        info.setVersion(appVersion);
        info.setContact(contact);

        OpenAPI openAPI = new OpenAPI();
        openAPI.setInfo(info);
        return openAPI;
    }


    @Bean
    @Profile("!prod")
    public OpenAPI devOpenAPI() {

        Contact contact = new Contact();
        contact.name("Zbyszko not on PRoD");
        contact.email("your@email.com");

        Info info = new Info();
        info.setTitle(appName);
        info.setDescription(appDescription);
        info.setVersion(appVersion);
        info.setContact(contact);

        OpenAPI openAPI = new OpenAPI();
        openAPI.setInfo(info);
        return openAPI;
    }
}
