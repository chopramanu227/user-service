package com.example.user.persistence.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name= "UserAddress")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long addressId;
    private String street;
    private String city;
    private String state;
    private String postcode;

    @OneToOne(mappedBy = "address")
    private UserEntity userEntity;
}
