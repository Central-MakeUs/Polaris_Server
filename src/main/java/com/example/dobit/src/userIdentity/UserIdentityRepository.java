package com.example.dobit.src.userIdentity;

import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userIdentity.models.UserIdentity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserIdentityRepository extends CrudRepository<UserIdentity, Integer> {

    List<UserIdentity> findByUserInfoAndStatus(UserInfo userInfo, String active);
}