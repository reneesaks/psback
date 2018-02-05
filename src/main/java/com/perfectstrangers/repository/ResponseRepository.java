package com.perfectstrangers.repository;

import com.perfectstrangers.domain.Response;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResponseRepository extends CrudRepository<Response, Long> {

    List<Response> findAll();

    Response findById(Long id);

}
