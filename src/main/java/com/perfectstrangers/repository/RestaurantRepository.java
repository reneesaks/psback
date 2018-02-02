package com.perfectstrangers.repository;

import com.perfectstrangers.domain.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

    List<Restaurant> findAll();

    Restaurant findByName(String name);

    Restaurant findById(Long id);

}
