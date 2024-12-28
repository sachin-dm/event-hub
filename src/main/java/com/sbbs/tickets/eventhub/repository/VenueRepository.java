package com.sbbs.tickets.eventhub.repository;

import com.sbbs.tickets.eventhub.model.Event;
import com.sbbs.tickets.eventhub.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, String> {

}
