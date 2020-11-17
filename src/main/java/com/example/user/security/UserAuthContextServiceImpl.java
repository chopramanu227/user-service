package com.example.user.security;

import com.example.user.persistence.entity.UserEntity;
import com.example.user.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Serive class to retrieve user authentication data from database.
 */
public class UserAuthContextServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Method retrieves user data from User details table.
     *<br/>
     * Userid is used as username to retrieve user information.
     * @param userId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserEntity userEntity=userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new UsernameNotFoundException("Authentication Failed : User not found"));
        return new UserAuthContext(userEntity);
    }
}
