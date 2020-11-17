package com.example.user.pactTest;

import com.example.user.model.User;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "userService",  url = "http://localhost:8081/", configuration = UserServiceClient.FeignClientConfiguration.class)
public interface UserServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    List<User> getUsers();

    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}")
    User getUser(@PathVariable("userId") Long userId);

    @RequestMapping(method = RequestMethod.PUT, value = "/users/{userId}")
    User updateUser(@PathVariable("userId") Long userId, @RequestBody  User user);

    @Configuration
    class FeignClientConfiguration {
        @Bean
        public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
            return new BasicAuthRequestInterceptor("1001", "test");
        }
    }
}
