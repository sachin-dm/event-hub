package com.sbbs.tickets.eventhub.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EqualsAndHashCode
public class Event {
    @Id
    private String id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venueId;
    private String artists;
    private Timestamp eventDate;
}
