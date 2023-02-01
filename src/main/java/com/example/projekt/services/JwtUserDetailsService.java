package com.example.projekt.services;

import com.example.projekt.enums.UserRole;
import com.example.projekt.daos.AddressDao;
import com.example.projekt.daos.UserDao;
import com.example.projekt.models.UserDto;
import com.example.projekt.daos.UserRoleDao;
import com.example.projekt.repositories.AddressRepository;
import com.example.projekt.repositories.UserRepository;
import com.example.projekt.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDao user = userRepository.findByUsername(username);
        if (user != null) {
            return new User(
                    user.getUsername(),
                    user.getPassword(),
                    new ArrayList<>()
            );
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }

    public boolean save(UserDto user) {
        UserDao userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            // nie udalo sie zapisac uzytkownika w bazie
            // uzytkownik o podanej nazwie juz istnieje w bazie
            return false;
        }

        // uzytkownik o podanej nazwie nie istnieje w bazie
        // nastepuje zapis uzytkownika w bazie

        AddressDao newUserAddress = new AddressDao();
        newUserAddress.setStreet(user.getStreet());
        newUserAddress.setApartmentNumber(user.getApartmentNumber());
        newUserAddress.setZipCode(user.getZipCode());
        newUserAddress.setCity(user.getCity());
        newUserAddress.setState(user.getState());
        addressRepository.save(newUserAddress);

        UserDao newUser = new UserDao();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        newUser.setAge(user.getAge());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setEmailAddress(user.getEmailAddress());
        newUser.setIsArchived(false);
        newUser.setAddress(newUserAddress);

        Long roleId = (long) UserRole.ROLE_USER.value();
        UserRoleDao userRoleFromDb = roleRepository.findUserRoleDaoById(roleId);
        newUser.setRole(userRoleFromDb);

        userRepository.save(newUser);

        return true;
    }

}