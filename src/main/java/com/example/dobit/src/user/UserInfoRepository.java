package com.example.dobit.src.user;

import com.example.dobit.src.user.models.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Integer> {
    List<UserInfo> findByEmailAndStatus(String email, String status);
}