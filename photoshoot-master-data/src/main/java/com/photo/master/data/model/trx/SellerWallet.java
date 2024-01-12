package com.photo.master.data.model.trx;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.photo.master.data.model.base.ABaseAuditAwareMaster;
import com.photo.master.data.model.base.ABaseAuditTrail;
import com.photo.master.data.model.user.AuthUser;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seller_wallet", schema = "auth_security")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
public class SellerWallet extends ABaseAuditAwareMaster implements Serializable {
    @Serial
    private static final long serialVersionUID = -5685555676741830720L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_security.wallet_id_seq")
    @SequenceGenerator(name = "auth_security.wallet_id_seq", sequenceName = "auth_security.wallet_id_seq", allocationSize = 1)
    @Column(name = "wallet_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private AuthUser user;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "amount")
    private String amount;

    @Column(name = "description")
    private String description;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "status")
    private String status;

    @Column(name = "reference_id")
    private String referenceId;
}
