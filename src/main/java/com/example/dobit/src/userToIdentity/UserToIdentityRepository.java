package com.example.dobit.src.userToIdentity;

import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userToIdentity.models.UserToIdentity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserToIdentityRepository extends CrudRepository<UserToIdentity, Integer> {
    List<UserToIdentity> findByUserInfoAndStatus(UserInfo userInfo, String active);
}