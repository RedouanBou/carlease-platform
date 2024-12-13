package com.nl.sogeti.customerservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    @NotNull
    private String street;

    @NotNull
    private int houseNumber;

    @NotNull
    private String zipCode;

    @NotNull
    private String place;

    @NotNull
    @Email
    private String emailAddress;

    @NotNull
    private String phoneNumber;

}