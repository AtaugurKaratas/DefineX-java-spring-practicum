package com.example.credit.model;

import com.example.credit.model.abstraction.Auditable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerCreditRating extends Auditable implements Serializable {
    private double creditProductPaymentHabitScore;

    private double currentAccountAndDebitStatusScore;

    private double newCreditProductLaunchesScore;

    private double creditUsageIntensityScore;

    @NotNull
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer customer;
}
