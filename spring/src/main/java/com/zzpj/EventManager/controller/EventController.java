package com.zzpj.EventManager.controller;


import com.zzpj.EventManager.model.Event;
import com.zzpj.EventManager.model.EventErrorResponse;
import com.zzpj.EventManager.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/getAllEvents")
    public List<Event> getAllEvents(){
        return eventService.getAllEvents();
    }

    @GetMapping("/getEvent/{id}")
    public Event getEvent(@PathVariable("id") Long id) {
        return eventService.getEvent(id);
    }


    @Operation(
            summary = "Adds event",
            description = "Adds event to event list")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = EventErrorResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error") })
    @PostMapping("/addEvent")
    public void addEvent(@Valid @RequestBody Event event) {
        eventService.addEvent(event);
    }

    @DeleteMapping("/delete/{id}")
    public void removeEvent(@PathVariable("id") Long id) {
        eventService.deleteEvent(id);
    }

    @PutMapping("update/{id}")
    public Event updateEvent(@PathVariable Long id, @RequestBody Event event) {
        return eventService.updateEvent(event);
    }

    @GetMapping("/getAllFreeEvents")
    public List<Event> getAllFreeEvents(){
        return eventService.getAllFreeEvents();
    }
}
