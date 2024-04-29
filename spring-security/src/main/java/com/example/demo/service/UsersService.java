package com.example.demo.service;

import com.example.demo.model.dao.UserDao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {
    private List<UserDao> users;

    {
        users = new ArrayList<>() {{
           add(new UserDao(1L, "user_1", "pwd_1", "email_1@example.com", "999_000_000", "address_1", "99-999"));
            add(new UserDao(2L, "user_2", "pwd_2", "email_2@example.com", "999_000_000", "address_2", "99-999"));
            add(new UserDao(3L, "user_3", "pwd_3", "email_3@example.com", "999_000_000", "address_3", "99-999"));
            add(new UserDao(4L, "user_4", "pwd_4", "email_4@example.com", "999_000_000", "address_4", "99-999"));
            add(new UserDao(5L, "user_5", "pwd_5", "email_5@example.com", "999_000_000", "address_5", "99-999"));
        }};
    }

    public List<UserDao> getUsers() {
        return users;
    }

    public UserDao addUser(UserDao userDao) {
        UserDao existingUser = findUserById(userDao.getId());
        if (existingUser == null) {
            users.add(userDao);
            return userDao;
        } else {
            return null;
        }
    }

    public UserDao deleteUser(Long id) {
        UserDao existingUser = findUserById(id);
        users.remove(existingUser);
        return existingUser;
    }

    public UserDao findUserById(Long id) {
        return users
                .stream()
                .filter(user -> user.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    public UserDao updateUser(UserDao userDao) {
        UserDao existingUser = findUserById(userDao.getId());
        if (existingUser != null) {
            users.remove(existingUser);
            users.add(userDao);
            return userDao;
        } else {
            return null;
        }
    }
}
