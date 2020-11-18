package com.example.user.persistence.repository;

import com.example.user.persistence.entity.AddressEntity;
import com.example.user.persistence.entity.UserEntity;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository layer abstracting Spring Data JPA CRUD functions with custom implementations.
 */
@Repository
public class UserDAOImpl implements UserDAO{

    private static final List<UserEntity> userEntities = getUserEntities();

    @Autowired
    private UserRepository userRepository;

    /**
     * Method returns all users present in UserDetail table of db.
     * <br/>
     * If DB operation is failed, method triggers a fallback mechanism using hystrix
     * which returns cached result.
     * @return
     */
    @HystrixCommand(
            commandKey = "fetchAllUsersFromDB",
            fallbackMethod = "fetchAllUsersFromCache")
    @Override
    public Iterable<UserEntity> fetchAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Method returns  user present in UserDetail table of db using userId.
     * <br/>
     * If DB operation is failed, method triggers a fallback mechanism using hystrix
     * which returns cached result.
     * @return
     */
    @HystrixCommand(
            commandKey = "fetchUserByIdFromDB",
            fallbackMethod = "fetchUserByIdFromCache")
    @Override
    public Optional<UserEntity> fetchUserByIdWithFallback(Long userId) {
       return userRepository.findById(userId);
    }

    /**
     * Method returns  user present in UserDetail table of db using userId without any fallbacks.
     * @return
     */
    @Override
    public Optional<UserEntity> fetchUserById(Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Method triggers update call to database for UserDetails entity.
     *
     * @return
     */
    @Override
    public UserEntity updateUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    private Iterable<UserEntity> fetchAllUsersFromCache() {
        return userEntities;
    }

    private Optional<UserEntity> fetchUserByIdFromCache(Long userId) {
        return userEntities
                .stream()
                .filter(usrEntity-> usrEntity.getUserId()==userId)
                .findAny();
    }

    private static List<UserEntity> getUserEntities() {
        List<UserEntity> userEntities= new ArrayList<>();
        UserEntity userEntity= UserEntity.builder()
                .userId(Long.valueOf("1001"))
                .firstName("fname")
                .lastName("lastname")
                .gender("male")
                .title("Mr")
                .address(AddressEntity.builder()
                        .addressId(Long.valueOf("1001"))
                        .city("Sydney")
                        .postcode("2000")
                        .state("NSW")
                        .street("Test street")
                        .build())
                .build();
        userEntities.add(userEntity);
        return userEntities;
    }
}
