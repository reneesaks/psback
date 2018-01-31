package com.perfectstrangers.service;

import com.perfectstrangers.domain.Hotel;
import com.perfectstrangers.domain.User;

import java.util.List;


public interface GenericService {

    List<User> getAllUsers();

    List<Hotel> getAllHotels();
}
