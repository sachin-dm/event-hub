package com.sbbs.tickets.eventhub.integration.service;

import com.sbbs.tickets.eventhub.integration.BaseIntegrationTest;
import com.sbbs.tickets.eventhub.model.Event;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class EventServiceTest extends BaseIntegrationTest {

    @Test
    public void getEventTest() {
        Optional<Event> eventOptional = eventRepository.findById("event1");
        assertTrue(eventOptional.isPresent());

        Event event = eventOptional.get();
        assertEquals("event1", event.getId());
        assertEquals("Rock Concert", event.getName());
        assertNotNull(event.getVenueId());
        assertEquals("Grand Hall", event.getVenueId().getName());
    }

    @Test
    public void getInvalidEventTest() {
        Optional<Event> eventOptional = eventRepository.findById("event-invalid");
        assertTrue(eventOptional.isEmpty());
    }
}
