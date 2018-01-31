package com.perfectstrangers.service.impl;

import com.perfectstrangers.domain.Hotel;
import com.perfectstrangers.domain.User;
import com.perfectstrangers.repository.HotelRepository;
import com.perfectstrangers.repository.UserRepository;
import com.perfectstrangers.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GenericServiceImpl implements GenericService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public List<User> getAllUsers() {
        return (List<User>)userRepository.findAll();
    }

    @Override
    public List<Hotel> getAllHotels() {
        return (List<Hotel>) hotelRepository.findAll();
    }
}
