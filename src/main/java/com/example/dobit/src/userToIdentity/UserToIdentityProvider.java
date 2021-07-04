package com.example.dobit.src.userToIdentity;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userToIdentity.models.GetIdentityRes;
import com.example.dobit.src.userToIdentity.models.UserToIdentity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.dobit.config.BaseResponseStatus.FAILED_TO_FIND_BY_USERINFO_AND_STATUS;

@Service
@RequiredArgsConstructor
public class UserToIdentityProvider {
    private final UserToIdentityRepository userToIdentityRepository;


    /**
     * 목표 조회하기 API
     * @param userInfo
     * @return List<GetIdentityRes>
     * @throws BaseException
     */
    public List<GetIdentityRes> retrieveIdentity(UserInfo userInfo) throws BaseException {
        List<UserToIdentity> userToIdentityList ;
        try {
            userToIdentityList = userToIdentityRepository.findByUserInfoAndStatus(userInfo,"ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_FIND_BY_USERINFO_AND_STATUS);
        }

        List<GetIdentityRes> getIdentityResList = new ArrayList<>();
        for(int i=0;i<userToIdentityList.size();i++){
            Integer userToIdentityIdx = userToIdentityList.get(i).getUtiIdx();
            String identityName = userToIdentityList.get(i).getIdentityName();
            String doHabit = null;
            String doNotHabit = null;

            GetIdentityRes getIdentityRes = new GetIdentityRes(userToIdentityIdx, identityName, doHabit, doNotHabit);
            getIdentityResList.add(getIdentityRes);
        }
        return getIdentityResList;

    }



}
