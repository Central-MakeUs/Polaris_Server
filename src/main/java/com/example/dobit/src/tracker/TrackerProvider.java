package com.example.dobit.src.tracker;


import com.example.dobit.config.BaseException;
import com.example.dobit.src.doHabit.DoHabitProvider;
import com.example.dobit.src.doHabit.models.DoHabit;
import com.example.dobit.src.doHabitCheck.DoHabitCheckProvider;
import com.example.dobit.src.doHabitCheck.models.DoHabitCheck;
import com.example.dobit.src.dontHabit.DontHabitProvider;
import com.example.dobit.src.tracker.models.GetTrackerRes;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userIdentity.UserIdentityProvider;
import com.example.dobit.src.userIdentity.models.UserIdentity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackerProvider {
    private final DoHabitProvider doHabitProvider;
    private final DontHabitProvider dontHabitProvider;
    private final UserIdentityProvider userIdentityProvider;
    private final DoHabitCheckProvider doHabitCheckProvider;



//    /**
//     * 트래커 조회하기 API
//     * @param userIdentityIdx, month, userInfo
//     * return getTrackerRes
//     */
//    public GetTrackerRes retreiveTracker(int userIdentityIdx, int month, UserInfo userInfo) throws BaseException{
//        UserIdentity userIdentity = userIdentityProvider.retrieveUserIdentityByUserIdentityIdx(userIdentityIdx);
//        DoHabit doHabit = doHabitProvider.retrieveDoHabitByUserIdentity(userIdentity);
//        DoHabitCheck doHabitCheck = doHabitCheckProvider.retrieveDoHabitCheckByUserInfoAndDoHabit(userInfo,doHabit);
//
////        private final int userIdentityIdx;
////        private final List checkDateList;
////        private final Integer checkDateCount;
//
//
//        return new GetTrackerRes();
//    }
}
