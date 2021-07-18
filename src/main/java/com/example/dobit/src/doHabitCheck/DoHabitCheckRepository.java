package com.example.dobit.src.doHabitCheck;

import com.example.dobit.src.doHabit.models.DoHabit;
import com.example.dobit.src.doHabitCheck.models.DoHabitCheck;
import com.example.dobit.src.user.models.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DoHabitCheckRepository extends CrudRepository<DoHabitCheck, Integer> {

    Boolean existsByUserInfoAndDoHabitAndYearAndMonthAndDayAndStatus(UserInfo userInfo, DoHabit doHabit, int year, int month, int day, String active);

    DoHabitCheck findByUserInfoAndDoHabitAndYearAndMonthAndDayAndStatus(UserInfo userInfo, DoHabit doHabit, int year, int month, int day, String active);
}
