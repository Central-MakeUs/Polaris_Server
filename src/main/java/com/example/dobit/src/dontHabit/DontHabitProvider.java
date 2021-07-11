package com.example.dobit.src.dontHabit;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.doHabit.models.DoHabit;
import com.example.dobit.src.dontHabit.models.DontHabit;
import com.example.dobit.src.userIdentity.models.UserIdentity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.dobit.config.BaseResponseStatus.FAILED_TO_EXIST_BY_USERIDENTITY_AND_STATUS;
import static com.example.dobit.config.BaseResponseStatus.FAILED_TO_FIND_BY_DNHIDX_AND_STATUS;

@Service
@RequiredArgsConstructor
public class DontHabitProvider {
    private final DontHabitRepository dontHabitRepository;

    /**
     * userIdentity로 DontHabit 조회
     * @param userIdentity
     * @return existDontHabit
     * @throws BaseException
     */
    public Boolean retrieveExistingDontHabitByUserIdentity(UserIdentity userIdentity) throws BaseException {
        Boolean existDontHabit;
        try {
            existDontHabit = dontHabitRepository.existsByUserIdentityAndStatus(userIdentity,"ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_EXIST_BY_USERIDENTITY_AND_STATUS);
        }

        return existDontHabit;
    }

    /**
     * Idx로 DontHabit 조회
     * @param dnhIdx
     * @return DontHabit
     * @throws BaseException
     */
    public DontHabit retrieveDontHabitByDnhIdx(Integer dnhIdx) throws BaseException {
        DontHabit dontHabit;
        try {
            dontHabit = dontHabitRepository.findByDnhIdxAndStatus(dnhIdx,"ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_FIND_BY_DNHIDX_AND_STATUS);
        }


        return dontHabit;
    }


}
