package com.perfectstrangers.repository;

import com.perfectstrangers.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    List<User> findAll();

    Page<User> findAll(Pageable pageable);

    User findByEmail(String email);

    User findById(Long id);
}
