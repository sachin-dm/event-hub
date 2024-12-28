package com.sbbs.tickets.eventhub.service;

import com.sbbs.tickets.eventhub.model.Event;
import com.sbbs.tickets.eventhub.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public void saveUser(Event event) {
        eventRepository.saveAndFlush(event);
    }

    public Event getEvent(String eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }

    public Page<Event> listEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }
}
