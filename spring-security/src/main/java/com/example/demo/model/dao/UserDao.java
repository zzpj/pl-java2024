package com.example.demo.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDao {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String zipCode;
}
