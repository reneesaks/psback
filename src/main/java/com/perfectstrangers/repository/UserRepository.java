package com.perfectstrangers.repository;

import com.perfectstrangers.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAll();

    User findByEmail(String email);

    User findById(Long id);

}
