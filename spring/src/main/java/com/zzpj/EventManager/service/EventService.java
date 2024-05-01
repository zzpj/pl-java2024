package com.zzpj.EventManager.service;

import com.zzpj.EventManager.model.Event;
import com.zzpj.EventManager.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public List<Event>  getAllEvents() {
        return eventRepository.findAll();
    }

    public void addEvent(Event event) {
        eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id.intValue());
    }

    public Event getEvent(Long id) {
        return eventRepository.findById(id.intValue()).orElse(null);
    }

    public Event updateEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllFreeEvents() {
        return eventRepository.findAllFreeEvents(0.0);
    }
}
