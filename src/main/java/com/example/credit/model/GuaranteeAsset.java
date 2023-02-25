package com.example.credit.model;

import com.example.credit.model.abstraction.Auditable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuaranteeAsset extends Auditable implements Serializable {

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_asset_id", referencedColumnName = "id")
    private CustomerAsset customerAsset;

    private BigDecimal guaranteeAmount;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "credit_id", referencedColumnName = "id")
    private Credit credit;
}
