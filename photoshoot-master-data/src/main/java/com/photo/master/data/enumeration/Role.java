package com.photo.master.data.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

import static com.photo.master.data.enumeration.Permission.*;

@RequiredArgsConstructor
public enum Role {
    USER(Collections.EMPTY_SET),
    ADMIN(
            Set.of(
                    ADMIN_UPDATE,
                    ADMIN_READ,
                    ADMIN_CREATE,
                    ADMIN_DELETE,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE,
                    MANAGER_READ
            )
    ),
    SELLER(
            Set.of(
                    SELLER_UPDATE,
                    SELLER_READ,
                    SELLER_CREATE,
                    SELLER_DELETE,
                    ADMIN_UPDATE,
                    ADMIN_READ,
                    ADMIN_CREATE,
                    ADMIN_DELETE,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE,
                    MANAGER_READ
            )
    ),
    MANAGER(
            Set.of(
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE,
                    MANAGER_READ
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
       var authorities =  getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toList());
       authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
       return authorities;
    }
}
