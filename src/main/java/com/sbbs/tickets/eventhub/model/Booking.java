package com.sbbs.tickets.eventhub.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event eventId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private EventUser eventUserId;
    private String bookingStatus;
    private Timestamp created;
    private Timestamp updated;
}
