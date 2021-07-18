package com.example.dobit.src.dontHabitCheck;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.doHabitCheck.models.DoHabitCheck;
import com.example.dobit.src.dontHabit.DontHabitProvider;
import com.example.dobit.src.dontHabit.models.DontHabit;
import com.example.dobit.src.dontHabitCheck.models.DontHabitCheck;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userIdentity.models.UserIdentity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

import static com.example.dobit.config.BaseResponseStatus.*;


@Service
@RequiredArgsConstructor
public class DontHabitCheckProvider {
    private final DontHabitCheckRepository dontHabitCheckRepository;

    /**
     * dontHabitCheck 존재여부
     * @param userInfo, dontHabit
     * @return Boolean
     * @throws BaseException
     */
    public Boolean retrieveExistingDontHabitCheckToCurrentDate(UserInfo userInfo, DontHabit dontHabit) throws BaseException {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        Boolean existDontHabitCheck;
        try {
            existDontHabitCheck = dontHabitCheckRepository.existsByUserInfoAndDontHabitAndYearAndMonthAndDayAndStatus(userInfo,dontHabit,year,month,day,"ACTIVE");
        }catch (Exception e){
            throw new BaseException(FAIED_TO_EXIST_BY_USERINFO_AND_DONTHABIT_AND_DATE_AND_STATUS);
        }


        return existDontHabitCheck;
    }

    /**
     * userInfo, dontHabit,현재날짜로 DontHabitCheck 조회
     * @param userInfo, dontHabit
     * @return DontHabitCheck
     * @throws BaseException
     */
    public DontHabitCheck retrieveDontHabitCheckByUserInfoAndDontHabitToCurrentDate(UserInfo userInfo, DontHabit dontHabit) throws BaseException{
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        DontHabitCheck dontHabitCheck;
        try{
            dontHabitCheck =dontHabitCheckRepository.findByUserInfoAndDontHabitAndYearAndMonthAndDayAndStatus(userInfo,dontHabit,year,month,day,"ACTIVE");
        }catch (Exception e){
            throw new BaseException(FAILED_TO_FIND_BY_USERINFO_AND_DONTHABIT_AND_DATE_AND_STATUS);
        }
        return dontHabitCheck;
    }

    /**
     * userInfo, userIdentity로 DontHabitCheck 리스트 조회
     * @param userInfo, dontHabit
     * @return List<DontHabitCheck>
     * @throws BaseException
     */
    public List<DontHabitCheck> retrieveDontHabitCheckByUserInfoAndUserIdentityAndYearAndMonth(UserInfo userInfo, UserIdentity userIdentity,
                                                                                           int year, int month) throws BaseException{
        List<DontHabitCheck> dontHabitChecks;
        try {
            dontHabitChecks = dontHabitCheckRepository.findByUserInfoAndUserIdentityAndYearAndMonthAndStatus(userInfo,userIdentity,year,month,"ACTIVE");
        }catch (Exception e){
            throw new BaseException(FAILED_TO_FIND_BY_USERINFO_AND_USERIDENTITY_AND_DATE_AND_STATUS);
        }
        return dontHabitChecks;
    }

}