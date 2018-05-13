package com.professionalstrangers.service.impl;

import com.professionalstrangers.domain.Advert;
import com.professionalstrangers.domain.Degree;
import com.professionalstrangers.domain.Hotel;
import com.professionalstrangers.domain.Occupation;
import com.professionalstrangers.domain.Response;
import com.professionalstrangers.domain.Resto;
import com.professionalstrangers.domain.User;
import com.professionalstrangers.domain.enums.AdvertStatus;
import com.professionalstrangers.error.EntityNotFoundException;
import com.professionalstrangers.repository.AdvertRepository;
import com.professionalstrangers.repository.DegreeRepository;
import com.professionalstrangers.repository.HotelRepository;
import com.professionalstrangers.repository.OccupationRepository;
import com.professionalstrangers.repository.ResponseRepository;
import com.professionalstrangers.repository.RestoRepository;
import com.professionalstrangers.repository.UserRepository;
import com.professionalstrangers.service.GenericService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * All repositories are accessible with this service. Includes most common methods that might be needed.
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
        User user = userRepository.getById(id);
        if (user == null) {
            throw new EntityNotFoundException(User.class, "id", id.toString());
        }
        return user;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) throws EntityNotFoundException {
        Long id = user.getId();
        User findUser = userRepository.getById(id);
        if (findUser == null) {
            throw new EntityNotFoundException(User.class, "id", id.toString());
        }
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) throws EntityNotFoundException {
        Long id = user.getId();
        User findUser = userRepository.getById(id);
        if (findUser == null) {
            throw new EntityNotFoundException(User.class, "id", id.toString());
        }
        userRepository.delete(user);
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
        Occupation occupation = occupationRepository.getById(id);
        if (occupation == null) {
            throw new EntityNotFoundException(Occupation.class, "id", id.toString());
        }
        return occupation;
    }

    @Override
    public void saveOccupation(Occupation occupation) {
        occupationRepository.save(occupation);
    }

    @Override
    public void updateOccupation(Occupation occupation) throws EntityNotFoundException {
        Long id = occupation.getId();
        Occupation findOccupation = occupationRepository.getById(id);
        if (findOccupation == null) {
            throw new EntityNotFoundException(Occupation.class, "id", id.toString());
        }
        occupationRepository.save(occupation);
    }

    @Override
    public void deleteOccupation(Occupation occupation) throws EntityNotFoundException {
        Long id = occupation.getId();
        Occupation findOccupation = occupationRepository.getById(id);
        if (findOccupation == null) {
            throw new EntityNotFoundException(Occupation.class, "id", id.toString());
        }
        occupationRepository.delete(occupation);
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
        Degree degree = degreeRepository.getById(id);
        if (degree == null) {
            throw new EntityNotFoundException(Degree.class, "id", id.toString());
        }
        return degree;
    }

    @Override
    public void saveDegree(Degree degree) {
        degreeRepository.save(degree);
    }

    @Override
    public void updateDegree(Degree degree) throws EntityNotFoundException {
        Long id = degree.getId();
        Degree findDegree = degreeRepository.getById(id);
        if (findDegree == null) {
            throw new EntityNotFoundException(Degree.class, "id", id.toString());
        }
        degreeRepository.save(degree);
    }

    @Override
    public void deleteDegree(Degree degree) throws EntityNotFoundException {
        Long id = degree.getId();
        Degree findDegree = degreeRepository.getById(id);
        if (findDegree == null) {
            throw new EntityNotFoundException(Degree.class, "id", id.toString());
        }
        degreeRepository.delete(degree);
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
        Hotel hotel = hotelRepository.getById(id);
        if (hotel == null) {
            throw new EntityNotFoundException(Hotel.class, "id", id.toString());
        }
        return hotel;
    }

    @Override
    public void saveHotel(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    @Override
    public void updateHotel(Hotel hotel) throws EntityNotFoundException {
        Long id = hotel.getId();
        Hotel findHotel = hotelRepository.getById(id);
        if (findHotel == null) {
            throw new EntityNotFoundException(Hotel.class, "id", id.toString());
        }
        hotelRepository.save(hotel);
    }

    @Override
    public void deleteHotel(Hotel hotel) throws EntityNotFoundException {
        Long id = hotel.getId();
        Hotel findHotel = hotelRepository.getById(id);
        if (findHotel == null) {
            throw new EntityNotFoundException(Hotel.class, "id", id.toString());
        }
        hotelRepository.delete(hotel);
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
        Resto resto = restoRepository.getById(id);
        if (resto == null) {
            throw new EntityNotFoundException(Resto.class, "id", id.toString());
        }
        return resto;
    }

    @Override
    public void saveResto(Resto resto) {
        restoRepository.save(resto);
    }

    @Override
    public void updateResto(Resto resto) throws EntityNotFoundException {
        Long id = resto.getId();
        Resto findResto = restoRepository.getById(id);
        if (findResto == null) {
            throw new EntityNotFoundException(Resto.class, "id", id.toString());
        }
        restoRepository.save(resto);
    }

    @Override
    public void deleteResto(Resto resto) throws EntityNotFoundException {
        Long id = resto.getId();
        Resto findResto = restoRepository.getById(id);
        if (findResto == null) {
            throw new EntityNotFoundException(Resto.class, "id", id.toString());
        }
        restoRepository.delete(resto);
    }

    // ============== ADVERTS ============
    @Override
    public List<Advert> getAllAdverts() {

        List<Advert> adverts = advertRepository.findAllByAdvertStatusIsNot(AdvertStatus.ACCEPTED);
        final Date now = DateUtils.addMinutes(Date.from(Instant.now()), 15);

        adverts.removeIf(advert -> Date.from(Instant.parse(advert.getPreferredStart())).before(now));

        return adverts;
    }

    @Override
    public Page<Advert> getAllAdvertsByPage(Pageable pageable) {
        return advertRepository.findAllByAdvertStatusIsNot(pageable, AdvertStatus.ACCEPTED);
    }

    @Override
    public Advert getAdvertById(Long id) throws EntityNotFoundException {
        Advert advert = advertRepository.getById(id);
        if (advert == null) {
            throw new EntityNotFoundException(Advert.class, "id", id.toString());
        }
        return advert;
    }

    @Override
    public List<Advert> getAdvertsByHotelId(Long id) throws EntityNotFoundException {

        List<Hotel> hotel = new ArrayList<>();
        hotel.add(hotelRepository.getById(id));
        List<Advert> adverts = advertRepository
                .findAllByHotelsAndAdvertStatusIsNot(hotel, AdvertStatus.ACCEPTED);
        final Date now = DateUtils.addMinutes(Date.from(Instant.now()), 15);

        adverts.removeIf(advert -> Date.from(Instant.parse(advert.getPreferredStart())).before(now));

        return adverts;
    }

    @Override
    public List<Advert> getAdvertsByUserId(Long id) throws EntityNotFoundException {
        User user = userRepository.getById(id);
        if (user == null) {
            throw new EntityNotFoundException(User.class, "id", id.toString());
        }

        return advertRepository.findAllByUser(userRepository.getById(user.getId()));
    }

    @Override
    public void saveAdvert(Advert advert) {
        advertRepository.save(advert);
    }

    @Override
    public void updateAdvert(Advert advert) throws EntityNotFoundException {
        Long id = advert.getId();
        Advert findAdvert = advertRepository.getById(id);
        if (findAdvert == null) {
            throw new EntityNotFoundException(Advert.class, "id", id.toString());
        }
        advertRepository.save(advert);
    }

    @Override
    public void deleteAdvert(Advert advert) throws EntityNotFoundException {
        Long id = advert.getId();
        Advert findAdvert = advertRepository.getById(id);
        if (findAdvert == null) {
            throw new EntityNotFoundException(Advert.class, "id", id.toString());
        }
        advertRepository.delete(advert);
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
        Response response = responseRepository.getById(id);
        if (response == null) {
            throw new EntityNotFoundException(Response.class, "id", id.toString());
        }
        return response;
    }

    @Override
    public List<Response> getResponsesByAdvert(Advert advert) throws EntityNotFoundException {
        return advert.getResponses();
    }

    @Override
    public List<Response> getResponsesByUserId(Long id) throws EntityNotFoundException {

        User user = userRepository.getById(id);
        if (user == null) {
            throw new EntityNotFoundException(User.class, "id", id.toString());
        }

        List<Response> responses = responseRepository.findAllByUser(user);
        final Date now = DateUtils.addMinutes(Date.from(Instant.now()), 15);

        responses.removeIf(
                response -> Date.from(Instant.parse(response.getAdvert().getPreferredStart())).before(now));

        return responses;
    }

    @Override
    public void saveResponse(Response response) {
        responseRepository.save(response);
    }

    @Override
    public void updateResponse(Response response) throws EntityNotFoundException {
        Long id = response.getId();
        Response findResponse = responseRepository.getById(id);
        if (findResponse == null) {
            throw new EntityNotFoundException(Response.class, "id", id.toString());
        }
        responseRepository.save(response);
    }

    @Override
    public void deleteResponse(Response response) throws EntityNotFoundException {
        Long id = response.getId();
        Response findResponse = responseRepository.getById(id);
        if (findResponse == null) {
            throw new EntityNotFoundException(Response.class, "id", id.toString());
        }
        responseRepository.delete(response);
    }
}
