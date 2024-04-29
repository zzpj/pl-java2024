package com.example.demo.model.dto;

public class UserDto {
    // not implemented. As you can see in the dao package,
    // UserDao class has some fields which should not be passed to a client e.g. password or address.
    // Before UserDao is sent to the client, it has to be mapped to UserDto (Data Access Object -> Data Transfer Object)
    // When dao is mapped to dto, such data as passwords are ignored.
    // The topic is not related to Spring Security itself, but is important when creating secure systems
}
