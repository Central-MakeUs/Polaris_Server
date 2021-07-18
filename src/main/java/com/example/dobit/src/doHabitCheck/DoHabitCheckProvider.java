package com.example.dobit.src.doHabitCheck;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.doHabit.models.DoHabit;
import com.example.dobit.src.doHabitCheck.models.DoHabitCheck;
import com.example.dobit.src.user.models.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;

import static com.example.dobit.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class DoHabitCheckProvider {
    private final DoHabitCheckRepository doHabitCheckRepository;

    /**
     * doHabitCheck 존재여부
     * @param userInfo, doHabit
     * @return Boolean
     * @throws BaseException
     */
    public Boolean retrieveExistingDoHabitCheckToCurrentDate(UserInfo userInfo, DoHabit doHabit) throws BaseException {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        Boolean existDoHabitCheck;
        try {
            existDoHabitCheck = doHabitCheckRepository.existsByUserInfoAndDoHabitAndYearAndMonthAndDayAndStatus(userInfo,doHabit,year,month,day,"ACTIVE");
        }catch (Exception e){
            throw new BaseException(FAIED_TO_EXIST_BY_USERINFO_AND_DOHABIT_AND_DATE_AND_STATUS);
        }


        return existDoHabitCheck;
    }

    /**
     * userInfo, doHabit, 현재날짜로 DoHabitCheck 조회
     * @param userInfo, doHabit
     * @return DoHabitCheck
     * @throws BaseException
     */
    public DoHabitCheck retrieveDoHabitCheckByUserInfoAndDoHabitToCurrentDate(UserInfo userInfo, DoHabit doHabit) throws BaseException{
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DoHabitCheck doHabitCheck;
        try{
            doHabitCheck =doHabitCheckRepository.findByUserInfoAndDoHabitAndYearAndMonthAndDayAndStatus(userInfo,doHabit,year,month,day,"ACTIVE");
        }catch (Exception e){
            throw new BaseException(FAILED_TO_FIND_BY_USERINFO_AND_DOHABIT_AND_DATE_AND_STATUS);
        }
        return doHabitCheck;
    }

}
