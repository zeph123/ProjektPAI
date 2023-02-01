package com.example.projekt.daos;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_address")
public class AddressDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String street; // ulica

    @Column
    private String apartmentNumber; // numer domu

    @Column
    private String city; // miasto

    @Column
    private String zipCode; // kod pocztowy

    @Column
    private String state; // panstwo

    @OneToOne(mappedBy = "address")
    private UserDao user;
}
