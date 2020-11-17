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

/**
 * Rest controller class to provide API endpoints for user information.
 */
@RestController
@Validated
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Method returns list of users present in database.
     * <br/>
     * <code>{@link LogEntryExit}</code> annotation will ensure entry and exit logging of the method.
     *
     * @return
     */
    @LogEntryExit
    @GetMapping
    public List<User> fetchAllUsers() {
        return userService.fetchAllUsers();
    }

    /**
     * Method returns user based on the requested user id.
     * <br/>
     * <code>{@link LogEntryExit}</code> annotation will ensure entry and exit logging of the method.
     *
     * @return
     */
    @LogEntryExit
    @GetMapping("/{id}")
    public User fetchUser(@PathVariable
                          @Min(value = 1000, message = "User Id can't be less than 1000")
                          @Max(value = 99999, message = "User Id can't be greter than 99999") Integer id) {
        return userService.fetchUser(id.longValue());
    }

    /**
     * Method allows to update User information based on user id.
     * <br/>
     * <code>{@link LogEntryExit}</code> annotation will ensure entry and exit logging of the method.
     *
     * @return
     */
    @LogEntryExit
    @PutMapping("/{id}")
    public User updateUser(@PathVariable
                           @Min(value = 1000, message = "User Id can't be less than 1000")
                           @Max(value = 99999, message = "User Id can't be greter than 99999") Long id,
                           @RequestBody User user) {
        return userService.updateUser(id, user);
    }
}
