package com.photo.master.data.model.user;

import com.photo.master.data.enumeration.Role;
import com.photo.master.data.model.base.ABaseAuditTrail;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
//import java.io.Serial;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auth_user",schema = "auth_security")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class AuthUser extends ABaseAuditTrail implements Serializable, UserDetails {
    @Serial
    private static final long serialVersionUID = -5685555676741830720L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_security.seller_id_seq")
    @SequenceGenerator(name = "auth_security.seller_id_seq", sequenceName = "auth_security.seller_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @BatchSize(size = 10)
    private AuthUserProfile userProfile;

    @Column(name = "username", length = 250, unique = true, nullable = false)
    @NotNull(message = "Username Field of Entity is Required")
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    @NotNull(message = "Username Field of Entity is Required")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "login_status")
    private Boolean loginStatus = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "authUser")
    private List<AuthToken> tokens;

    @Column(name = "mfa_enabled")
    private boolean mfaEnabled;

    @Column(name = "secret")
    private String secret;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
