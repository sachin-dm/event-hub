package com.sbbs.tickets.eventhub.repository;

import com.sbbs.tickets.eventhub.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, String> {

}
