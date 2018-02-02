package com.perfectstrangers.repository;

import com.perfectstrangers.domain.Occupation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OccupationRepository extends CrudRepository<Occupation, Long> {

    List<Occupation> findAll();

    Occupation findByName(String name);

    Occupation findById(Long id);

}
