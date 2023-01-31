package com.example.projekt.repositories;

import com.example.projekt.models.AddressDao;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<AddressDao, Long> {

    AddressDao findAddressDaoById(Long id);
}
