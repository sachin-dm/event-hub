package com.sbbs.tickets.eventhub.service;

import com.sbbs.tickets.eventhub.model.EventUser;
import com.sbbs.tickets.eventhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(EventUser eventUser) {
        userRepository.saveAndFlush(eventUser);
    }

    public EventUser getUser(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public Page<EventUser> listUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
