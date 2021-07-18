package com.example.dobit.src.doHabitCheck;

import com.example.dobit.src.doHabit.models.DoHabit;
import com.example.dobit.src.doHabitCheck.models.DoHabitCheck;
import com.example.dobit.src.user.models.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DoHabitCheckRepository extends CrudRepository<DoHabitCheck, Integer> {


    Boolean existsByUserInfoAndDoHabitAndStatus(UserInfo userInfo, DoHabit doHabit, String active);

    DoHabitCheck findByUserInfoAndDoHabitAndStatus(UserInfo userInfo, DoHabit doHabit, String active);
}
