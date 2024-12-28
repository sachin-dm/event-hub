package com.sbbs.tickets.eventhub.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Builder
@Entity(name="event_user")
@NoArgsConstructor
@AllArgsConstructor
public class EventUser {
    @Id
    @Column(name = "id")
    private String id;
    private String username;
    private String name;
    private Date dob;
    private String gender;
    private String emailAddress;
    private Long mobileNumber;
}
