package com.sbbs.tickets.eventhub.controller;

import com.sbbs.tickets.eventhub.model.Event;
import com.sbbs.tickets.eventhub.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEvent(@PathVariable String eventId) {
        Event event = eventService.getEvent(eventId);
        return event != null ? ResponseEntity.ok(event) : ResponseEntity.notFound().build();
    }

    @GetMapping("/list")
    public Page<Event> listEvents(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "50") int size,
                                  @RequestParam(defaultValue = "id") String sortBy,
                                  @RequestParam(defaultValue = "true") boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return eventService.listEvents(pageable);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> createEvent(@RequestBody Event event) {
        eventService.saveEvent(event);
        return ResponseEntity.created(URI.create("/events/" + event.getId())).build();
    }
}