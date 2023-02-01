package com.example.projekt.daos;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "user")
public class UserDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private Integer age;

    @Column
    private String phoneNumber;

    @Column
    private String emailAddress;

    @Column(columnDefinition="tinyint(1)")
    private Boolean isArchived;

    @JoinColumn(name="address_id")
    @OneToOne(cascade = CascadeType.ALL)
    private AddressDao address;

    @JoinColumn(name="role_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private UserRoleDao role;

}
