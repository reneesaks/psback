package com.perfectstrangers.service.impl;

import com.perfectstrangers.domain.*;
import com.perfectstrangers.repository.*;
import com.perfectstrangers.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenericServiceImpl implements GenericService {

    // Repositories
    private UserRepository userRepository;

    private OccupationRepository occupationRepository;

    private DegreeRepository degreeRepository;

    private HotelRepository hotelRepository;

    private RestoRepository restoRepository;

    private AdvertRepository advertRepository;

    private ResponseRepository responseRepository;

    @Autowired
    public GenericServiceImpl(UserRepository userRepository,
                              OccupationRepository occupationRepository,
                              DegreeRepository degreeRepository,
                              HotelRepository hotelRepository,
                              RestoRepository restoRepository,
                              AdvertRepository advertRepository,
                              ResponseRepository responseRepository) {
        this.userRepository = userRepository;
        this.occupationRepository = occupationRepository;
        this.degreeRepository = degreeRepository;
        this.hotelRepository = hotelRepository;
        this.restoRepository = restoRepository;
        this.advertRepository = advertRepository;
        this.responseRepository = responseRepository;
    }

    public GenericServiceImpl() {
    }

    // Users
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> getAllUsersByPage(Pageable pageable) {
        return userRepository.findAll(pageable);
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
        return hotelRepository.findAll();
    }

    @Override
    public Page<Hotel> getAllHotelsByPage(Pageable pageable) {
        return hotelRepository.findAll(pageable);
    }

    @Override
    public Hotel getHotelByName(String name) {
        return hotelRepository.findByName(name);
    }

    @Override
    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id);
    }

    // Restos
    @Override
    public List<Resto> getAllRestos() {
        return restoRepository.findAll();
    }

    @Override
    public Page<Resto> getAllRestosByPage(Pageable pageable) {
        return restoRepository.findAll(pageable);
    }

    @Override
    public Resto getRestoByName(String name) {
        return restoRepository.findByName(name);
    }

    @Override
    public Resto getRestoById(Long id) {
        return restoRepository.findById(id);
    }

    // Adverts
    @Override
    public List<Advert> getAllAdverts() {
        return advertRepository.findAll();
    }

    @Override
    public Page<Advert> getAllAdvertsByPage(Pageable pageable) {
        return advertRepository.findAll(pageable);
    }

    @Override
    public Advert getAdvertById(Long id) {
        return advertRepository.findById(id);
    }

    // Responses
    @Override
    public List<Response> getAllResponses() {
        return responseRepository.findAll();
    }

    @Override
    public Page<Response> getAllResponsesByPage(Pageable pageable) {
        return responseRepository.findAll(pageable);
    }

    @Override
    public Response getResponseById(Long id) {
        return responseRepository.findById(id);
    }

}
