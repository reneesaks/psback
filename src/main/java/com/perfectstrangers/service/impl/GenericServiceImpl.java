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
    private UserRepository userRepository;

    private OccupationRepository occupationRepository;

    private DegreeRepository degreeRepository;

    private HotelRepository hotelRepository;

    private RestaurantRepository restaurantRepository;

    @Autowired
    public GenericServiceImpl(UserRepository userRepository,
                              OccupationRepository occupationRepository,
                              DegreeRepository degreeRepository,
                              HotelRepository hotelRepository,
                              RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.occupationRepository = occupationRepository;
        this.degreeRepository = degreeRepository;
        this.hotelRepository = hotelRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public GenericServiceImpl() {
    }

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
