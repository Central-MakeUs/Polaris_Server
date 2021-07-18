package com.example.dobit.src.doHabitCheck;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.doHabit.models.DoHabit;
import com.example.dobit.src.doHabitCheck.models.DoHabitCheck;
import com.example.dobit.src.user.models.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;

import static com.example.dobit.config.BaseResponseStatus.FAILED_TO_SAVE_DO_HABIT_CHECK;

@Service
@RequiredArgsConstructor
public class DoHabitCheckService {
    private final DoHabitCheckRepository doHabitCheckRepository;
    private final DoHabitCheckProvider doHabitCheckProvider;

    /**
     * doHabit 체크하기 생성
     * @param userInfo,doHabit
     * @return void
     * @throws BaseException
     */
    public void createDoHabitCheck(UserInfo userInfo, DoHabit doHabit) throws BaseException {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DoHabitCheck doHabitCheck = new DoHabitCheck(doHabit,userInfo,doHabit.getUserIdentity(),year,month,day);
        try {
            doHabitCheckRepository.save(doHabitCheck);
        }catch (Exception e){
            throw new BaseException(FAILED_TO_SAVE_DO_HABIT_CHECK);
        }

    }

    /**
     * doHabit 체크하기 취소
     * @param userInfo,doHabit
     * @return void
     * @throws BaseException
     */
    public void deleteDoHabitCheck(UserInfo userInfo, DoHabit doHabit) throws BaseException {
        DoHabitCheck doHabitCheck =doHabitCheckProvider.retrieveDoHabitCheckByUserInfoAndDoHabitToCurrentDate(userInfo,doHabit);
        doHabitCheck.setStatus("INACTIVE");
        try {
            doHabitCheckRepository.save(doHabitCheck);
        }catch (Exception e){
            throw new BaseException(FAILED_TO_SAVE_DO_HABIT_CHECK);
        }

    }

}
