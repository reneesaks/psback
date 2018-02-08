package com.perfectstrangers.repository;

import com.perfectstrangers.domain.Resto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestoRepository extends PagingAndSortingRepository<Resto, Long> {

    List<Resto> findAll();

    Page<Resto> findAll(Pageable pageable);

    Resto findByName(String name);

    Resto findById(Long id);
}
