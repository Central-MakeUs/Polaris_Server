package com.example.dobit.src.tracker;


import com.example.dobit.config.BaseException;
import com.example.dobit.src.doHabitCheck.DoHabitCheckProvider;
import com.example.dobit.src.doHabitCheck.models.DoHabitCheck;
import com.example.dobit.src.dontHabitCheck.DontHabitCheckProvider;
import com.example.dobit.src.dontHabitCheck.models.DontHabitCheck;
import com.example.dobit.src.tracker.models.GetTrackerRes;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userIdentity.UserIdentityProvider;
import com.example.dobit.src.userIdentity.models.UserIdentity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TrackerProvider {
    private final DoHabitCheckProvider doHabitCheckProvider;
    private final DontHabitCheckProvider dontHabitCheckProvider;



    /**
     * 트래커 조회하기 API
     * @param userIdentity,year, month, userInfo
     * return getTrackerRes
     */
    public GetTrackerRes retreiveTracker(UserIdentity userIdentity, int year, int month, UserInfo userInfo) throws BaseException{
        List<DoHabitCheck> doHabitChecks = doHabitCheckProvider.retrieveDoHabitCheckByUserInfoAndUserIdentityAndYearAndMonth(userInfo,userIdentity,year,month);
        List<DontHabitCheck> dontHabitChecks = dontHabitCheckProvider.retrieveDontHabitCheckByUserInfoAndUserIdentityAndYearAndMonth(userInfo,userIdentity,year,month);

        List<Integer> doHabitCheckDateList = new ArrayList<>();
        for(int i=0;i<doHabitChecks.size();i++){
            doHabitCheckDateList.add(doHabitChecks.get(i).getDay());
        }
        List<Integer> dontHabitCheckDateList = new ArrayList<>();
        for(int i=0;i<dontHabitChecks.size();i++){
            dontHabitCheckDateList.add(dontHabitChecks.get(i).getDay());
        }

        Set<Integer> set = new LinkedHashSet<>(doHabitCheckDateList);
        set.addAll(dontHabitCheckDateList);
        List<Integer> checkDateList = new ArrayList<>(set);


        return new GetTrackerRes(userIdentity.getUserIdentityIdx(),checkDateList,checkDateList.size());
    }
}
