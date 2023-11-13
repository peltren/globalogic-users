package com.globallogic.users.infrastructure.output.repositories;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Phones")
@Data
public class UserPhoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long number;

    private Integer cityCode;

    private String countryCode;
}
