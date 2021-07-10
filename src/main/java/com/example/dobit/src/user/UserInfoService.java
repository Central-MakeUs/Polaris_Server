package com.example.dobit.src.user;

import com.example.dobit.src.user.models.*;
import com.example.dobit.utils.JwtService;
import com.example.dobit.config.secret.Secret;
import com.example.dobit.utils.AES128;
import com.example.dobit.config.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.dobit.config.BaseResponseStatus.*;


@Service
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;
    private final UserInfoProvider userInfoProvider;
    private final JwtService jwtService;

    @Autowired
    public UserInfoService(UserInfoRepository userInfoRepository, UserInfoProvider userInfoProvider, JwtService jwtService) {
        this.userInfoRepository = userInfoRepository;
        this.userInfoProvider = userInfoProvider;
        this.jwtService = jwtService;
    }

    /**
     * 회원가입 API
     * @param postSignUpReq
     * @return PostSignUpRes
     * @throws BaseException
     */
    public PostSignUpRes createUserInfo(PostSignUpReq postSignUpReq) throws BaseException {
        UserInfo existsUserInfo = null;
        try {
            existsUserInfo = userInfoProvider.retrieveUserInfoByEmail(postSignUpReq.getEmail());
        } catch (BaseException exception) {
            if (exception.getStatus() != NOT_FOUND_USER) {
                throw exception;
            }
        }
        if (existsUserInfo != null) {
            throw new BaseException(DUPLICATED_USER);
        }


        String email = postSignUpReq.getEmail();
        String nickname = postSignUpReq.getNickname();
        String password;

        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postSignUpReq.getPassword());
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_ENCRYPT_PASSWORD);
        }
        UserInfo userInfo = new UserInfo(email, password, nickname);
        try {
            userInfo = userInfoRepository.save(userInfo);
        } catch (Exception exception) {
            throw new BaseException(FAILED_TO_SAVE_USER_INFO);
        }

        String jwt = jwtService.createJwt(userInfo.getUserIdx());

        int id = userInfo.getUserIdx();
        return new PostSignUpRes(id, jwt);
    }




    /**
     * 유저 탈퇴 API
     * @param jwtUserIdx
     * @throws BaseException
     */
    public void updateUserStatus(Integer jwtUserIdx) throws BaseException {

        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(jwtUserIdx);

        userInfo.setStatus("INACTIVE");
        try {
            userInfo = userInfoRepository.save(userInfo);
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_SAVE_USERINFO);
        }
    }

    /**
     * 비밀번호 재설정하기 API
     * @param patchPasswordReq
     * @throws BaseException
     **/
    public void updatePassword(PatchPasswordReq patchPasswordReq) throws BaseException {
        String password;
        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(patchPasswordReq.getPassword());
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_ENCRYPT_PASSWORD);
        }
        UserInfo userInfo = userInfoProvider.retrieveUserInfoByEmail(patchPasswordReq.getEmail());
        userInfo.setPassword(password);
        try {
            userInfoRepository.save(userInfo);
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_SAVE_USERINFO);
        }
    }

}