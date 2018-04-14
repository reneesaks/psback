package com.perfectstrangers.controller.priv;

import com.perfectstrangers.domain.Hotel;
import com.perfectstrangers.dto.HotelDTO;
import com.perfectstrangers.error.EntityNotFoundException;
import com.perfectstrangers.service.GenericService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
     * @return advert list.
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Hotel> getHotels() {
        return genericService.getAllHotels();
    }

    /**
     * Get one hotel by id.
     *
     * @param id id of an existing hotel.
     * @return hotel object.
     * @throws EntityNotFoundException when hotel with given id is not found.
     */
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Hotel getHotel(@PathVariable("id") Long id) throws EntityNotFoundException {
        return genericService.getHotelById(id);
    }

    /**
     * Create new hotel.
     *
     * @param hotelDTO hotel object.
     */
    @PostMapping("new")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @ApiOperation(value = "newHotel ADMIN_ONLY")
    @ResponseStatus(HttpStatus.OK)
    public void newHotel(@RequestBody @Valid HotelDTO hotelDTO) {

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
    }

    /**
     * Update existing hotel.
     *
     * @param hotelDTO hotel object.
     * @param id id of an existing hotel.
     * @throws EntityNotFoundException when hotel with given id is not found.
     */
    @PutMapping("update/{id}")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @ApiOperation(value = "updateHotel ADMIN_ONLY")
    @ResponseStatus(HttpStatus.OK)
    public void editHotel(
            @RequestBody @Valid HotelDTO hotelDTO,
            @PathVariable("id") Long id
    ) throws EntityNotFoundException {

        Hotel hotel = genericService.getHotelById(id);

        hotel.setName(hotelDTO.getName());
        hotel.setWebpage(hotelDTO.getWebpage());
        hotel.setCountry(hotelDTO.getCountry());
        hotel.setState(hotelDTO.getState());
        hotel.setCity(hotelDTO.getCity());
        hotel.setAddress(hotelDTO.getAddress());
        hotel.setZipCode(hotelDTO.getZipCode());
        hotel.setRestos(hotelDTO.getRestos());

        genericService.saveHotel(hotel);
    }

    /**
     * Delete an existing hotel.
     *
     * @param id id of an existing advert.
     * @throws EntityNotFoundException when hotel with given id is not found.
     */
    @PutMapping("delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @ApiOperation(value = "deleteHotel ADMIN_ONLY")
    @ResponseStatus(HttpStatus.OK)
    public void deleteHotel(@PathVariable("id") Long id) throws EntityNotFoundException {
        Hotel hotel = genericService.getHotelById(id);
        genericService.deleteHotel(hotel);
    }
}
