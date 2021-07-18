package com.example.dobit.src.dontHabitCheck;

import com.example.dobit.src.dontHabit.models.DontHabit;
import com.example.dobit.src.dontHabitCheck.models.DontHabitCheck;
import com.example.dobit.src.user.models.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DontHabitCheckRepository extends CrudRepository<DontHabitCheck, Integer> {

    Boolean existsByUserInfoAndDontHabitAndYearAndMonthAndDayAndStatus(UserInfo userInfo, DontHabit dontHabit, int year, int month, int day, String active);

    DontHabitCheck findByUserInfoAndDontHabitAndYearAndMonthAndDayAndStatus(UserInfo userInfo, DontHabit dontHabit, int year, int month, int day, String active);
}
