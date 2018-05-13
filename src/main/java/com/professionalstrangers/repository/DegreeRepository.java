package com.professionalstrangers.repository;

import com.professionalstrangers.domain.Degree;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DegreeRepository extends CrudRepository<Degree, Long> {

    List<Degree> findAll();

    Degree findByName(String name);

    Degree getById(Long id);
}
