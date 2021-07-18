package com.example.dobit.src.dontHabitCheck;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.dontHabit.models.DontHabit;
import com.example.dobit.src.dontHabitCheck.models.DontHabitCheck;
import com.example.dobit.src.user.models.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;

import static com.example.dobit.config.BaseResponseStatus.FAILED_TO_SAVE_DONT_HABIT_CHECK;

@Service
@RequiredArgsConstructor
public class DontHabitCheckService {
    private final DontHabitCheckProvider dontHabitCheckProvider;
    private final DontHabitCheckRepository dontHabitCheckRepository;

    /**
     * dontHabit 체크하기 생성
     * @param userInfo,dontHabit
     * @return void
     * @throws BaseException
     */
    public void createDontHabitCheck(UserInfo userInfo, DontHabit dontHabit) throws BaseException {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        DontHabitCheck dontHabitCheck = new DontHabitCheck(dontHabit,userInfo,dontHabit.getUserIdentity(),year,month,day);
        try {
            dontHabitCheckRepository.save(dontHabitCheck);
        }catch (Exception e){
            throw new BaseException(FAILED_TO_SAVE_DONT_HABIT_CHECK);
        }

    }

    /**
     * dontHabit 체크하기 취소
     * @param userInfo,dontHabit
     * @return void
     * @throws BaseException
     */
    public void deleteDontHabitCheck(UserInfo userInfo, DontHabit dontHabit) throws BaseException {
        DontHabitCheck dontHabitCheck =dontHabitCheckProvider.retrieveDontHabitCheckByUserInfoAndDontHabitToCurrentDate(userInfo,dontHabit);
        dontHabitCheck.setStatus("INACTIVE");
        try {
            dontHabitCheckRepository.save(dontHabitCheck);
        }catch (Exception e){
            throw new BaseException(FAILED_TO_SAVE_DONT_HABIT_CHECK);
        }

    }
}
