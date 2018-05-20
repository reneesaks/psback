package com.professionalstrangers.controller.priv;

import com.professionalstrangers.domain.Hotel;
import com.professionalstrangers.domain.Resto;
import com.professionalstrangers.dto.HotelDTO;
import com.professionalstrangers.error.EntityNotFoundException;
import com.professionalstrangers.service.GenericService;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private/hotel")
public class HotelController {

    private GenericService genericService;

    @Autowired
    public HotelController(GenericService genericService) {
        this.genericService = genericService;
    }

    /**
     * Get all hotels as a list.
     *
     * @return invitation list.
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Hotel> getHotels() {
        return genericService.getAllHotels();
    }

    /**
     * Get one hotel by id.
     *
     * @param hotelId id of an existing hotel.
     * @return hotel object.
     * @throws EntityNotFoundException when hotel with given id is not found.
     */
    @GetMapping("{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public Hotel getHotel(@PathVariable("hotelId") Long hotelId) throws EntityNotFoundException {
        return genericService.getHotelById(hotelId);
    }

    /**
     * Create new hotel.
     *
     * @param hotelDTO hotel object.
     * @return created hotel object.
     */
    @PostMapping("new")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @ApiOperation(value = "ADMIN_ONLY")
    @ResponseStatus(HttpStatus.OK)
    public Hotel newHotel(@RequestBody @Valid HotelDTO hotelDTO) {

        Hotel hotel = new Hotel();

        hotel.setName(hotelDTO.getName());
        hotel.setWebpage(hotelDTO.getWebpage());
        hotel.setCountry(hotelDTO.getCountry());
        hotel.setState(hotelDTO.getState());
        hotel.setCity(hotelDTO.getCity());
        hotel.setAddress(hotelDTO.getAddress());
        hotel.setZipCode(hotelDTO.getZipCode());
        hotel.setRestos(hotelDTO.getRestos());

        genericService.saveHotel(hotel);
        return hotel;
    }

    /**
     * Update an existing hotel.
     *
     * @param hotelDTO hotel object.
     * @param hotelId id of an existing hotel.
     * @return updated hotel object.
     * @throws EntityNotFoundException when hotel with given id is not found.
     */
    @PutMapping("update/{hotelId}")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @ApiOperation(value = "ADMIN_ONLY")
    @ResponseStatus(HttpStatus.OK)
    public Hotel updateHotel(
            @RequestBody @Valid HotelDTO hotelDTO,
            @PathVariable("hotelId") Long hotelId
    ) throws EntityNotFoundException {

        Hotel hotel = genericService.getHotelById(hotelId);

        hotel.setName(hotelDTO.getName());
        hotel.setWebpage(hotelDTO.getWebpage());
        hotel.setCountry(hotelDTO.getCountry());
        hotel.setState(hotelDTO.getState());
        hotel.setCity(hotelDTO.getCity());
        hotel.setAddress(hotelDTO.getAddress());
        hotel.setZipCode(hotelDTO.getZipCode());

        List<Resto> existingRestos = hotel.getRestos();
        List<Resto> updatedRestos = hotelDTO.getRestos();
        List<Resto> newRestos = new ArrayList<>();

        for (Resto resto : existingRestos) {
            resto.setHotel(null);
        }

        for (Resto resto : updatedRestos) {
            Resto restoToAdd = genericService.getRestoById(resto.getId());
            restoToAdd.setHotel(hotel);
            newRestos.add(restoToAdd);
        }

        hotel.setRestos(newRestos);
        genericService.saveHotel(hotel);
        return hotel;
    }

    /**
     * Delete an existing hotel.
     *
     * @param hotelId id of an existing invitation.
     * @throws EntityNotFoundException when hotel with given id is not found.
     */
    @DeleteMapping("delete/{hotelId}")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @ApiOperation(value = "ADMIN_ONLY")
    @ResponseStatus(HttpStatus.OK)
    public void deleteHotel(@PathVariable("hotelId") Long hotelId) throws EntityNotFoundException {
        Hotel hotel = genericService.getHotelById(hotelId);
        List<Resto> restos = hotel.getRestos();

        for (Resto resto : restos) {
            resto.setHotel(null);
        }

        genericService.deleteHotel(hotel);
    }
}
