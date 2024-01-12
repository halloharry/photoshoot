package com.photo.master.data.dao;

import com.photo.master.data.model.user.AuthUserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthUserProfileDao extends JpaRepository<AuthUserProfile, Long> {
}
