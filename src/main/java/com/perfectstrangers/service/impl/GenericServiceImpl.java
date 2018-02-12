package com.perfectstrangers.service.impl;

import com.perfectstrangers.domain.Advert;
import com.perfectstrangers.domain.Degree;
import com.perfectstrangers.domain.Hotel;
import com.perfectstrangers.domain.Occupation;
import com.perfectstrangers.domain.Response;
import com.perfectstrangers.domain.Resto;
import com.perfectstrangers.domain.User;
import com.perfectstrangers.error.EntityNotFoundException;
import com.perfectstrangers.repository.AdvertRepository;
import com.perfectstrangers.repository.DegreeRepository;
import com.perfectstrangers.repository.HotelRepository;
import com.perfectstrangers.repository.OccupationRepository;
import com.perfectstrangers.repository.ResponseRepository;
import com.perfectstrangers.repository.RestoRepository;
import com.perfectstrangers.repository.UserRepository;
import com.perfectstrangers.service.GenericService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Most repositories are accessible with this service.
 */
@Service
public class GenericServiceImpl implements GenericService {

    // ============== REPOSITORIES ============
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

    // ============== USERS ============
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> getAllUsersByPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getUserByEmail(String email) throws EntityNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException(User.class, "email", email);
        }
        return user;
    }

    @Override
    public User getUserById(Long id) throws EntityNotFoundException {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new EntityNotFoundException(User.class, "id", id.toString());
        }
        return user;
    }

    // ============== OCCUPATIONS ============
    @Override
    public List<Occupation> getAllOccupations() {
        return occupationRepository.findAll();
    }

    @Override
    public Occupation getOccupationByName(String name) throws EntityNotFoundException {
        Occupation occupation = occupationRepository.findByName(name);
        if (occupation == null) {
            throw new EntityNotFoundException(Occupation.class, "name", name);
        }
        return occupation;
    }

    @Override
    public Occupation getOccupationById(Long id) throws EntityNotFoundException {
        Occupation occupation = occupationRepository.findById(id);
        if (occupation == null) {
            throw new EntityNotFoundException(Occupation.class, "id", id.toString());
        }
        return occupation;
    }

    // ============== DEGREES ============
    @Override
    public List<Degree> getAllDegrees() {
        return degreeRepository.findAll();
    }

    @Override
    public Degree getDegreeByName(String name) throws EntityNotFoundException {
        Degree degree = degreeRepository.findByName(name);
        if (degree == null) {
            throw new EntityNotFoundException(Degree.class, "name", name);
        }
        return degree;
    }

    @Override
    public Degree getDegreeById(Long id) throws EntityNotFoundException {
        Degree degree = degreeRepository.findById(id);
        if (degree == null) {
            throw new EntityNotFoundException(Degree.class, "id", id.toString());
        }
        return degree;
    }

    // ============== HOTELS ============
    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Page<Hotel> getAllHotelsByPage(Pageable pageable) {
        return hotelRepository.findAll(pageable);
    }

    @Override
    public Hotel getHotelByName(String name) throws EntityNotFoundException {
        Hotel hotel = hotelRepository.findByName(name);
        if (hotel == null) {
            throw new EntityNotFoundException(Hotel.class, "name", name);
        }
        return hotel;
    }

    @Override
    public Hotel getHotelById(Long id) throws EntityNotFoundException {
        Hotel hotel = hotelRepository.findById(id);
        if (hotel == null) {
            throw new EntityNotFoundException(Hotel.class, "id", id.toString());
        }
        return hotel;
    }

    // ============== RESTOS ============
    @Override
    public List<Resto> getAllRestos() {
        return restoRepository.findAll();
    }

    @Override
    public Page<Resto> getAllRestosByPage(Pageable pageable) {
        return restoRepository.findAll(pageable);
    }

    @Override
    public Resto getRestoByName(String name) throws EntityNotFoundException {
        Resto resto = restoRepository.findByName(name);
        if (resto == null) {
            throw new EntityNotFoundException(Resto.class, "name", name);
        }
        return resto;
    }

    @Override
    public Resto getRestoById(Long id) throws EntityNotFoundException {
        Resto resto = restoRepository.findById(id);
        if (resto == null) {
            throw new EntityNotFoundException(Resto.class, "id", id.toString());
        }
        return resto;
    }

    // ============== ADVERTS ============
    @Override
    public List<Advert> getAllAdverts() {
        return advertRepository.findAll();
    }

    @Override
    public Page<Advert> getAllAdvertsByPage(Pageable pageable) {
        return advertRepository.findAll(pageable);
    }

    @Override
    public Advert getAdvertById(Long id) throws EntityNotFoundException {
        Advert advert = advertRepository.findById(id);
        if (advert == null) {
            throw new EntityNotFoundException(Advert.class, "id", id.toString());
        }
        return advert;
    }

    // ============== RESPONSES ============
    @Override
    public List<Response> getAllResponses() {
        return responseRepository.findAll();
    }

    @Override
    public Page<Response> getAllResponsesByPage(Pageable pageable) {
        return responseRepository.findAll(pageable);
    }

    @Override
    public Response getResponseById(Long id) throws EntityNotFoundException {
        Response response = responseRepository.findById(id);
        if (response == null) {
            throw new EntityNotFoundException(Response.class, "id", id.toString());
        }
        return response;
    }
}
