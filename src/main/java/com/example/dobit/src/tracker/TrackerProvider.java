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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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

            // 두 리스트 합치되 중복 제거
            Set<Integer> set = new LinkedHashSet<>(doHabitCheckDateList);
            set.addAll(dontHabitCheckDateList);
            List<Integer> checkDateList = new ArrayList<>(set);

            List graphDataList = new ArrayList<>();



            long doHabitCheckCount = doHabitCheckProvider.retrieveCountByUserInfoAndUserIdentityAndStatus(userInfo,userIdentity);
            long dontHabitCheckCount = dontHabitCheckProvider.retrieveCountByUserInfoAndUserIdentityAndStatus(userInfo,userIdentity);

            long habitCheckCount = doHabitCheckCount+dontHabitCheckCount;
            System.out.println("habitCheckCount:"+habitCheckCount);

            Date theLatestDHCheckDate = doHabitCheckProvider.retrieveTheLatestDate(userInfo,userIdentity);
            Date theLatestDHabitCheckDate = dontHabitCheckProvider.retrieveTheLatestDate(userInfo,userIdentity);

            long diffDay=0;
            if(theLatestDHCheckDate!=null){
                try{
                    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                    Date date = new Date();
                    String theLatestDoHabitCheckDate = dateFormat.format(theLatestDHCheckDate);
                    String theLatestDontHabitCheckDate = dateFormat.format(theLatestDHabitCheckDate);
                    String theCurrentDate = dateFormat.format(date);
                    String strFormat = "yyyyMMdd";
                    SimpleDateFormat sdf = new SimpleDateFormat(strFormat);

                    Date startDate = sdf.parse(theCurrentDate);
                    Date endDohabitDate = sdf.parse(theLatestDoHabitCheckDate);
                    Date endDonthabitDate = sdf.parse(theLatestDontHabitCheckDate);

                    //두날짜 사이의 시간 차이(ms)를 하루 동안의 ms(24시*60분*60초*1000밀리초) 로 나눈다.
                    long doHabitDiffDay = (startDate.getTime() - endDohabitDate.getTime()) / (24*60*60*1000);
                    long dontHabitDiffDay = (startDate.getTime() - endDonthabitDate.getTime()) / (24*60*60*1000);
                    diffDay = Math.max(doHabitDiffDay,dontHabitDiffDay);
                    System.out.println(diffDay+"일");
                }catch(java.text.ParseException e){
                    e.printStackTrace();
                }
            }


            if(diffDay>=2){
                habitCheckCount-=diffDay;
                for(int j=0;j<6;j++ ){
                    habitCheckCount+=30;
                    System.out.println("diffDay>=2");
                    System.out.println(habitCheckCount+"habitCheckCount");
                    Double result = Math.pow(1.01f,habitCheckCount);
                    graphDataList.add(Math.round(result*100)/100.0);

                }
            }
            else{
                for(int j=0;j<6;j++ ){
                    habitCheckCount+=30;
                    System.out.println("diffDay<2");
                    System.out.println(habitCheckCount+"habitCheckCount");
                    Double result = Math.pow(1.01f,habitCheckCount);
                    graphDataList.add(Math.round(result*100)/100.0);
                }
            }




            GetTrackerRes getTrackerRes = new GetTrackerRes(userIdentity.getUserIdentityIdx(),checkDateList,graphDataList);
            getTrackerResList.add(getTrackerRes);
        }


        return  getTrackerResList;

    }


}
