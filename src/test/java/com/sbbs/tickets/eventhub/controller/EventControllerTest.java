package com.sbbs.tickets.eventhub.controller;

import com.sbbs.tickets.eventhub.model.Event;
import com.sbbs.tickets.eventhub.model.Venue;
import com.sbbs.tickets.eventhub.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
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
        Event event = Event.builder()
                .id("event1")
                .name("Rock Event")
                .description("Test")
                .artists("MJ")
                .venueId(Venue.builder()
                        .id("venue1")
                        .build())
                .eventDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        when(eventService.getEvent(anyString())).thenReturn(event);

        //Act
        ResponseEntity<Event> actual = eventController.getEvent("event1");

        //Assert
        assertEquals(HttpStatus.OK, actual.getStatusCode());
    }

    @Test
    void listEvents() {
    }

    @Test
    void createEvent() {
    }
}