package com.photo.master.data.model.reservation;

import com.photo.master.data.model.base.ABaseAuditAwareMaster;
import com.photo.master.data.model.base.ABaseAuditTrail;
import com.photo.master.data.model.user.AuthUser;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "slot", schema = "master_reservation")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Slot extends ABaseAuditAwareMaster implements Serializable {
    @Serial
    private static final long serialVersionUID = -5685555676741830720L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "master_reservation.slot_id_seq")
    @SequenceGenerator(name = "master_reservation.slot_id_seq", sequenceName = "master_reservation.slot_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AuthUser user;

    @Column(name = "customer_id")
    private String customer;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "status")
    private String status;

    @Column(name = "is_available")
    private Boolean isAvailable = true;

}
