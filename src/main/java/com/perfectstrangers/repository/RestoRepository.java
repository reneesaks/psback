package com.perfectstrangers.repository;

import com.perfectstrangers.domain.Resto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestoRepository extends CrudRepository<Resto, Long> {

    List<Resto> findAll();

    Resto findByName(String name);

    Resto findById(Long id);

}
