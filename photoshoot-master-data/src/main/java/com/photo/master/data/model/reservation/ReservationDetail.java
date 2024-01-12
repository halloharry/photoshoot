package com.photo.master.data.model.reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.photo.master.data.model.base.ABaseAuditAwareMaster;
import com.photo.master.data.model.base.ABaseAuditTrail;
import com.photo.master.data.model.user.AuthUser;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservation_detail", schema = "master_reservation")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
public class ReservationDetail extends ABaseAuditAwareMaster implements Serializable {
    @Serial
    private static final long serialVersionUID = -5685555676741830720L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "master_reservation.reservation_detail_id_seq")
    @SequenceGenerator(name = "master_reservation.reservation_detail_id_seq", sequenceName = "master_reservation.reservation_detail_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private AuthUser user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Column(name = "customer_id")
    private String customer;

    @Column(name = "start_time", columnDefinition = "TIME WITH TIME ZONE")
    private LocalTime startTime;

    @Column(name = "end_time", columnDefinition = "TIME WITH TIME ZONE")
    private LocalTime endTime;

    @Column(name = "reference_id")
    private String referenceId;

    @Column(name = "phone_number")
    private String phoneNumber;
}
