package com.example.demo.controller;

import com.example.demo.model.dao.UserDao;
import com.example.demo.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor()
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;

    @GetMapping()
    public ResponseEntity<List<UserDao>> getUsers() {
        return ResponseEntity.ok(usersService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDao> getUser(@PathVariable Long id) {
        UserDao userDao = usersService.findUserById(id);
        if (userDao != null) {
            return ResponseEntity.ok(userDao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add-user")
    public ResponseEntity<UserDao> createUser(@RequestBody UserDao userDao) {
        UserDao newUser = usersService.addUser(userDao);
        if (newUser != null) {
            return ResponseEntity.ok(userDao);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<UserDao> deleteUser(@PathVariable Long id) {
        UserDao deletedUser = usersService.deleteUser(id);
        return ResponseEntity.ok(deletedUser);
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<UserDao> updateUser(@PathVariable Long id, @RequestBody UserDao userDao) {
        UserDao updatedUser = usersService.updateUser(userDao);
        if (updatedUser != null) {
            return ResponseEntity.ok(userDao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
