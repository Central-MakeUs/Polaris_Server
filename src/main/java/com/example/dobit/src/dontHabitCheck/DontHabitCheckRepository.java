package com.example.dobit.src.dontHabitCheck;

import com.example.dobit.src.dontHabit.models.DontHabit;
import com.example.dobit.src.dontHabitCheck.models.DontHabitCheck;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userIdentity.models.UserIdentity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface DontHabitCheckRepository extends CrudRepository<DontHabitCheck, Integer> {

    Boolean existsByUserInfoAndDontHabitAndYearAndMonthAndDayAndStatus(UserInfo userInfo, DontHabit dontHabit, int year, int month, int day, String active);

    DontHabitCheck findByUserInfoAndDontHabitAndYearAndMonthAndDayAndStatus(UserInfo userInfo, DontHabit dontHabit, int year, int month, int day, String active);

    List<DontHabitCheck> findByUserInfoAndUserIdentityAndYearAndMonthAndStatus(UserInfo userInfo, UserIdentity userIdentity, int year, int month, String active);

    long countByUserInfoAndUserIdentityAndStatus(UserInfo userInfo, UserIdentity userIdentity, String active);

    @Query(value = "SELECT checkDate FROM DoHabitCheck Where userIdx = ? AND userIdentityIdx = ? AND status='ACTIVE' ORDER BY checkDate DESC LIMIT 1;", nativeQuery=true)
    Date findByTheLatestDate(UserInfo userInfo, UserIdentity userIdentity);

}
