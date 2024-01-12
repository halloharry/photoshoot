package com.photo.master.data.dao;

import com.photo.master.data.model.user.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IAuthTokenDao extends JpaRepository<AuthToken, Long> {

    @Query(""" 
            select t from AuthToken t join AuthUser u on t.authUser.id = u.id
            where u.id = :userId and (t.expired = false or t.revoked = false )
            """)
    List<AuthToken> findAllByValidTokenByUser(Long userId);

    Optional<AuthToken> findByToken(String token);
}
