package com.sbbs.tickets.eventhub.controller;

import com.sbbs.tickets.eventhub.model.EventUser;
import com.sbbs.tickets.eventhub.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private final UserService userService = Mockito.mock(UserService.class);

    private UserController userController;

    @BeforeEach
    public void init() {
        userController = new UserController(userService);
    }
    @Test
    void getUser() throws ParseException {
        // Arrange
        EventUser eventUser = buildEventUser();

        when(userService.getUser(anyString())).thenReturn(eventUser);

        //Act
        ResponseEntity<EventUser> actual = userController.getUser("user1");

        //Assert
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertNotNull(actual.getBody());
        assertEquals(eventUser, actual.getBody());
    }

    @Test
    void getInvalidEvent() {
        // Arrange
        when(userService.getUser(anyString())).thenReturn(null);

        //Act
        ResponseEntity<EventUser> actual = userController.getUser("event1");

        //Assert
        assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
        assertNull(actual.getBody());
    }

    @Test
    void listEvents() throws ParseException {
        int size = 3;
        // Arrange
        Page<EventUser> eventList = buildNEventUsers(5, size);
        when(userService.listUsers(any())).thenReturn(eventList);

        //Act
        Page<EventUser> actual = userController.listUsers(1, 3, "id",true);

        //Assert
        assertNotNull(actual);
        assertEquals(size, actual.getContent().size());
        assertEquals(5, actual.getTotalElements());
    }

    @Test
    void createEvent() throws ParseException {
        // Arrange
        EventUser user = buildEventUser();
        doNothing().when(userService).saveUser(user);

        //Act
        ResponseEntity<Void> result = userController.createUser(user);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(List.of("/users/srt10"), result.getHeaders().get("Location"));
    }

    // ------------------------- Private supporting methods -------------------------
    private static EventUser buildEventUser() throws ParseException {
        return EventUser.builder()
                .id("srt10")
                .name("Sachin Tendulkar")
                .dob(new SimpleDateFormat("dd-MM-yyyy").parse("24-04-1973"))
                .emailAddress("srt10@gmail.com")
                .gender("Male")
                .mobileNumber(9810101010L)
                .username("srt10")
                .build();
    }

    private static Page<EventUser> buildNEventUsers(int n, int size) throws ParseException {
        List<EventUser> eventUsers = new ArrayList<>();
        for (int operand = 0; operand < n; operand++) {
            EventUser eventUser = EventUser.builder()
                    .id("user" + operand)
                    .name("Rock User " + operand)
                    .dob(new SimpleDateFormat("dd-MM-yyyy").parse("24-04-1973"))
                    .emailAddress(String.format("user%s@gmail.com", operand))
                    .gender("Male")
                    .mobileNumber(9810101010L)
                    .username("user" + operand)
                    .build();
            eventUsers.add(eventUser);
        }

        int pageNumber = 0; // Page index (zero-based)
        // Number of items per page
        PageRequest pageRequest = PageRequest.of(pageNumber, size);

        // Sub-list for the current page
        int start = (int) pageRequest.getOffset();
        int end = Math.min(start + size, eventUsers.size());
        List<EventUser> pagedSubList = eventUsers.subList(start, end);

        // Create Page<String> using PageImpl
        return new PageImpl<>(pagedSubList, pageRequest, eventUsers.size());
    }
}