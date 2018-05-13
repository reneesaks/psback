package com.professionalstrangers.repository;

import com.professionalstrangers.domain.Response;
import com.professionalstrangers.domain.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ResponseRepository extends PagingAndSortingRepository<Response, Long> {

    List<Response> findAll();

    Page<Response> findAll(Pageable pageable);

    Response getById(Long id);

    List<Response> findAllByUser(User user);
}
