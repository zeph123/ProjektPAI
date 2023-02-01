package com.example.projekt.services;

import com.example.projekt.daos.AddressDao;
import com.example.projekt.daos.UserDao;
import com.example.projekt.models.UserDto;
import com.example.projekt.daos.UserRoleDao;
import com.example.projekt.repositories.AddressRepository;
import com.example.projekt.repositories.UserRepository;
import com.example.projekt.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRoleRepository roleRepository;

    public List<UserDto> getAllUser() {
        return (List<UserDto>) userRepository.findAllUsers();
    }

    public UserDao getUserById(String id) {
        Long userId = Long.parseLong(id);
        if (userRepository.existsById(userId)) {
            return userRepository.findUserDaoById(userId);
        }
        return null;
    }

    public UserDto getUserDtoById(String id) {
        Long userId = Long.parseLong(id);
        if (userRepository.existsById(userId)) {
            return userRepository.findUserDtoById(userId);
        }
        return null;
    }

    public UserDao getUserByUsername(String username) {
        UserDao userFromDb = userRepository.findByUsername(username);
        if (userFromDb != null) {
            return userFromDb;
        }
        return null;
    }

    public UserDto getUserDtoByUsername(String username) {
        UserDto userFromDb = userRepository.findUserDtoByUsername(username);
        if (userFromDb != null) {
            return userFromDb;
        }
        return null;
    }

    public boolean archiveSelectedUserById(String id) {
        UserDao userFromDb = getUserById(id);
        if (userFromDb != null) {
            userFromDb.setIsArchived(true);
            userRepository.save(userFromDb);
            return true;
        }
        return false;
    }

    public boolean unarchiveSelectedUserById(String id) {
        UserDao userFromDb = getUserById(id);
        if (userFromDb != null) {
            userFromDb.setIsArchived(false);
            userRepository.save(userFromDb);
            return true;
        }
        return false;
    }

    public boolean updateSelectedUserById(UserDto user, String id) {
        UserDao userFromDb = getUserById(id);
        if (userFromDb != null) {
            // aktualizacja danych uzytkownika
            userFromDb.setFirstname(user.getFirstname());
            userFromDb.setLastname(user.getLastname());
            userFromDb.setAge(user.getAge());
            userFromDb.setPhoneNumber(user.getPhoneNumber());
            userFromDb.setEmailAddress(user.getEmailAddress());
            userRepository.save(userFromDb);
            // aktualizacja danych adresu uzytkownika
            Long addressId = userFromDb.getAddress().getId();
            AddressDao userAddressFromDb = addressRepository.findAddressDaoById(addressId);
            userAddressFromDb.setStreet(user.getStreet());
            userAddressFromDb.setApartmentNumber(user.getApartmentNumber());
            userAddressFromDb.setZipCode(user.getZipCode());
            userAddressFromDb.setCity(user.getCity());
            userAddressFromDb.setState(user.getState());
            addressRepository.save(userAddressFromDb);
            return true;
        }
        return false;
    }

    public UserRoleDao getRoleById(String id) {
        Long roleId = Long.parseLong(id);
        if (roleRepository.existsById(roleId)) {
            return roleRepository.findUserRoleDaoById(roleId);
        }
        return null;
    }

    public boolean changeSelectedUserRoleById(String id, UserRoleDao role) {
        UserDao userFromDb = getUserById(id);
        if (userFromDb != null) {
            userFromDb.setRole(role);
            userRepository.save(userFromDb);
            return true;
        }
        return false;
    }

    public boolean archiveLoggedUser(String username) {
        UserDao userFromDb = getUserByUsername(username);
        if (userFromDb != null) {
            userFromDb.setIsArchived(true);
            userRepository.save(userFromDb);
            return true;
        }
        return false;
    }

    public boolean updateLoggedUser(UserDto user, String username) {
        UserDao userFromDb = getUserByUsername(username);
        if (userFromDb != null) {
            // aktualizacja danych uzytkownika
            userFromDb.setFirstname(user.getFirstname());
            userFromDb.setLastname(user.getLastname());
            userFromDb.setAge(user.getAge());
            userFromDb.setPhoneNumber(user.getPhoneNumber());
            userFromDb.setEmailAddress(user.getEmailAddress());
            userRepository.save(userFromDb);
            // aktualizacja danych adresu uzytkownika
            Long addressId = userFromDb.getAddress().getId();
            AddressDao userAddressFromDb = addressRepository.findAddressDaoById(addressId);
            userAddressFromDb.setStreet(user.getStreet());
            userAddressFromDb.setApartmentNumber(user.getApartmentNumber());
            userAddressFromDb.setZipCode(user.getZipCode());
            userAddressFromDb.setCity(user.getCity());
            userAddressFromDb.setState(user.getState());
            addressRepository.save(userAddressFromDb);
            return true;
        }
        return false;
    }

}
