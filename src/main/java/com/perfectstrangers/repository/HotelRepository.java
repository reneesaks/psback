package com.perfectstrangers.repository;

import com.perfectstrangers.domain.Hotel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends CrudRepository<Hotel, Long> {

    List<Hotel> findAll();

    Hotel findByName(String name);

    Hotel findById(Long id);

}
