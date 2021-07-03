package com.example.dobit.src.identity;

import com.example.dobit.src.identity.models.Identity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface IdentityRepository extends CrudRepository<Identity, Integer> {
    List<Identity> findByStatus(String active);
}