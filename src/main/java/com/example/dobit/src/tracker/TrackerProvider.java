package com.example.dobit.src.tracker;


import com.example.dobit.config.BaseException;
import com.example.dobit.src.doHabitCheck.DoHabitCheckProvider;
import com.example.dobit.src.doHabitCheck.models.DoHabitCheck;
import com.example.dobit.src.dontHabitCheck.DontHabitCheckProvider;
import com.example.dobit.src.dontHabitCheck.models.DontHabitCheck;
import com.example.dobit.src.tracker.models.GetTrackerRes;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userIdentity.UserIdentityProvider;
import com.example.dobit.src.userIdentity.models.GetIdentitiesRes;
import com.example.dobit.src.userIdentity.models.UserIdentity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.thymeleaf.util.ListUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class TrackerProvider {
    private final DoHabitCheckProvider doHabitCheckProvider;
    private final DontHabitCheckProvider dontHabitCheckProvider;
    private final UserIdentityProvider userIdentityProvider;
//
//    /**
//     * 트래커 조회하기 API - 하나만 조회
//     * @param userIdentity,year, month, userInfo
//     * return getTrackerRes
//     */
//    public GetTrackerRes retreiveTracker(UserIdentity userIdentity, int year, int month, UserInfo userInfo) throws BaseException{
//        List<DoHabitCheck> doHabitChecks = doHabitCheckProvider.retrieveDoHabitCheckByUserInfoAndUserIdentityAndYearAndMonth(userInfo,userIdentity,year,month);
//        List<DontHabitCheck> dontHabitChecks = dontHabitCheckProvider.retrieveDontHabitCheckByUserInfoAndUserIdentityAndYearAndMonth(userInfo,userIdentity,year,month);
//
//        List<Integer> doHabitCheckDateList = new ArrayList<>();
//        for(int i=0;i<doHabitChecks.size();i++){
//            doHabitCheckDateList.add(doHabitChecks.get(i).getDay());
//        }
//        List<Integer> dontHabitCheckDateList = new ArrayList<>();
//        for(int i=0;i<dontHabitChecks.size();i++){
//            dontHabitCheckDateList.add(dontHabitChecks.get(i).getDay());
//        }
//
//        Set<Integer> set = new LinkedHashSet<>(doHabitCheckDateList);
//        set.addAll(dontHabitCheckDateList);
//        List<Integer> checkDateList = new ArrayList<>(set);
//
//
//        return new GetTrackerRes(userIdentity.getUserIdentityIdx(),checkDateList,checkDateList.size());
//    }

    /**
     * 트래커 조회하기 API
     * @param year, month, userInfo
     * return List<GetTrackerRes>
     */
    public List<GetTrackerRes> retreiveTracker(int year, int month, UserInfo userInfo) throws BaseException{
        List<GetIdentitiesRes> getIdentitiesResList = userIdentityProvider.retrieveIdentities(userInfo);

        List<GetTrackerRes> getTrackerResList = new ArrayList<>();
        for (int i=0;i<getIdentitiesResList.size();i++){
            UserIdentity userIdentity = userIdentityProvider.retrieveUserIdentityByUserIdentityIdx(getIdentitiesResList.get(i).getUserIdentityIdx());
            List<DoHabitCheck> doHabitChecks = doHabitCheckProvider.retrieveDoHabitCheckByUserInfoAndUserIdentityAndYearAndMonth(userInfo,userIdentity,year,month);
            List<DontHabitCheck> dontHabitChecks = dontHabitCheckProvider.retrieveDontHabitCheckByUserInfoAndUserIdentityAndYearAndMonth(userInfo,userIdentity,year,month);

            List<Integer> doHabitCheckDateList = new ArrayList<>();
            if(!isEmpty(doHabitChecks)){
                for(int j=0;j<doHabitChecks.size();j++){
                    doHabitCheckDateList.add(doHabitChecks.get(j).getDay());
                }
            }

            List<Integer> dontHabitCheckDateList = new ArrayList<>();
            if(!isEmpty(dontHabitChecks)){
                for(int j=0;j<dontHabitChecks.size();j++){
                    dontHabitCheckDateList.add(dontHabitChecks.get(j).getDay());
                }
            }

            // doHabitCheckDateList,dontHabitCheckDateList 두 리스트 합치되 중복 제거
            Set<Integer> set = new LinkedHashSet<>(doHabitCheckDateList);
            set.addAll(dontHabitCheckDateList);
            List<Integer> checkDateList = new ArrayList<>(set);

            List graphDataList = new ArrayList<>();

            Integer checkDateCount = checkDateList.size();
            for(int j=0;j<6;j++ ){
//                Math.round((result*100)/100.0) // 소수점 둘째자리까지
                float result = (1.01f)*checkDateCount;
                graphDataList.add(result);
                checkDateCount+=30;

            }

            GetTrackerRes getTrackerRes = new GetTrackerRes(userIdentity.getUserIdentityIdx(),checkDateList,graphDataList);
            getTrackerResList.add(getTrackerRes);
        }


        return  getTrackerResList;

    }


}
