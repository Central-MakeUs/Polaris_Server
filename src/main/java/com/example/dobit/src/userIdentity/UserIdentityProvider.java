package com.example.dobit.src.userIdentity;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.doHabit.DoHabitRepository;
import com.example.dobit.src.doHabit.models.DoHabit;
import com.example.dobit.src.dontHabit.DontHabitRepository;
import com.example.dobit.src.dontHabit.models.DontHabit;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userIdentity.models.GetIdentityRes;
import com.example.dobit.src.userIdentity.models.UserIdentity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.dobit.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class UserIdentityProvider {
    private final UserIdentityRepository userIdentityRepository;
    private final DoHabitRepository doHabitRepository;
    private final DontHabitRepository dontHabitRepository;

    /**
     * idx로 유저정체성 조회하기
     */
    public UserIdentity retrieveUserIdentityByUserIdentityIdx(Integer userIdentityIdx)throws BaseException{
        UserIdentity userIdentity;
        try{
            userIdentity = userIdentityRepository.findByUserIdentityIdxAndStatus(userIdentityIdx,"ACTIVE");
        }catch (Exception ignored){
            throw new BaseException(FAILED_TO_FIND_BY_USERIDENTITYIDX_AND_STATUS);
        }
        return userIdentity;

    }


    /**
     * 정체성 조회하기 API
     * @param userInfo
     * @return List<GetIdentityRes>
     * @throws BaseException
     */
    public List<GetIdentityRes> retrieveIdentity(UserInfo userInfo) throws BaseException {
        List<UserIdentity> userIdentityList;
        try {
            userIdentityList = userIdentityRepository.findByUserInfoAndStatus(userInfo,"ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_FIND_BY_USERINFO_AND_STATUS);
        }

        List<GetIdentityRes> getIdentityResList = new ArrayList<>();
        for(int i=0;i<userIdentityList.size();i++){
            Integer userIdentityIdx = userIdentityList.get(i).getUserIdentityIdx();
            String userIdentityName = userIdentityList.get(i).getUserIdentityName();
            Integer userIdentityColorIdx  = userIdentityList.get(i).getUserIdentityColor().getUserIdentityColorIdx();
            String userIdentityColorName = userIdentityList.get(i).getUserIdentityColor().getUserIdentityColorName();

            UserIdentity userIdentity = retrieveUserIdentityByUserIdentityIdx(userIdentityIdx);
            DoHabit doHabit;
            try{
                doHabit = doHabitRepository.findByUserIdentityAndStatus(userIdentity,"ACTIVE");
            }catch (Exception e){
                throw new BaseException(FAILED_TO_FIND_BY_USERIDENTITY_AND_STATUS);
            }

            DontHabit dontHabit;
            try {
                dontHabit = dontHabitRepository.findByUserIdentityAndStatus(userIdentity,"ACTIVE");
            }catch (Exception e){
                throw new BaseException(FAILED_TO_FIND_BY_USERIDENTITY_AND_STATUS);
            }
            String doHabitName = null;
            if(doHabit!=null) {
                doHabitName = doHabit.getDhName();
            }
            String dontHabitName = null;
            if(dontHabit!=null){
                dontHabitName = dontHabit.getDnhName();
            }

            GetIdentityRes getIdentityRes = new GetIdentityRes(userIdentityIdx, userIdentityName, userIdentityColorIdx, userIdentityColorName,doHabitName, dontHabitName);
            getIdentityResList.add(getIdentityRes);
        }
        return getIdentityResList;

    }

    /**
     * 존재하는 유저정체성인지 확인
     * @param userInfo,userIdentityIdx
     * @return existEmail
     * @throws BaseException
     */
    public Boolean retrieveExistingUserIdentity(UserInfo userInfo,Integer userIdentityIdx) throws BaseException {
        Boolean existUserIdentity;

        try {
            existUserIdentity = userIdentityRepository.existsByUserInfoAndUserIdentityIdxAndStatus(userInfo, userIdentityIdx,"ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_EXIST_BY_USERINFO_AND_USERIDENTITYIDX_AND_STATUS);
        }


        return existUserIdentity;
    }
//
//    /**
//     * 유저로 유저정체성 조회
//     * @param userInfo
//     * @return UserIdentity
//     * @throws BaseException
//     */
//    public UserIdentity retrieveUserIdentityByUserInfo(UserInfo userInfo) throws BaseException{
//        UserIdentity userIdentity;
//        try{
//            userIdentity = userIdentityRepository.findByUserInfoAndStatus()
//        }
//    }

}
