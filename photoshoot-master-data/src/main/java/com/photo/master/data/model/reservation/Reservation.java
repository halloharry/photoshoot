package com.photo.master.data.model.reservation;

import com.photo.master.data.model.base.ABaseAuditAwareMaster;
import com.photo.master.data.model.user.AuthUser;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservation", schema = "master_reservation")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Reservation extends ABaseAuditAwareMaster implements Serializable {
    @Serial
    private static final long serialVersionUID = -5685555676741830720L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "master_reservation.reservation_id_seq")
    @SequenceGenerator(name = "master_reservation.reservation_id_seq", sequenceName = "master_reservation.reservation_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AuthUser user;

    @Column(name = "reservation_number")
    private String reservationNumber;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(mappedBy = "reservation", orphanRemoval = true)
    private List<ReservationDetail> reservationDetails;

}
