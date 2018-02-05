package com.perfectstrangers.repository;

import com.perfectstrangers.domain.Advert;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdvertRepository extends CrudRepository<Advert, Long> {

    List<Advert> findAll();

    Advert findById(Long id);

}
