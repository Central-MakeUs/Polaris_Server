package com.example.dobit.src.habit;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.doHabit.DoHabitProvider;
import com.example.dobit.src.doHabit.models.DoHabit;
import com.example.dobit.src.dontHabit.DontHabitProvider;
import com.example.dobit.src.dontHabit.models.DontHabit;
import com.example.dobit.src.habit.models.GetHabitRes;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userIdentity.UserIdentityProvider;
import com.example.dobit.src.userIdentity.models.GetIdentityRes;
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

    /**
     * 습관 조회하기 API
     * @param userInfo
     * @return List<GetHabitRes>
     * @throws BaseException
     */
    public List<GetHabitRes> retrieveHabit(UserInfo userInfo) throws BaseException {

        List<GetIdentityRes> getIdentityResList = userIdentityProvider.retrieveIdentity(userInfo);

        List<GetHabitRes> getHabitResList = new ArrayList<>();
        for (int i=0;i<getIdentityResList.size();i++){
            Integer userIdentityIdx = getIdentityResList.get(i).getUserIdentityIdx();
            UserIdentity userIdentity = userIdentityProvider.retrieveUserIdentityByUserIdentityIdx(userIdentityIdx);
            DoHabit doHabit = doHabitProvider.retrieveDoHabitByUserIdentity(userIdentity);
            DontHabit dontHabit = dontHabitProvider.retrieveDontHabitByUserIdentity(userIdentity);

            Integer doHabitIdx = null;
            String doHabitName = null;
            String doHabitSummary = null;
            if(doHabit!=null){
                doHabitIdx = doHabit.getDhIdx();
                doHabitName = doHabit.getDhName();
                doHabitSummary = doHabit.getDhWhen() + "에" + doHabit.getDhWhere() +"에서" + doHabit.getDhStart();
            }

            Integer dontHabitIdx = null;
            String dontHabitName = null;
            String dontHabitSummary = null;
            if(dontHabit!=null){
                dontHabitIdx = dontHabit.getDnhIdx();
                dontHabitName = dontHabit.getDnhName();
                dontHabitSummary = dontHabit.getDnhAdvantage() + " " + dontHabit.getDnhEnv();
            }
            GetHabitRes getHabitRes = new GetHabitRes(userIdentityIdx,doHabitIdx,doHabitName,doHabitSummary,dontHabitIdx,dontHabitName,dontHabitSummary);
            getHabitResList.add(getHabitRes);
        }


        return getHabitResList;
    }
}
