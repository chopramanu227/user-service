package com.example.user.controller;

import com.example.user.aspect.LogEntryExit;
import com.example.user.model.User;
import com.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @LogEntryExit
    @GetMapping
    public List<User> fetchAllUsers() {
       return userService.fetchAllUsers() ;
    }

    @LogEntryExit
    @GetMapping("/{id}")
    public User fetchUser(@PathVariable @Min(value=1000, message="Invalid User Id") @Max(value=10000, message="Invalid User Id")  Long id) {
        return userService.fetchUser(id);
    }

    @LogEntryExit
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id,user);
    }
}
