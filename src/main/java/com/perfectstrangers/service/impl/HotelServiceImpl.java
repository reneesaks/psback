package com.perfectstrangers.service.impl;

import com.perfectstrangers.domain.Hotel;
import com.perfectstrangers.repository.HotelRepository;
import com.perfectstrangers.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Hotel getHotelByName(String name) {
        return hotelRepository.findByName(name);
    }

}
