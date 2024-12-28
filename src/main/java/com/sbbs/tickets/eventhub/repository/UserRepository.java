package com.sbbs.tickets.eventhub.repository;

import com.sbbs.tickets.eventhub.model.EventUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<EventUser, String> {
    Optional<EventUser> findById(String id);

    Page<EventUser> findAll(Pageable pageable);
}
