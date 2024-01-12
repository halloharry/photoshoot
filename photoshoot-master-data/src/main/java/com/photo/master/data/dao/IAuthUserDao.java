package com.photo.master.data.dao;

import com.photo.master.data.model.user.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface IAuthUserDao extends JpaRepository<AuthUser, Long> {

    AuthUser findAuthSellerByUsernameLike(@NotNull(message = "Username Field of Entity is Required") String username);

    AuthUser findByEmail(String email);
    AuthUser findByUserProfilePhoneNumber(@NotNull(message = "Phone Number Field of Entity is Required") String userPhoneNumber);

}
