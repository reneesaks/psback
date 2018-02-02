package com.perfectstrangers.service;

import com.perfectstrangers.domain.*;

import java.util.List;


public interface GenericService {

    // Users
    List<User> getAllUsers();

    User getUserByEmail(String email);

    User getUserById(Long id);

    // Occupations
    List<Occupation> getAllOccupations();

    Occupation getOccupationByName(String name);

    Occupation getOccupationById(Long id);

    // Degrees
    List<Degree> getAllDegrees();

    Degree getDegreeByName(String name);

    Degree getDegreeById(Long id);

    // Hotels
    List<Hotel> getAllHotels();

    Hotel getHotelByName(String name);

    Hotel getHotelById(Long id);

    // Restaurants
    List<Restaurant> getAllRestaurants();

    Restaurant getRestaurantByName(String name);

    Restaurant getRestaurantById(Long id);

}
