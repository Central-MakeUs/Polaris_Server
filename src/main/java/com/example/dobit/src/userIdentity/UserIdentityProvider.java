package com.example.dobit.src.userIdentity;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.doHabit.DoHabitProvider;
import com.example.dobit.src.doHabit.DoHabitRepository;
import com.example.dobit.src.doHabit.models.DoHabit;
import com.example.dobit.src.dontHabit.DontHabitProvider;
import com.example.dobit.src.dontHabit.DontHabitRepository;
import com.example.dobit.src.dontHabit.models.DontHabit;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userIdentity.models.GetIdentitiesRes;
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
    private final DoHabitProvider doHabitProvider;
    private final DontHabitProvider dontHabitProvider;

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
     * @return List<GetIdentitiesRes>
     * @throws BaseException
     */
    public List<GetIdentitiesRes> retrieveIdentities(UserInfo userInfo) throws BaseException {
        List<UserIdentity> userIdentityList;
        try {
            userIdentityList = userIdentityRepository.findByUserInfoAndStatus(userInfo,"ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_FIND_BY_USERINFO_AND_STATUS);
        }

        List<GetIdentitiesRes> getIdentitiesResList = new ArrayList<>();
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

            GetIdentitiesRes getIdentitiesRes = new GetIdentitiesRes(userIdentityIdx, userIdentityName, userIdentityColorIdx, userIdentityColorName,doHabitName, dontHabitName);
            getIdentitiesResList.add(getIdentitiesRes);
        }
        return getIdentitiesResList;

    }

    /**
     * 정체성 상세 조회하기 API
     * @param userIdentity
     * @return List<GetIdentitiesRes>
     * @throws BaseException
     */
    public GetIdentityRes retrieveIdentity(UserIdentity userIdentity) throws BaseException {
        int userIdentityIdx = userIdentity.getUserIdentityIdx();
        DoHabit doHabit = doHabitProvider.retrieveDoHabitByUserIdentity(userIdentity);
        int doHabitIdx = doHabit.getDhIdx();
        String doHabitName = doHabit.getDhName();
        DontHabit dontHabit = dontHabitProvider.retrieveDontHabitByUserIdentity(userIdentity);
        int dontHabitIdx = dontHabit.getDnhIdx();
        String dontHabitName = dontHabit.getDnhName();
        int userIdentityColorIdx = userIdentity.getUserIdentityColor().getUserIdentityColorIdx();
        String userIdentityColorName = userIdentity.getUserIdentityColor().getUserIdentityColorName();

        return new GetIdentityRes(userIdentityIdx,doHabitIdx,doHabitName,dontHabitIdx,dontHabitName,userIdentityColorIdx,userIdentityColorName);

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
