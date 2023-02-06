package com.example.credit.model;

import com.example.credit.model.abstraction.BaseEntitySecure;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auth extends BaseEntitySecure implements Serializable {

    private String identityNumber;

    private String email;

    private String password;

    private String roles;

    @CreationTimestamp
    private Instant createDate;

    @UpdateTimestamp
    private Instant updateDate;

    private boolean is_check = true;
}
