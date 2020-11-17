package com.example.user.service;

import com.example.user.exception.RecordNotFoundException;
import com.example.user.model.User;
import com.example.user.persistence.entity.AddressEntity;
import com.example.user.persistence.entity.UserEntity;
import com.example.user.persistence.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class UserServiceImplTest {

   @InjectMocks
   private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void whenFetchAllUsers_AndUsersPresentInDb_ThenUserListReturned() {
        List<UserEntity> userEntities= new ArrayList<>();
        userEntities.add(mockUserEntity(Long.valueOf("1001")));
        Mockito.when(userRepository.findAll())
                .thenReturn(userEntities);
        List<User> users= userService.fetchAllUsers();
        Assert.assertFalse(users.isEmpty());
        Assert.assertEquals(users.size(),1);
    }

    @Test
    public void whenFetchAllUsers_AndUsersNotPresentInDb_ThenEmptyUserListReturned() {
        Mockito.when(userRepository.findAll())
                .thenReturn(Collections.emptyList());
        List<User> users= userService.fetchAllUsers();
        Assert.assertTrue(users.isEmpty());
        Assert.assertEquals(users.size(),0);
    }

    @Test
    public void whenFetchUser_AndUserPresentInDb_ThenUserIsReturned() {
        Long userId= Long.valueOf("1001");
        Mockito.when(userRepository.findById(userId))
                .thenReturn(Optional.of(mockUserEntity(userId)));
        User user= userService.fetchUser(userId);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getFirstName(),"fname");
    }

    @Test
    public void whenFetchUser_AndUserNotPresentInDb_ThenRecordNotFoundException() {
        Long userId= Long.valueOf("1001");
        Mockito.when(userRepository.findById(userId))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(RecordNotFoundException.class,() -> userService.fetchUser(userId));
    }

    @Test
    public void whenUpdateUser_AndUserNotPresentInDb_ThenRecordNotFoundException() {
        Long userId= Long.valueOf("1001");
        Mockito.when(userRepository.findById(userId))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(RecordNotFoundException.class,() -> userService.updateUser(userId,Mockito.mock(User.class)));
    }

    @Test
    public void whenUpdateUser_AndUserPresentInDb_ThenRecordIsUpdated() {
        Long userId= Long.valueOf("1001");

        UserEntity savedUserEntity = mockUserEntity(userId);
        UserEntity newUserEntity = UserEntity.builder()
                .firstName("newFname")
                .build();
        Mockito.when(userRepository.findById(userId))
                .thenReturn(Optional.of(savedUserEntity));
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class)))
                .thenReturn(newUserEntity);
        User user=userService.updateUser(userId,User.builder()
                .firstName("newFname")
                .build());
        Assertions.assertNotNull(user);
        Assertions.assertEquals(user.getFirstName(),"newFname");

    }


    private UserEntity mockUserEntity(Long userId) {
        Long addressId= Long.valueOf("1022");
        return UserEntity.builder()
                .firstName("fname")
                .lastName("lastname")
                .gender("male")
                .title("Mr")
                .userId(userId)
                .address(mockAddressEntity(addressId))
                .build();
    }

    private AddressEntity mockAddressEntity(Long addressId) {
        return AddressEntity.builder()
                .addressId(addressId)
                .city("Sydney")
                .postcode("2000")
                .state("NSW")
                .street("Test street")
                .build();
    }
}