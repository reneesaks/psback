package com.perfectstrangers.service;

import com.perfectstrangers.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface GenericService {

    // Users
    List<User> getAllUsers();

    Page<User> getAllUsersByPage(Pageable pageable);

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

    Page<Hotel> getAllHotelsByPage(Pageable pageable);

    Hotel getHotelByName(String name);

    Hotel getHotelById(Long id);

    // Restos
    List<Resto> getAllRestos();

    Page<Resto> getAllRestosByPage(Pageable pageable);

    Resto getRestoByName(String name);

    Resto getRestoById(Long id);

    // Adverts
    List<Advert> getAllAdverts();

    Page<Advert> getAllAdvertsByPage(Pageable pageable);

    Advert getAdvertById(Long id);

    // Response
    List<Response> getAllResponses();

    Page<Response> getAllResponsesByPage(Pageable pageable);

    Response getResponseById(Long id);

}
