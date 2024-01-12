package com.photo.master.user.service.impl;

import com.photo.master.data.dao.IAuthUserDao;
import com.photo.master.data.dao.IAuthUserProfileDao;
import com.photo.master.data.dto.request.user.RequestRegisterUserDto;
import com.photo.master.data.dto.response.user.ResponseRegisterUserDto;
import com.photo.master.data.model.user.AuthUser;
import com.photo.master.data.model.user.AuthUserProfile;
import com.photo.master.user.service.RegisterSellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegisterSellerServiceImpl implements RegisterSellerService {

    private final IAuthUserDao authSellerDao;
    private final IAuthUserProfileDao authSellerProfileDao;

    @Override
    public ResponseRegisterUserDto registerSeller(RequestRegisterUserDto requestRegisterUserDto) {
        AuthUser seller = new AuthUser();
        seller.setUsername("k");
        seller.setEmail("hahaha");
        authSellerDao.save(seller);
        AuthUserProfile sellerProfile = new AuthUserProfile();
        sellerProfile.setFirstName("halo");
        sellerProfile.setLastName("joko");
        sellerProfile.setPhoneNumber("090909");
        authSellerProfileDao.save(sellerProfile);
        return null;
    }

    @Override
    public ResponseRegisterUserDto registerSeller1() {
        AuthUser seller = new AuthUser();
        seller.setUsername("k");
        seller.setEmail("hahaha");
        AuthUser saved = authSellerDao.save(seller);
        System.out.println(saved);
        AuthUserProfile sellerProfile = new AuthUserProfile();
        sellerProfile.setFirstName("halo");
        sellerProfile.setLastName("joko");
        sellerProfile.setPhoneNumber("090909");
        sellerProfile.setUser(saved);
        authSellerProfileDao.save(sellerProfile);
        return null;
    }
}
