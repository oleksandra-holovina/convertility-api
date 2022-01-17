package com.convertility.dao;

import com.convertility.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserDao extends CrudRepository<User, String> {
    Optional<User> findFirstByBearerToken(String bearerToken);
    boolean existsUserByIdAndBearerToken(String id, String bearerToken);
}
