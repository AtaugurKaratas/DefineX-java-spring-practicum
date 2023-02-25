package com.example.credit.model;

import com.example.credit.model.abstraction.Auditable;
import com.example.credit.model.enumeration.GuaranteeType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Credit extends Auditable implements Serializable {

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "guarantee_customer_id", referencedColumnName = "id")
    private GuaranteeCustomer guaranteeCustomer;

    private boolean guaranteeCustomerCheck;

    private boolean guaranteeCheck;

    @Enumerated(EnumType.STRING)
    private GuaranteeType guaranteeType;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "guarantee_asset_id", referencedColumnName = "id")
    private GuaranteeAsset guaranteeAsset;

    private boolean guaranteeAssetCheck;

    private boolean check;

    private BigDecimal price;

    private double creditScore;

    private LocalDate responseTime;
}
