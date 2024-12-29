package com.sbbs.tickets.eventhub.controller;

import com.sbbs.tickets.eventhub.model.Event;
import com.sbbs.tickets.eventhub.model.Venue;
import com.sbbs.tickets.eventhub.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class EventControllerTest {

    private final EventService eventService = Mockito.mock(EventService.class);

    private EventController eventController;

    @BeforeEach
    public void init() {
        eventController = new EventController(eventService);
    }
    @Test
    void getEvent() {
        // Arrange
        Event event = buildEvent();

        when(eventService.getEvent(anyString())).thenReturn(event);

        //Act
        ResponseEntity<Event> actual = eventController.getEvent("event1");

        //Assert
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertNotNull(actual.getBody());
        assertEquals(event, actual.getBody());
    }

    @Test
    void getInvalidEvent() {
        // Arrange
        when(eventService.getEvent(anyString())).thenReturn(null);

        //Act
        ResponseEntity<Event> actual = eventController.getEvent("event1");

        //Assert
        assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
        assertNull(actual.getBody());
    }

    @Test
    void listEvents() {
        int size = 3;
        // Arrange
        Page<Event> eventList = buildNEvents(5, size);
        when(eventService.listEvents(any())).thenReturn(eventList);

        //Act
        Page<Event> actual = eventController.listEvents(1, 3, "id",true);

        //Assert
        assertNotNull(actual);
        assertEquals(size, actual.getContent().size());
        assertEquals(5, actual.getTotalElements());
    }

    @Test
    void createEvent() {
        // Arrange
        Event event = buildEvent();
        doNothing().when(eventService).saveEvent(event);

        //Act
        ResponseEntity<Void> result = eventController.createEvent(event);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(List.of("/events/event1"), result.getHeaders().get("Location"));
    }

    // ------------------------- Private supporting methods -------------------------
    private static Event buildEvent() {
        return Event.builder()
                .id("event1")
                .name("Rock Event")
                .description("Test")
                .artists("MJ")
                .venueId(Venue.builder()
                        .id("venue1")
                        .build())
                .eventDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }

    private static Page<Event> buildNEvents(int n, int size) {
        List<Event> events = new ArrayList<>();
        for (int operand = 0; operand < n; operand++) {
            Event build = Event.builder()
                    .id("event" + operand)
                    .name("Rock Event " + operand)
                    .description("Test " + operand)
                    .artists("MJ " + operand)
                    .venueId(Venue.builder()
                            .id("venue" + operand)
                            .build())
                    .eventDate(Timestamp.valueOf(LocalDateTime.now()))
                    .build();
            events.add(build);
        }

        int pageNumber = 0; // Page index (zero-based)
        // Number of items per page
        PageRequest pageRequest = PageRequest.of(pageNumber, size);

        // Sub-list for the current page
        int start = (int) pageRequest.getOffset();
        int end = Math.min(start + size, events.size());
        List<Event> pagedSubList = events.subList(start, end);

        // Create Page<String> using PageImpl
        return new PageImpl<>(pagedSubList, pageRequest, events.size());
    }
}