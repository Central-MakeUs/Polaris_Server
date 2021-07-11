package com.example.dobit.src.userIdentity;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.user.UserInfoProvider;
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
    private final UserInfoProvider userInfoProvider;

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
        List<UserIdentity> userIdentityList ;
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
            String doHabit = null;
            String doNotHabit = null;

            GetIdentityRes getIdentityRes = new GetIdentityRes(userIdentityIdx, userIdentityName, userIdentityColorIdx, userIdentityColorName,doHabit, doNotHabit);
            getIdentityResList.add(getIdentityRes);
        }
        return getIdentityResList;

    }

    /**
     * 존재하는 유저정체성인지 확인
     * @param jwtUserIdx,userIdentityIdx
     * @return existEmail
     * @throws BaseException
     */
    public Boolean retrieveExistingUserIdentity(Integer jwtUserIdx,Integer userIdentityIdx) throws BaseException {
        Boolean existUserIdentity;

        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(jwtUserIdx);

        try {
            existUserIdentity = userIdentityRepository.existsByUserInfoAndUserIdentityIdxAndStatus(userInfo, userIdentityIdx,"ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_EXIST_BY_USERINFO_AND_USERIDENTITYIDX_AND_STATUS);
        }


        return existUserIdentity;
    }

}
