package com.example.user.persistence.repository;

import com.example.user.persistence.entity.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Test class to validate Spring data CRUD functions. <br/>
 * Class loads data objects using H2 database files "schema.sql" and "data.sql" from classpath.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    /**
     * Tests findById function of <code>{@link UserRepository}</code> CRUD Repository.
     * <br/>
     * Methods test non-empty result to be returned
     * when findById is triggered with a valid user id.
     */
    @Test
    public void whenFindById_thenReturnUser() {
        // given
        Long userId = Long.valueOf(1001);
        // when
        Optional<UserEntity> user = userRepository.findById(userId);
        // then
        assertNotNull(user.get());
        assertEquals(user.get().getFirstName(),"fname1");
    }

    /**
     * Tests findById function of <code>{@link UserRepository}</code> CRUD Repository.
     * <br/>
     * Methods test empty result to be returned
     * when findById is triggered with a in-valid user id.
     */
    @Test
    public void whenFindById_WithNoRecord_thenReturnOptionalEmpty() {
        // given
        Long userId = Long.valueOf(1011);
        // when
        Optional<UserEntity> user = userRepository.findById(userId);
        // then
        assertFalse(user.isPresent());
    }

    /**
     * Tests save function of <code>{@link UserRepository}</code> CRUD Repository.
     * <br/>
     * Methods saves the record in database and validates
     * if the persisted record has same attributes which is requested.
     */
    @Test
    public void whenSave_thenReturnSavedUser() {
        // when
        UserEntity user = userRepository.save(UserEntity.builder()
                .firstName("fnameTest")
                .lastName("lastnameTest")
                .gender("male")
                .title("Mr")
                .build());
        // then
        assertNotNull(user);
        assertEquals(user.getFirstName(),"fnameTest");
    }


}