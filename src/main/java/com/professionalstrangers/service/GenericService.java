package com.professionalstrangers.service;

import com.professionalstrangers.domain.Advert;
import com.professionalstrangers.domain.Degree;
import com.professionalstrangers.domain.Hotel;
import com.professionalstrangers.domain.Occupation;
import com.professionalstrangers.domain.Response;
import com.professionalstrangers.domain.Resto;
import com.professionalstrangers.domain.User;
import com.professionalstrangers.error.EntityNotFoundException;
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

    void deleteUser(User user) throws EntityNotFoundException;

    // ============== OCCUPATIONS ============
    List<Occupation> getAllOccupations();

    Occupation getOccupationByName(String name) throws EntityNotFoundException;

    Occupation getOccupationById(Long id) throws EntityNotFoundException;

    void saveOccupation(Occupation occupation);

    void updateOccupation(Occupation occupation) throws EntityNotFoundException;

    void deleteOccupation(Occupation occupation) throws EntityNotFoundException;

    // ============== DEGREES ============
    List<Degree> getAllDegrees();

    Degree getDegreeByName(String name) throws EntityNotFoundException;

    Degree getDegreeById(Long id) throws EntityNotFoundException;

    void saveDegree(Degree degree);

    void updateDegree(Degree degree) throws EntityNotFoundException;

    void deleteDegree(Degree degree) throws EntityNotFoundException;

    // ============== HOTELS ============
    List<Hotel> getAllHotels();

    Page<Hotel> getAllHotelsByPage(Pageable pageable);

    Hotel getHotelByName(String name) throws EntityNotFoundException;

    Hotel getHotelById(Long id) throws EntityNotFoundException;

    void saveHotel(Hotel hotel);

    void updateHotel(Hotel hotel) throws EntityNotFoundException;

    void deleteHotel(Hotel hotel) throws EntityNotFoundException;

    // ============== RESTOS ============
    List<Resto> getAllRestos();

    Page<Resto> getAllRestosByPage(Pageable pageable);

    Resto getRestoByName(String name) throws EntityNotFoundException;

    Resto getRestoById(Long id) throws EntityNotFoundException;

    void saveResto(Resto resto);

    void updateResto(Resto resto) throws EntityNotFoundException;

    void deleteResto(Resto resto) throws EntityNotFoundException;

    // ============== ADVERTS ============
    List<Advert> getAllAdverts();

    Page<Advert> getAllAdvertsByPage(Pageable pageable);

    Advert getAdvertById(Long id) throws EntityNotFoundException;

    List<Advert> getAdvertsByHotelId(Long id) throws EntityNotFoundException;

    List<Advert> getAdvertsByUserId(Long id) throws EntityNotFoundException;

    void saveAdvert(Advert advert);

    void updateAdvert(Advert advert) throws EntityNotFoundException;

    void deleteAdvert(Advert advert) throws EntityNotFoundException;

    // ============== RESPONSES ============
    List<Response> getAllResponses();

    Page<Response> getAllResponsesByPage(Pageable pageable);

    Response getResponseById(Long id) throws EntityNotFoundException;

    List<Response> getResponsesByAdvert(Advert advert) throws EntityNotFoundException;

    List<Response> getResponsesByUserId(Long id) throws EntityNotFoundException;

    void saveResponse(Response response);

    void updateResponse(Response response) throws EntityNotFoundException;

    void deleteResponse(Response response) throws EntityNotFoundException;
}