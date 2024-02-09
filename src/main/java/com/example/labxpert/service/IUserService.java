package com.example.labxpert.service;


import com.example.labxpert.dtos.UserDto;
import com.example.labxpert.model.User;

import java.util.List;

public interface IUserService {

    UserDto add(UserDto userDto);
    UserDto update(Long id, UserDto userDto);
    void delete(Long id);
    List<UserDto> getAll();
    UserDto getById(Long id);
    UserDto getByName(String name);
    String getByEmail(String email);
    void checkExistEmail(UserDto userDto);
    void SiNoEqualCheckEmailExist(User userExist, UserDto userDto);
    void validation(UserDto userDto);
    UserDto loadUserByEmail(String email);
}
