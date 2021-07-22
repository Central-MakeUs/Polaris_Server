package com.example.dobit.src.doHabitCheck;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.doHabit.models.DoHabit;
import com.example.dobit.src.doHabitCheck.models.DoHabitCheck;
import com.example.dobit.src.dontHabitCheck.models.DontHabitCheck;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userIdentity.models.UserIdentity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    /**
     * userInfo, userIdentity로 DoHabitCheck 리스트 조회
     * @param userInfo, doHabit
     * @return List<DoHabitCheck>
     * @throws BaseException
     */
    public List<DoHabitCheck> retrieveDoHabitCheckByUserInfoAndUserIdentityAndYearAndMonth(UserInfo userInfo, UserIdentity userIdentity,
                                                                                           int year, int month) throws BaseException{
        List<DoHabitCheck> doHabitChecks;
        try {
            doHabitChecks = doHabitCheckRepository.findByUserInfoAndUserIdentityAndYearAndMonthAndStatus(userInfo,userIdentity,year,month,"ACTIVE");
        }catch (Exception e){
            throw new BaseException(FAILED_TO_FIND_BY_USERINFO_AND_USERIDENTITY_AND_DATE_AND_STATUS);
        }
        return doHabitChecks;
    }

    /**
     * userIdx,userIdentityIdx,status로 doHabitCheck 갯수 세기
     * @param userInfo, userIdentity
     * @return doHabitCheckCount
     * @throws BaseException
     */
    public long retrieveCountByUserInfoAndUserIdentityAndStatus(UserInfo userInfo, UserIdentity userIdentity) throws BaseException{
        long doHabitCheckCount;
        try {
            doHabitCheckCount = doHabitCheckRepository.countByUserInfoAndUserIdentityAndStatus(userInfo,userIdentity,"ACTIVE");
        }catch (Exception e){
            throw new BaseException(FAILED_TO_COUNT_BY_USERINFO_AND_USERIDENTITY_AND_STATUS);
        }
        return doHabitCheckCount;
    }

    /**
     * userIdx,userIdentityIdx,status로 doHabitCheck 가장 최신 데이터 날짜 가져오기
     * @param userInfo, userIdentity
     * @return theLatestDate
     * @throws BaseException
     */
    public Date retrieveTheLatestDate(UserInfo userInfo, UserIdentity userIdentity) throws BaseException{
        Date theLatestDate;
        try {
            theLatestDate = doHabitCheckRepository.findByTheLatestDate(userInfo,userIdentity);
        }catch (Exception e){
            throw new BaseException(FAILED_TO_FIND_BY_THE_LATEST_DATE);
        }
        return theLatestDate;
    }

}
