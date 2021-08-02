package com.example.dobit.src.userIdentity;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.identity.IdentityProvider;
import com.example.dobit.src.identity.models.Identity;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userIdentity.models.*;
import com.example.dobit.src.userIdentityColor.UserIdentityColorProvider;
import com.example.dobit.src.userIdentityColor.models.UserIdentityColor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.dobit.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class UserIdentityService {
    private final IdentityProvider identityProvider;
    private final UserIdentityProvider userIdentityProvider;
    private final UserIdentityRepository userIdentityRepository;
    private final UserIdentityColorProvider userIdentityColorProvider;

    /**
     * 정체성 생성하기 API
     * @param userInfo, postIdentityReq
     * @return void
     * @throws BaseException
     */
    @Transactional
    public void  createIdentity(UserInfo userInfo , PostIdentityReq postIdentityReq) throws BaseException {
        List<Integer> identityList = postIdentityReq.getIdentityList();

        for (int i = 0; i < identityList.size(); i++) {
            Integer identityIdx = identityList.get(i);
            Identity identity = identityProvider.retrieveIdentityByIdentityIdx(identityIdx);
            String identityName = identity.getIdentityName();
            UserIdentityColor userIdentityColor = userIdentityColorProvider.retrieveUserIdentityColorByUserIdentityColorIdx(9); //black

            UserIdentity userIdentity = new UserIdentity(userInfo, identityName,userIdentityColor);
            try {
                userIdentityRepository.save(userIdentity);
            } catch (Exception exception) {
                throw new BaseException(FAILED_TO_SAVE_USER_IDENTITY);
            }
        }
    }

    /**
     * 정체성 직접 생성하기 API
     * @param userInfo, postDirectIdentityReq
     * @return PostDirectIdentityRes
     * @throws BaseException
     */
    @Transactional
    public PostIdentityRes createDirectIdentity(UserInfo userInfo , PostDirectIdentityReq postDirectIdentityReq) throws BaseException {
        String identityName = postDirectIdentityReq.getIdentityName();
        UserIdentityColor userIdentityColor = userIdentityColorProvider.retrieveUserIdentityColorByUserIdentityColorIdx(9); //black
        UserIdentity userIdentity = new UserIdentity(userInfo, identityName,userIdentityColor);
        try {
            userIdentityRepository.save(userIdentity);
        } catch (Exception exception) {
            throw new BaseException(FAILED_TO_SAVE_USER_IDENTITY);

        }
        int userIdentityIdx = userIdentity.getUserIdentityIdx();
        String userIdentityName = userIdentity.getUserIdentityName();
        return new PostIdentityRes(userIdentityIdx,userIdentityName);
    }

    /**
     * 정체성 추가하기 API
     * @param userInfo, postIdentityPlusReq
     * @return PostIdentityRes
     * @throws BaseException
     */
    @Transactional
    public PostIdentityRes createIdentityPlus(UserInfo userInfo , PostIdentityPlusReq postIdentityPlusReq) throws BaseException {
        String identityName = postIdentityPlusReq.getIdentityName();
        Integer colorIdx = postIdentityPlusReq.getUserIdentityColorIdx();
        UserIdentityColor userIdentityColor;
        if(colorIdx==null){
            userIdentityColor = userIdentityColorProvider.retrieveUserIdentityColorByUserIdentityColorIdx(9); //black
        }
        else{
            userIdentityColor = userIdentityColorProvider.retrieveUserIdentityColorByUserIdentityColorIdx(colorIdx);
        }
        UserIdentity userIdentity = new UserIdentity(userInfo, identityName,userIdentityColor);
        try {
            userIdentityRepository.save(userIdentity);
        } catch (Exception exception) {
            throw new BaseException(FAILED_TO_SAVE_USER_IDENTITY);

        }
        int userIdentityIdx = userIdentity.getUserIdentityIdx();
        String userIdentityName = userIdentity.getUserIdentityName();
        return new PostIdentityRes(userIdentityIdx,userIdentityName);
    }

    /**
     * 정체성 삭제하기 API
     * @param userIdentityIdx
     * @return void
     * @throws BaseException
     */
    public void updateIdentityStatus(Integer userIdentityIdx) throws BaseException{
        UserIdentity userIdentity = userIdentityProvider.retrieveUserIdentityByUserIdentityIdx(userIdentityIdx);

        userIdentity.setStatus("INACTIVE");

        try{
            userIdentityRepository.save(userIdentity);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_SAVE_USER_IDENTITY);
        }
    }


    /**
     * 정체성 수정하기 API
     * @param userIdentity,patchIdentityReq
     * @return void
     * @throws BaseException
     */
    public void updateIdentityColor(UserIdentity userIdentity,UserIdentityColor userIdentityColor,String userIdentityName) throws BaseException{
        userIdentity.setUserIdentityName(userIdentityName);
        userIdentity.setUserIdentityColor(userIdentityColor);

        try{
            userIdentityRepository.save(userIdentity);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_SAVE_USER_IDENTITY);
        }
    }
}
