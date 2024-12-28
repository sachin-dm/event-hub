package com.sbbs.tickets.eventhub.repository;

import com.sbbs.tickets.eventhub.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, String> {

}
