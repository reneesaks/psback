package com.professionalstrangers.service.impl;

import com.professionalstrangers.domain.Invitation;
import com.professionalstrangers.domain.Degree;
import com.professionalstrangers.domain.Hotel;
import com.professionalstrangers.domain.Occupation;
import com.professionalstrangers.domain.Response;
import com.professionalstrangers.domain.Resto;
import com.professionalstrangers.domain.User;
import com.professionalstrangers.domain.enums.InvitationStatus;
import com.professionalstrangers.error.EntityNotFoundException;
import com.professionalstrangers.repository.InvitationRepository;
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
    private InvitationRepository invitationRepository;
    private ResponseRepository responseRepository;

    @Autowired
    public GenericServiceImpl(UserRepository userRepository,
            OccupationRepository occupationRepository,
            DegreeRepository degreeRepository,
            HotelRepository hotelRepository,
            RestoRepository restoRepository,
            InvitationRepository invitationRepository,
            ResponseRepository responseRepository) {
        this.userRepository = userRepository;
        this.occupationRepository = occupationRepository;
        this.degreeRepository = degreeRepository;
        this.hotelRepository = hotelRepository;
        this.restoRepository = restoRepository;
        this.invitationRepository = invitationRepository;
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

    // ============== INVITATIONS ============
    @Override
    public List<Invitation> getAllInvitations() {

        List<Invitation> invitations = invitationRepository.findAllByInvitationStatusIsNot(InvitationStatus.ACCEPTED);
        final Date now = DateUtils.addMinutes(Date.from(Instant.now()), 15);

        invitations.removeIf(invitation -> Date.from(Instant.parse(invitation.getPreferredStart())).before(now));

        return invitations;
    }

    @Override
    public Page<Invitation> getAllInvitationsByPage(Pageable pageable) {
        return invitationRepository.findAllByInvitationStatusIsNot(pageable, InvitationStatus.ACCEPTED);
    }

    @Override
    public Invitation getInvitationById(Long id) throws EntityNotFoundException {
        Invitation invitation = invitationRepository.getById(id);
        if (invitation == null) {
            throw new EntityNotFoundException(Invitation.class, "id", id.toString());
        }
        return invitation;
    }

    @Override
    public List<Invitation> getInvitationsByHotelId(Long id) throws EntityNotFoundException {

        List<Hotel> hotel = new ArrayList<>();
        hotel.add(hotelRepository.getById(id));
        List<Invitation> invitations = invitationRepository
                .findAllByHotelsAndInvitationStatusIsNot(hotel, InvitationStatus.ACCEPTED);
        final Date now = DateUtils.addMinutes(Date.from(Instant.now()), 15);

        invitations.removeIf(invitation -> Date.from(Instant.parse(invitation.getPreferredStart())).before(now));

        return invitations;
    }

    @Override
    public List<Invitation> getInvitationsByUserId(Long id) throws EntityNotFoundException {
        User user = userRepository.getById(id);
        if (user == null) {
            throw new EntityNotFoundException(User.class, "id", id.toString());
        }

        return invitationRepository.findAllByUser(userRepository.getById(user.getId()));
    }

    @Override
    public void saveInvitation(Invitation invitation) {
        invitationRepository.save(invitation);
    }

    @Override
    public void updateInvitation(Invitation invitation) throws EntityNotFoundException {
        Long id = invitation.getId();
        Invitation findInvitation = invitationRepository.getById(id);
        if (findInvitation == null) {
            throw new EntityNotFoundException(Invitation.class, "id", id.toString());
        }
        invitationRepository.save(invitation);
    }

    @Override
    public void deleteInvitation(Invitation invitation) throws EntityNotFoundException {
        Long id = invitation.getId();
        Invitation findInvitation = invitationRepository.getById(id);
        if (findInvitation == null) {
            throw new EntityNotFoundException(Invitation.class, "id", id.toString());
        }
        invitationRepository.delete(invitation);
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
    public List<Response> getResponsesByInvitation(Invitation invitation) throws EntityNotFoundException {
        return invitation.getResponses();
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
                response -> Date.from(Instant.parse(response.getInvitation().getPreferredStart())).before(now));

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
