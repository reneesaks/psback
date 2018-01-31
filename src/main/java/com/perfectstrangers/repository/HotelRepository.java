package com.perfectstrangers.repository;

import com.perfectstrangers.domain.Hotel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends CrudRepository<Hotel, Long> {

    public Hotel findByName(String name);

}
