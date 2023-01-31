package com.example.projekt.repositories;

import com.example.projekt.models.UserRoleDao;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRoleDao, Long> {

    UserRoleDao findUserRoleDaoById(Long id);
}
