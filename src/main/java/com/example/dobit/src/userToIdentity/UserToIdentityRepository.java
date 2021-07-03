package com.example.dobit.src.userToIdentity;

import com.example.dobit.src.userToIdentity.models.UserToIdentity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserToIdentityRepository extends CrudRepository<UserToIdentity, Integer> {
}