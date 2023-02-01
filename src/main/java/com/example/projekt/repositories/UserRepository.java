package com.example.projekt.repositories;

import com.example.projekt.daos.UserDao;
import com.example.projekt.models.TaskDto;
import com.example.projekt.models.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<UserDao, Long>, JpaRepository<UserDao, Long> {

    UserDao findByUsername(String username);
    UserDao findUserDaoById(Long id);

    @Query("select new com.example.projekt.models.UserDto(u.id, u.username, u.password, u.firstname, u.lastname, u.age, u.phoneNumber, u.emailAddress, u.isArchived, " +
            "u.address.street, u.address.apartmentNumber, u.address.city, u.address.zipCode, u.address.state, u.role.id, u.role.name)" +
            "from UserDao u")
    List<UserDto> findAllUsers();

    @Query("select new com.example.projekt.models.UserDto(u.id, u.username, u.password, u.firstname, u.lastname, u.age, u.phoneNumber, u.emailAddress, u.isArchived, " +
            "u.address.street, u.address.apartmentNumber, u.address.city, u.address.zipCode, u.address.state, u.role.id, u.role.name)" +
            "from UserDao u WHERE u.id = :id")
    UserDto findUserDtoById(@Param("id") Long id);

    @Query("select new com.example.projekt.models.UserDto(u.id, u.username, u.password, u.firstname, u.lastname, u.age, u.phoneNumber, u.emailAddress, u.isArchived, " +
            "u.address.street, u.address.apartmentNumber, u.address.city, u.address.zipCode, u.address.state, u.role.id, u.role.name)" +
            "from UserDao u WHERE u.username = :username")
    UserDto findUserDtoByUsername(@Param("username") String username);

}
