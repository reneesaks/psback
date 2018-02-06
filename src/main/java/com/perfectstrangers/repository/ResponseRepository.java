package com.perfectstrangers.repository;

import com.perfectstrangers.domain.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ResponseRepository extends PagingAndSortingRepository<Response, Long> {

    List<Response> findAll();

    Page<Response> findAll(Pageable pageable);

    Response findById(Long id);

}
