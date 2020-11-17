package com.example.user.persistence.repository;

import com.example.user.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class which provides capabilities for Crud operations on UserDetails table.
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long>{
}
