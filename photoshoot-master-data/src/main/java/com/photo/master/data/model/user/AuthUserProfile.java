package com.photo.master.data.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.photo.master.data.model.base.ABaseAuditTrail;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auth_user_profile", schema = "auth_security")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class AuthUserProfile extends ABaseAuditTrail implements Serializable {
    @Serial
    private static final long serialVersionUID = -5685555676741830720L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_security.seller_profile_id_seq")
    @SequenceGenerator(name = "auth_security.seller_profile_id_seq", sequenceName = "auth_security.seller_profile_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", length = 250, nullable = false)
    @NotNull(message = "First Name Field of Entity is Required")
    private String firstName;

    @Column(name = "last_name", length = 250, nullable = false)
    @NotNull(message = "Last Name Field of Entity is Required")
    private String lastName;

    @Column(name = "phone_number", length = 250, unique = true, nullable = false)
    @NotNull(message = "Phone Number Field of Entity is Required")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "birth_date")
    private LocalDate birtDate;

    @Column(name = "gender")
    private String gender;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private AuthUser user;

}
