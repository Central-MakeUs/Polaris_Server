package com.example.dobit.src.user;

import com.example.dobit.config.BaseResponse;
import com.example.dobit.config.BaseResponseStatus;
import com.example.dobit.src.user.models.*;
import com.example.dobit.utils.JwtService;
import com.example.dobit.config.BaseException;
import com.example.dobit.utils.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import static com.example.dobit.config.BaseResponseStatus.*;
import static com.example.dobit.utils.ValidationRegex.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserInfoController {
    private final UserInfoProvider userInfoProvider;
    private final UserInfoService userInfoService;
    private final JwtService jwtService;
    private final MailService mailService;


    /**
     * test
     */
    @ResponseBody
    @GetMapping("/users/test")
    public BaseResponse<Void> getTest() {
            System.out.println("test");
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    /**
     * 회원가입 API
     * [POST] /users/signup
     * @RequestBody postSignUpReq
     * @return BaseResponse<PostUserRes>
     */
    @PostMapping("/users/signup")
    public BaseResponse<PostSignUpRes> postSignUp(@RequestBody PostSignUpReq postSignUpReq) throws BaseException {

        if (postSignUpReq.getEmail() == null || postSignUpReq.getEmail().length() == 0) {
            return new BaseResponse<>(EMPTY_EMAIL);
        }
        if (!isRegexEmail(postSignUpReq.getEmail())){
            return new BaseResponse<>(INVALID_EMAIL);
        }
        boolean existEmail =  userInfoProvider.retrieveEmail(postSignUpReq.getEmail());
        if(existEmail){
            return new BaseResponse<>(EXIST_EMAIL);
        }
        if (postSignUpReq.getPassword() == null || postSignUpReq.getPassword().length() == 0) {
            return new BaseResponse<>(EMPTY_PASSWORD);
        }
        if (!isRegexPassword(postSignUpReq.getPassword())) {
            return new BaseResponse<>(INVALID_PASSWORD);
        }
        if (postSignUpReq.getConfirmPassword() == null || postSignUpReq.getConfirmPassword().length() == 0) {
            return new BaseResponse<>(EMPTY_CONFIRM_PASSWORD);
        }
        if (!postSignUpReq.getPassword().equals(postSignUpReq.getConfirmPassword())) {
            return new BaseResponse<>(DO_NOT_MATCH_PASSWORD);
        }
        if (postSignUpReq.getNickname() == null || postSignUpReq.getNickname().length() == 0) {
            return new BaseResponse<>(EMPTY_NICKNAME);
        }


        try {
            PostSignUpRes postSignUpRes = userInfoService.createUserInfo(postSignUpReq);
            return new BaseResponse<>(SUCCESS, postSignUpRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 로그인 API
     * [POST] /users/login
     * @RequestBody PostLoginReq
     * @return BaseResponse<PostLoginRes>
     */
    @PostMapping("/users/login")
    public BaseResponse<PostLoginRes> login(@RequestBody PostLoginReq postLoginReq) {
        if (postLoginReq.getEmail() == null || postLoginReq.getEmail().length() == 0) {
            return new BaseResponse<>(EMPTY_EMAIL);
        }
        if (!isRegexEmail(postLoginReq.getEmail())) {
            return new BaseResponse<>(INVALID_EMAIL);
        }
        if (postLoginReq.getPassword() == null || postLoginReq.getPassword().length() == 0) {
            return new BaseResponse<>(EMPTY_PASSWORD);
        }

        try {
            PostLoginRes postLoginRes = userInfoProvider.login(postLoginReq);
            return new BaseResponse<>(SUCCESS, postLoginRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 자동로그인 API
     * [POST] /users/auto-login
     */
    @ResponseBody
    @PostMapping("/users/auto-login")
    public BaseResponse<Void> postAutoLogin() {

        try {
            Integer userIdx = jwtService.getUserIdx();
            userInfoProvider.retrieveUserByUserIdx(userIdx);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 회원 탈퇴 API
     * [PATCH] /users/:userIdx/status
     * @PathVariable userIdx
     */
    @ResponseBody
    @PatchMapping("/users/{userIdx}/status")
    public BaseResponse<Void> patchUserStatus(@PathVariable Integer userIdx) throws BaseException {


        Integer jwtUserIdx;
        try {
            jwtUserIdx = jwtService.getUserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

        if(userIdx != jwtUserIdx){
            return new BaseResponse<>(INVALID_USERIDX);
        }

        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(userIdx);
        if(userInfo == null){
            return new BaseResponse<>(INVALID_USER);
        }


        try {
            userInfoService.updateUserStatus(userIdx);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }



    /**
     * 인증번호 발송하기 API
     * [POST] /mail/auth
     * @RequestBody postMailAuthReq
     * @return BaseResponse<Void>
     */

    @PostMapping("/mail/auth")
    public BaseResponse<PostMailAuthRes> postMailAuth(@RequestBody PostMailAuthReq postMailAuthReq) throws BaseException {
        String email = postMailAuthReq.getEmail();

        if (email == null || email.length() == 0) {
            return new BaseResponse<>(EMPTY_EMAIL);
        }
        if (!isRegexEmail(email)) {
            return new BaseResponse<>(INVALID_EMAIL);
        }
        Boolean existEmail = userInfoProvider.retrieveEmail(email);
        if(!existEmail){
            return new BaseResponse<>(INVALID_USER);
        }

        try {
            PostMailAuthRes postMailAuthRes = mailService.sendMailAuth(email);
            return new BaseResponse<>(SUCCESS,postMailAuthRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }



    }

//    /**
//     * 회원 전체 조회 API
//     * [GET] /users
//     * 회원 닉네임 검색 조회 API
//     * [GET] /users?word=
//     * @return BaseResponse<List<GetUsersRes>>
//     */
//    @ResponseBody
//    @GetMapping("") // (GET) 127.0.0.1:9000/users
//    public BaseResponse<List<GetUsersRes>> getUsers(@RequestParam(required = false) String word) {
//        try {
//            List<GetUsersRes> getUsersResList = userInfoProvider.retrieveUserInfoList(word);
//            if (word == null) {
//                return new BaseResponse<>(BaseResponseStatus.SUCCESS_READ_USERS, getUsersResList);
//            } else {
//                return new BaseResponse<>(BaseResponseStatus.SUCCESS_READ_SEARCH_USERS, getUsersResList);
//            }
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }
//
//    /**
//     * 회원 조회 API
//     * [GET] /users/:userId
//     * @PathVariable userId
//     * @return BaseResponse<GetUserRes>
//     */
//    @ResponseBody
//    @GetMapping("/{userId}")
//    public BaseResponse<GetUserRes> getUser(@PathVariable Integer userId) {
//        if (userId == null || userId <= 0) {
//            return new BaseResponse<>(BaseResponseStatus.EMPTY_USERID);
//        }
//
//        try {
//            GetUserRes getUserRes = userInfoProvider.retrieveUserInfo(userId);
//            return new BaseResponse<>(BaseResponseStatus.SUCCESS_READ_USER, getUserRes);
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }
//
//    /**
//     * 회원가입 API
//     * [POST] /users
//     * @RequestBody PostUserReq
//     * @return BaseResponse<PostUserRes>
//     */
//    @ResponseBody
//    @PostMapping("")
//    public BaseResponse<PostUserRes> postUsers(@RequestBody PostUserReq parameters) {
//        // 1. Body Parameter Validation
//        if (parameters.getEmail() == null || parameters.getEmail().length() == 0) {
//            return new BaseResponse<>(BaseResponseStatus.EMPTY_EMAIL);
//        }
//        if (!isRegexEmail(parameters.getEmail())){
//            return new BaseResponse<>(BaseResponseStatus.INVALID_EMAIL);
//        }
//        if (parameters.getPassword() == null || parameters.getPassword().length() == 0) {
//            return new BaseResponse<>(BaseResponseStatus.EMPTY_PASSWORD);
//        }
//        if (parameters.getConfirmPassword() == null || parameters.getConfirmPassword().length() == 0) {
//            return new BaseResponse<>(BaseResponseStatus.EMPTY_CONFIRM_PASSWORD);
//        }
//        if (!parameters.getPassword().equals(parameters.getConfirmPassword())) {
//            return new BaseResponse<>(BaseResponseStatus.DO_NOT_MATCH_PASSWORD);
//        }
//        if (parameters.getNickname() == null || parameters.getNickname().length() == 0) {
//            return new BaseResponse<>(BaseResponseStatus.EMPTY_NICKNAME);
//        }
//
//        // 2. Post UserInfo
//        try {
//            PostUserRes postUserRes = userInfoService.createUserInfo(parameters);
//            return new BaseResponse<>(BaseResponseStatus.SUCCESS_POST_USER, postUserRes);
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }
//
//    /**
//     * 회원 정보 수정 API
//     * [PATCH] /users/:userId
//     * @PathVariable userId
//     * @RequestBody PatchUserReq
//     * @return BaseResponse<PatchUserRes>
//     */
//    @ResponseBody
//    @PatchMapping("/{userId}")
//    public BaseResponse<PatchUserRes> patchUsers(@PathVariable Integer userId, @RequestBody PatchUserReq parameters) {
//        if (userId == null || userId <= 0) {
//            return new BaseResponse<>(BaseResponseStatus.EMPTY_USERID);
//        }
//
//        if (!parameters.getPassword().equals(parameters.getConfirmPassword())) {
//            return new BaseResponse<>(BaseResponseStatus.DO_NOT_MATCH_PASSWORD);
//        }
//
//        try {
//            return new BaseResponse<>(BaseResponseStatus.SUCCESS_PATCH_USER, userInfoService.updateUserInfo(userId, parameters));
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }
//
//    /**
//     * 로그인 API
//     * [POST] /users/login
//     * @RequestBody PostLoginReq
//     * @return BaseResponse<PostLoginRes>
//     */
//    @PostMapping("/login")
//    public BaseResponse<PostLoginRes> login(@RequestBody PostLoginReq parameters) {
//        // 1. Body Parameter Validation
//        if (parameters.getEmail() == null || parameters.getEmail().length() == 0) {
//            return new BaseResponse<>(BaseResponseStatus.EMPTY_EMAIL);
//        } else if (!isRegexEmail(parameters.getEmail())) {
//            return new BaseResponse<>(BaseResponseStatus.INVALID_EMAIL);
//        } else if (parameters.getPassword() == null || parameters.getPassword().length() == 0) {
//            return new BaseResponse<>(BaseResponseStatus.EMPTY_PASSWORD);
//        }
//
//        // 2. Login
//        try {
//            PostLoginRes postLoginRes = userInfoProvider.login(parameters);
//            return new BaseResponse<>(BaseResponseStatus.SUCCESS_LOGIN, postLoginRes);
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }
//
//    /**
//     * 회원 탈퇴 API
//     * [DELETE] /users/:userId
//     * @PathVariable userId
//     * @return BaseResponse<Void>
//     */
//    @DeleteMapping("/{userId}")
//    public BaseResponse<Void> deleteUsers(@PathVariable Integer userId) {
//        if (userId == null || userId <= 0) {
//            return new BaseResponse<>(BaseResponseStatus.EMPTY_USERID);
//        }
//
//        try {
//            userInfoService.deleteUserInfo(userId);
//            return new BaseResponse<>(BaseResponseStatus.SUCCESS_DELETE_USER);
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }
//
//    /**
//     * JWT 검증 API
//     * [GET] /users/jwt
//     * @return BaseResponse<Void>
//     */
//    @GetMapping("/jwt")
//    public BaseResponse<Void> jwt() {
//        try {
//            int userId = jwtService.getUserId();
//            userInfoProvider.retrieveUserInfo(userId);
//            return new BaseResponse<>(BaseResponseStatus.SUCCESS_JWT);
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }
}