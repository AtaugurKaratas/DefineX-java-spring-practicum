package com.definex.credit.model;

import com.definex.credit.model.abstraction.Auditable;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditRating extends Auditable implements Serializable {

    private int creditScoreStartingValue;

    private double creditProductPaymentHabits;

    private double currentAccountAndDebitStatus;

    private double newCreditProductLaunches;

    private double creditUsageIntensity;
}
