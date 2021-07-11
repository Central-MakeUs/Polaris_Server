package com.example.dobit.src.doHabit;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.doHabit.models.DoHabit;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userIdentity.models.UserIdentity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.dobit.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class DoHabitProvider {
    private final DoHabitRepository doHabitRepository;



    /**
     * Idx로 DoHabit 조회
     * @param dhIdx
     * @return DoHabit
     * @throws BaseException
     */
    public DoHabit retrieveDoHabitByDhIdx(Integer dhIdx) throws BaseException {
        DoHabit doHabit;
        try {
            doHabit = doHabitRepository.findByDhIdxAndStatus(dhIdx,"ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_FIND_BY_DHIDX_AND_STATUS);
        }


        return doHabit;
    }

    /**
     * userInfo로 DoHabit 조회
     * @param userIdentity
     * @return existDoHabit
     * @throws BaseException
     */
    public Boolean retrieveExistingDoHabitByUserIdentity(UserIdentity userIdentity) throws BaseException {
        Boolean existDoHabit;
        try {
            existDoHabit = doHabitRepository.existsByUserIdentityAndStatus(userIdentity,"ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_EXIST_BY_USERINFO_AND_STATUS);
        }

        return existDoHabit;
    }
}
