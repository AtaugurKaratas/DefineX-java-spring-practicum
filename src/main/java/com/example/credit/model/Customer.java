package com.example.credit.model;

import com.example.credit.model.abstraction.BaseEntitySecure;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer extends BaseEntitySecure implements Serializable {

    private String name;

    private String surname;

    private String phoneNumber;

    @CreationTimestamp
    private Instant createDate;

    @UpdateTimestamp
    private Instant updateDate;

    private BigDecimal monthlySalary;

    private String imagePath;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "auth_id", referencedColumnName = "id")
    private Auth auth;

}
