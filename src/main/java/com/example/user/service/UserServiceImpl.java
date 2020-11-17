package com.example.user.service;

import com.example.user.aspect.LogEntryExit;
import com.example.user.exception.EmptyPayloadException;
import com.example.user.exception.RecordNotFoundException;
import com.example.user.model.Address;
import com.example.user.model.User;
import com.example.user.persistence.entity.AddressEntity;
import com.example.user.persistence.entity.UserEntity;
import com.example.user.persistence.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service class provides business logic implementation for different user details Api operations.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves all the users from database.
     * <br/>
     * Transforms DB entities to model objects.
     * <br/>
     * <code>{@link LogEntryExit}</code> annotation will ensure entry and exit logging of the method.
     * @return
     */
    @LogEntryExit
    @Override
    public List<User> fetchAllUsers() {
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .map(userEntity -> User.builder()
                        .title(userEntity.getTitle())
                        .firstName(userEntity.getFirstName())
                        .lastName(userEntity.getLastName())
                        .gender(userEntity.getGender())
                        .address(Address.builder()
                                .street(userEntity.getAddress().getStreet())
                                .city(userEntity.getAddress().getCity())
                                .state(userEntity.getAddress().getState())
                                .postcode(userEntity.getAddress().getPostcode())
                                .build())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Retrieves  user from database using userId.
     * <br/>
     * Transforms DB entities to model objects.
     * <br/>
     * <code>{@link LogEntryExit}</code> annotation will ensure entry and exit logging of the method.
     * <br/>
     * Throws <code>{@link RecordNotFoundException}</code> exception if user is not found in database.
     * @return
     */
    @LogEntryExit
    @Override
    public User fetchUser(Long userId) {
        return userRepository.findById(userId)
                .map(userEntity -> User.builder()
                        .title(userEntity.getTitle())
                        .firstName(userEntity.getFirstName())
                        .lastName(userEntity.getLastName())
                        .gender(userEntity.getGender())
                        .address(Address.builder()
                                .street(userEntity.getAddress().getStreet())
                                .city(userEntity.getAddress().getCity())
                                .state(userEntity.getAddress().getState())
                                .postcode(userEntity.getAddress().getPostcode())
                                .build())
                        .build())
                .orElseThrow(()-> new RecordNotFoundException("User not found."));
    }

    /**
     * Method saves new user information to database.
     * <br/>
     * Transforms DB entities to model objects.
     * <br/>
     * <code>{@link LogEntryExit}</code> annotation will ensure entry and exit logging of the method.
     * <br/>
     * Throws <code>{@link RecordNotFoundException}</code> exception if user is not found in database.
     * @return
     */
    @LogEntryExit
    @Override
    public User updateUser(Long userId, User user) {
        if (user == null) {
            throw new EmptyPayloadException("Update request triggered with empty payload.");
        }
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("User not found."));
        // update existing user entity with new user details
        populateUserDetails(user, userEntity);
        //save data to db
        UserEntity updateUserEntity = userRepository.save(userEntity);
        //transform entity to domain object.
        User.UserBuilder userBuilder = User.builder()
                .title(updateUserEntity.getTitle())
                .firstName(updateUserEntity.getFirstName())
                .lastName(updateUserEntity.getLastName())
                .gender(updateUserEntity.getGender());

        if (null != updateUserEntity.getAddress()) {
            userBuilder
                    .address(Address.builder()
                            .street(updateUserEntity.getAddress().getStreet())
                            .city(updateUserEntity.getAddress().getCity())
                            .state(updateUserEntity.getAddress().getState())
                            .postcode(updateUserEntity.getAddress().getPostcode())
                            .build());

        }
        return userBuilder.build();
    }

    private void populateUserDetails(User user, UserEntity userEntity) {
        userEntity.setTitle(user.getTitle());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setGender(user.getGender());
        //set address details
        if(user.getAddress()!=null){
            AddressEntity addressEntity= userEntity.getAddress()!=null? userEntity.getAddress() : new AddressEntity();
            addressEntity.setStreet(user.getAddress().getStreet());
            addressEntity.setCity(user.getAddress().getCity());
            addressEntity.setState(user.getAddress().getState());
            addressEntity.setPostcode(user.getAddress().getPostcode());
        }
    }
}
