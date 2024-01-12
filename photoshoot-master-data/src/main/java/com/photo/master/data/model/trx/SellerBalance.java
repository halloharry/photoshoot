package com.photo.master.data.model.trx;

import com.photo.master.data.model.base.ABaseAuditAwareMaster;
import com.photo.master.data.model.user.AuthUser;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seller_balance", schema = "auth_security")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
public class SellerBalance extends ABaseAuditAwareMaster implements Serializable {

    @Serial
    private static final long serialVersionUID = -5685555676741830720L;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AuthUser userId;

    @Column(name = "latest_balance", nullable = false)
    @NotNull(message = "Name Field of Entity is Required")
    private Integer latestBalance;

    @Column(name = "last_update")
    private LocalDate lastUpdate;



}
