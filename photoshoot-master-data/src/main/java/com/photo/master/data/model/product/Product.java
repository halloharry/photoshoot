package com.photo.master.data.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.photo.master.data.model.base.ABaseAuditAwareMaster;
import com.photo.master.data.model.base.ABaseAuditTrail;
import com.photo.master.data.model.user.AuthUser;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product", schema = "master_product")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product extends ABaseAuditAwareMaster implements Serializable {
    @Serial
    private static final long serialVersionUID = -5685555676741830720L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "master_product.product_id_seq")
    @SequenceGenerator(name = "master_product.product_id_seq", sequenceName = "master_product.product_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "product_name", length = 250, unique = true, nullable = false)
    @NotNull(message = "Name Field of Entity is Required")
    private String productName;

    @Column(name = "price")
    private Integer price;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private AuthUser user;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "available_status")
    private String available_status;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "description")
    private String description;

}
