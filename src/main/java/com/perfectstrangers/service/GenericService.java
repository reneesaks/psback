package com.perfectstrangers.service;

import com.perfectstrangers.domain.Advert;
import com.perfectstrangers.domain.Degree;
import com.perfectstrangers.domain.Hotel;
import com.perfectstrangers.domain.Occupation;
import com.perfectstrangers.domain.Response;
import com.perfectstrangers.domain.Resto;
import com.perfectstrangers.domain.User;
import com.perfectstrangers.error.EntityNotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface GenericService {

    // ============== USERS ============
    List<User> getAllUsers();

    Page<User> getAllUsersByPage(Pageable pageable);

    User getUserByEmail(String email) throws EntityNotFoundException;

    User getUserById(Long id) throws EntityNotFoundException;

    void saveUser(User user);

    void updateUser(User user) throws EntityNotFoundException;

    // ============== OCCUPATIONS ============
    List<Occupation> getAllOccupations();

    Occupation getOccupationByName(String name) throws EntityNotFoundException;

    Occupation getOccupationById(Long id) throws EntityNotFoundException;

    void saveOccupation(Occupation occupation);

    void updateOccupation(Occupation occupation) throws EntityNotFoundException;

    // ============== DEGREES ============
    List<Degree> getAllDegrees();

    Degree getDegreeByName(String name) throws EntityNotFoundException;

    Degree getDegreeById(Long id) throws EntityNotFoundException;

    void saveDegree(Degree degree);

    void updateDegree(Degree degree) throws EntityNotFoundException;

    // ============== HOTELS ============
    List<Hotel> getAllHotels();

    Page<Hotel> getAllHotelsByPage(Pageable pageable);

    Hotel getHotelByName(String name) throws EntityNotFoundException;

    Hotel getHotelById(Long id) throws EntityNotFoundException;

    void saveHotel(Hotel hotel);

    void updateHotel(Hotel hotel) throws EntityNotFoundException;

    // ============== RESTOS ============
    List<Resto> getAllRestos();

    Page<Resto> getAllRestosByPage(Pageable pageable);

    Resto getRestoByName(String name) throws EntityNotFoundException;

    Resto getRestoById(Long id) throws EntityNotFoundException;

    void saveResto(Resto resto);

    void updateResto(Resto resto) throws EntityNotFoundException;

    // ============== ADVERTS ============
    List<Advert> getAllAdverts();

    Page<Advert> getAllAdvertsByPage(Pageable pageable);

    Advert getAdvertById(Long id) throws EntityNotFoundException;

    void saveAdvert(Advert advert);

    void updateAdvert(Advert advert) throws EntityNotFoundException;

    // ============== RESPONSES ============
    List<Response> getAllResponses();

    Page<Response> getAllResponsesByPage(Pageable pageable);

    Response getResponseById(Long id) throws EntityNotFoundException;

    void saveResponse(Response response);

    void updateResponse(Response response) throws EntityNotFoundException;
}
