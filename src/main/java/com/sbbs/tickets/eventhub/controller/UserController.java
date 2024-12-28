package com.sbbs.tickets.eventhub.controller;

import com.sbbs.tickets.eventhub.model.EventUser;
import com.sbbs.tickets.eventhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<EventUser> getUser(@PathVariable String userId) {
        EventUser eventUser = userService.getUser(userId);
        if (eventUser != null) {
            return ResponseEntity.ok(eventUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/list")
    public Page<EventUser> listUsers(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "5") int size,
                                     @RequestParam(defaultValue = "id") String sortBy,
                                     @RequestParam(defaultValue = "true") boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return userService.listUsers(pageable);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> createUser(@RequestBody EventUser eventUser) {
        userService.saveUser(eventUser);
        return ResponseEntity
                .created(URI.create("/users/" + eventUser.getId()))
                .build();
    }
}
