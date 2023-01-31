package com.example.projekt.repositories;

import com.example.projekt.models.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserDao, Long>, JpaRepository<UserDao, Long> {

    UserDao findByUsername(String username);
    UserDao findUserDaoById(Long id);

    @Query(
            "select " +
            "u.id, u.username, u.password, u.firstname, u.lastname, u.age, u.phoneNumber, u.emailAddress, u.isArchived, " +
            "a.street, a.apartmentNumber, a.city, a.zipCode, a.state, r.name " +
            "from UserDao u " +
            "inner join AddressDao a on a.id = u.id " +
            "inner join UserRoleDao r on r.id = u.id"
    )
    List<UserDao> findAllUsers();
}
