package com.example.dobit.src.user;

import com.example.dobit.src.user.models.*;
import com.example.dobit.utils.JwtService;
import com.example.dobit.config.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.dobit.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class UserInfoProvider {
    private final UserInfoRepository userInfoRepository;


    /**
     * 이메일로 회원 조회
     * @param email
     * @return UserInfo
     * @throws BaseException
     */
    public UserInfo retrieveUserInfoByEmail(String email) throws BaseException {
        List<UserInfo> existsUserInfoList;
        try {
            existsUserInfoList = userInfoRepository.findByEmailAndStatus(email, "ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_FIND_BY_EMAIL_AND_STATUS);
        }

        UserInfo userInfo;
        if (existsUserInfoList != null && existsUserInfoList.size() > 0) {
            userInfo = existsUserInfoList.get(0);
        } else {
            throw new BaseException(NOT_FOUND_USER);
        }

        return userInfo;
    }



    /**
     * Idx로 회원 조회
     * @param userIdx
     * @return UserInfo
     * @throws BaseException
     */
    public UserInfo retrieveUserByUserIdx(Integer userIdx) throws BaseException {
        UserInfo userInfo;
        try {
            userInfo = userInfoRepository.findById(userIdx).orElse(null);
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_FIND_BY_USERIDX);
        }

        if (userInfo == null || !userInfo.getStatus().equals("ACTIVE")) {
            throw new BaseException(INACTIVE_USER);
        }

        return userInfo;
    }

    /**
     * 존재하는 이메일인지 확인
     * @param email
     * @return existEmail
     * @throws BaseException
     */
    public Boolean retrieveEmail(String email) throws BaseException {
        Boolean existEmail;
        try {
            existEmail = userInfoRepository.existsByEmailAndStatus(email,"ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_EXIST_BY_EMAIL_AND_STATUS);
        }


        return existEmail;
    }

    /**
     * 회원정보 조회 API
     * @param userIdx
     * @return GetUserRes
     * @throws BaseException
     */
    public GetUserRes retrieveUserInfo(int userIdx) throws BaseException {

        UserInfo userInfo = retrieveUserByUserIdx(userIdx);

        Integer id = userInfo.getUserIdx();
        String email = userInfo.getEmail();
        String nickname = userInfo.getNickname();
        return new GetUserRes(id, email, nickname);
    }


}
