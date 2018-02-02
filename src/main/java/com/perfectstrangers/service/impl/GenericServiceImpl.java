package com.perfectstrangers.service.impl;

import com.perfectstrangers.domain.*;
import com.perfectstrangers.repository.*;
import com.perfectstrangers.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenericServiceImpl implements GenericService {

    // Repositories
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OccupationRepository occupationRepository;

    @Autowired
    private DegreeRepository degreeRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    // Users
    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Occupations
    @Override
    public List<Occupation> getAllOccupations() {
        return occupationRepository.findAll();
    }

    @Override
    public Occupation getOccupationByName(String name) {
        return occupationRepository.findByName(name);
    }

    @Override
    public Occupation getOccupationById(Long id) {
        return occupationRepository.findById(id);
    }

    // Degrees
    @Override
    public List<Degree> getAllDegrees() {
        return degreeRepository.findAll();
    }

    @Override
    public Degree getDegreeByName(String name) {
        return degreeRepository.findByName(name);
    }

    @Override
    public Degree getDegreeById(Long id) {
        return degreeRepository.findById(id);
    }

    // Hotels
    @Override
    public List<Hotel> getAllHotels() {
        return (List<Hotel>) hotelRepository.findAll();
    }

    @Override
    public Hotel getHotelByName(String name) {
        return hotelRepository.findByName(name);
    }

    @Override
    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id);
    }

    // Restaurants
    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant getRestaurantByName(String name) {
        return restaurantRepository.findByName(name);
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }

}
