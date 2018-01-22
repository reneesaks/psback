package com.perfectstrangers.repository;

import com.perfectstrangers.domain.Hotel;
import org.springframework.data.repository.CrudRepository;

public interface HotelRepository extends CrudRepository<Hotel, Long> {
}
