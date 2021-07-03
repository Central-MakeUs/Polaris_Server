package com.example.dobit.src.userToIdentity;

import com.example.dobit.config.BaseException;
import com.example.dobit.config.secret.Secret;
import com.example.dobit.src.identity.IdentityProvider;
import com.example.dobit.src.identity.IdentityRepository;
import com.example.dobit.src.identity.models.GetOriginIdentityRes;
import com.example.dobit.src.identity.models.Identity;
import com.example.dobit.src.user.UserInfoProvider;
import com.example.dobit.src.user.models.PostSignUpRes;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userToIdentity.models.PostIdentityReq;
import com.example.dobit.src.userToIdentity.models.UserToIdentity;
import com.example.dobit.utils.AES128;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.dobit.config.BaseResponseStatus.*;


@Service
@RequiredArgsConstructor
public class UserToIdentityService {
    private final UserToIdentityRepository userToIdentityRepository;
    private final IdentityProvider identityProvider;

    /**
     * 목표 추가하기 API
     *
     * @return List<PostIdentityReq>
     * @throws BaseException
     */
    public void  createIdentity(UserInfo userInfo ,PostIdentityReq postIdentityReq) throws BaseException {
        List<Integer> identityList = postIdentityReq.getIdentityList();

        for (int i = 0; i < identityList.size(); i++) {
            Integer identityIdx = identityList.get(i);
            Identity identity = identityProvider.retrieveIdentityByIdentityIdx(identityIdx);
            String identityName = identity.getIdentityName();

            UserToIdentity userToIdentity = new UserToIdentity(userInfo, identity, identityName);
            try {
                userToIdentityRepository.save(userToIdentity);
            } catch (Exception exception) {
                throw new BaseException(FAILED_TO_SAVE_USER_TO_IDENTITY);
            }


        }
    }

}