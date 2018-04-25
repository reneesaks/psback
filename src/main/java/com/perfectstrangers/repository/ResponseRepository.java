package com.perfectstrangers.repository;

import com.perfectstrangers.domain.Response;
import com.perfectstrangers.domain.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ResponseRepository extends PagingAndSortingRepository<Response, Long> {

    List<Response> findAll();

    Page<Response> findAll(Pageable pageable);

    Response findById(Long id);

    List<Response> findAllByUser(User user);
}
