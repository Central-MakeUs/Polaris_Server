package com.example.dobit.src.habit;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.doHabit.DoHabitProvider;
import com.example.dobit.src.doHabit.models.DoHabit;
import com.example.dobit.src.doHabitCheck.DoHabitCheckProvider;
import com.example.dobit.src.dontHabit.DontHabitProvider;
import com.example.dobit.src.dontHabit.models.DontHabit;
import com.example.dobit.src.dontHabitCheck.DontHabitCheckProvider;
import com.example.dobit.src.habit.models.GetHabitRes;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userIdentity.UserIdentityProvider;
import com.example.dobit.src.userIdentity.models.GetIdentitiesRes;
import com.example.dobit.src.userIdentity.models.UserIdentity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class HabitProvider {
    private final DontHabitProvider dontHabitProvider;
    private final DoHabitProvider doHabitProvider;
    private final UserIdentityProvider userIdentityProvider;
    private final DoHabitCheckProvider doHabitCheckProvider;
    private final DontHabitCheckProvider dontHabitCheckProvider;
    /**
     * 습관 조회하기 API
     * @param userInfo
     * @return List<GetHabitRes>
     * @throws BaseException
     */
    public List<GetHabitRes> retrieveHabit(UserInfo userInfo) throws BaseException {

        List<GetIdentitiesRes> getIdentitiesResList = userIdentityProvider.retrieveIdentities(userInfo);

        List<GetHabitRes> getHabitResList = new ArrayList<>();
        for (int i = 0; i< getIdentitiesResList.size(); i++){
            Integer userIdentityIdx = getIdentitiesResList.get(i).getUserIdentityIdx();
            UserIdentity userIdentity = userIdentityProvider.retrieveUserIdentityByUserIdentityIdx(userIdentityIdx);
            String userIdentityColorName = userIdentity.getUserIdentityColor().getUserIdentityColorName();
            DoHabit doHabit = doHabitProvider.retrieveDoHabitByUserIdentity(userIdentity);
            DontHabit dontHabit = dontHabitProvider.retrieveDontHabitByUserIdentity(userIdentity);
            Boolean existsDoHabitCheck = doHabitCheckProvider.retrieveExistingDoHabitCheckToCurrentDate(userInfo,doHabit);
            Boolean existsDontHabitCheck = dontHabitCheckProvider.retrieveExistingDontHabitCheckToCurrentDate(userInfo,dontHabit);

            Integer doHabitIdx = null;
            String doHabitName = null;
            String doHabitSummary = null;
            String isDoHabitCheck = null;
            if(doHabit!=null){
                doHabitIdx = doHabit.getDhIdx();
                doHabitName = doHabit.getDhName();
                doHabitSummary = doHabit.getDhWhen() + ", " + doHabit.getDhWhere() +", " + doHabit.getDhStart();
                if(existsDoHabitCheck){
                    isDoHabitCheck = "Y";
                }
                else {
                    isDoHabitCheck = "N";
                }
            }

            Integer dontHabitIdx = null;
            String dontHabitName = null;
            String dontHabitSummary = null;
            String isDontHabitCheck = null;
            if(dontHabit!=null){
                dontHabitIdx = dontHabit.getDnhIdx();
                dontHabitName = dontHabit.getDnhName();
                dontHabitSummary = dontHabit.getDnhAdvantage() + ", " + dontHabit.getDnhEnv();
                if(existsDontHabitCheck){
                    isDontHabitCheck = "Y";
                }
                else{
                    isDontHabitCheck = "N";
                }
            }
            GetHabitRes getHabitRes = new GetHabitRes(userIdentityIdx,doHabitIdx,doHabitName,doHabitSummary,isDoHabitCheck,dontHabitIdx,dontHabitName,dontHabitSummary,isDontHabitCheck,userIdentityColorName);
            getHabitResList.add(getHabitRes);
        }


        return getHabitResList;
    }
}
