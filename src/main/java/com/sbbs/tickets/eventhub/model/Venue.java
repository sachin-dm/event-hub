package com.sbbs.tickets.eventhub.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Venue {
    @Id
    private String id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String phone;
    private String web;
    private String email;
}
