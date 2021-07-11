package com.example.dobit.src.userIdentityColor;

import com.example.dobit.src.userIdentityColor.models.UserIdentityColor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserIdentityColorRepository extends CrudRepository<UserIdentityColor, Integer> {
    List<UserIdentityColor> findByStatus(String active);
}