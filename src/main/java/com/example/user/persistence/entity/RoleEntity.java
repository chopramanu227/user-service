package com.example.user.persistence.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Entity object to perform DB operation on Role table.
 */
@Entity
@Table(name= "Role")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roleId;
    private String name;

}
