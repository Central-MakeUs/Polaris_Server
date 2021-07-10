package com.example.dobit.src.userIdentity;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userIdentity.models.GetIdentityRes;
import com.example.dobit.src.userIdentity.models.UserIdentity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.dobit.config.BaseResponseStatus.FAILED_TO_FIND_BY_USERINFO_AND_STATUS;

@Service
@RequiredArgsConstructor
public class UserIdentityProvider {
    private final UserIdentityRepository userIdentityRepository;

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
            String doHabit = null;
            String doNotHabit = null;

            GetIdentityRes getIdentityRes = new GetIdentityRes(userIdentityIdx, userIdentityName, doHabit, doNotHabit);
            getIdentityResList.add(getIdentityRes);
        }
        return getIdentityResList;

    }

}
