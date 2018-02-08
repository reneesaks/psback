package com.perfectstrangers.service;

import com.perfectstrangers.domain.Advert;
import com.perfectstrangers.domain.Degree;
import com.perfectstrangers.domain.Hotel;
import com.perfectstrangers.domain.Occupation;
import com.perfectstrangers.domain.Response;
import com.perfectstrangers.domain.Resto;
import com.perfectstrangers.domain.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface GenericService {

    // ============== USERS ============
    List<User> getAllUsers();

    Page<User> getAllUsersByPage(Pageable pageable);

    User getUserByEmail(String email);

    User getUserById(Long id);

    // ============== OCCUPATIONS ============
    List<Occupation> getAllOccupations();

    Occupation getOccupationByName(String name);

    Occupation getOccupationById(Long id);

    // ============== DEGREES ============
    List<Degree> getAllDegrees();

    Degree getDegreeByName(String name);

    Degree getDegreeById(Long id);

    // ============== HOTELS ============
    List<Hotel> getAllHotels();

    Page<Hotel> getAllHotelsByPage(Pageable pageable);

    Hotel getHotelByName(String name);

    Hotel getHotelById(Long id);

    // ============== RESTOS ============
    List<Resto> getAllRestos();

    Page<Resto> getAllRestosByPage(Pageable pageable);

    Resto getRestoByName(String name);

    Resto getRestoById(Long id);

    // ============== ADVERTS ============
    List<Advert> getAllAdverts();

    Page<Advert> getAllAdvertsByPage(Pageable pageable);

    Advert getAdvertById(Long id);

    // ============== RESPONSES ============
    List<Response> getAllResponses();

    Page<Response> getAllResponsesByPage(Pageable pageable);

    Response getResponseById(Long id);
}
