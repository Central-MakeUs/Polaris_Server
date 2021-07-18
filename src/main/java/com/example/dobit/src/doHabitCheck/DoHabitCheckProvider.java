package com.example.dobit.src.doHabitCheck;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.doHabit.models.DoHabit;
import com.example.dobit.src.doHabitCheck.models.DoHabitCheck;
import com.example.dobit.src.user.models.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public Boolean retrieveExistingDoHabitCheck(UserInfo userInfo, DoHabit doHabit) throws BaseException {

        Boolean existDoHabitCheck;
        try {
            existDoHabitCheck = doHabitCheckRepository.existsByUserInfoAndDoHabitAndStatus(userInfo,doHabit,"ACTIVE");
        }catch (Exception e){
            throw new BaseException(FAIED_TO_EXIST_BY_USERINFO_AND_DOHABIT_AND_STATUS);
        }


        return existDoHabitCheck;
    }

    /**
     * userInfo, doHabit으로 DoHabitCheck 조회
     * @param userInfo, doHabit
     * @return DoHabitCheck
     * @throws BaseException
     */
    public DoHabitCheck retrieveDoHabitCheckByUserInfoAndDoHabit(UserInfo userInfo, DoHabit doHabit) throws BaseException{
        DoHabitCheck doHabitCheck;
        try{
            doHabitCheck =doHabitCheckRepository.findByUserInfoAndDoHabitAndStatus(userInfo,doHabit,"ACTIVE");
        }catch (Exception e){
            throw new BaseException(FAILED_TO_FIND_BY_USERINFO_AND_DOHABIT_AND_STATUS);
        }
        return doHabitCheck;
    }

}
