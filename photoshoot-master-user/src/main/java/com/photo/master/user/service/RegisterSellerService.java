package com.photo.master.user.service;

import com.photo.master.data.dto.request.user.RequestRegisterUserDto;
import com.photo.master.data.dto.response.user.ResponseRegisterUserDto;

public interface RegisterSellerService {
    ResponseRegisterUserDto registerSeller(RequestRegisterUserDto requestRegisterUserDto);
    ResponseRegisterUserDto registerSeller1();

}
