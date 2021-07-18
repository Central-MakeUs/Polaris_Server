package com.example.dobit.src.user;

import com.example.dobit.config.BaseResponse;
import com.example.dobit.config.BaseResponseStatus;
import com.example.dobit.src.user.models.*;
import com.example.dobit.utils.JwtService;
import com.example.dobit.config.BaseException;
import com.example.dobit.utils.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.Calendar;

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
    @GetMapping("/test")
    public BaseResponse<Void> getTest() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
            System.out.println("year"+year+"month"+month+"day"+day);
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
    public BaseResponse<PostLoginRes> login(@RequestBody PostLoginReq postLoginReq) throws BaseException {
        if (postLoginReq.getEmail() == null || postLoginReq.getEmail().length() == 0) {
            return new BaseResponse<>(EMPTY_EMAIL);
        }
        if (!isRegexEmail(postLoginReq.getEmail())) {
            return new BaseResponse<>(INVALID_EMAIL);
        }
        boolean existEmail =  userInfoProvider.retrieveEmail(postLoginReq.getEmail());
        if(!existEmail){
            return new BaseResponse<>(NOT_EXIST_EMAIL);
        }

        if (postLoginReq.getPassword() == null || postLoginReq.getPassword().length() == 0) {
            return new BaseResponse<>(EMPTY_PASSWORD);
        }



        try {
            PostLoginRes postLoginRes = userInfoService.login(postLoginReq);
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



    /**
     * 비밀번호 재설정하기 API
     * [PATCH] /password
     * @RequestBody patchPasswordReq
     * @return BaseResponse<Void>
     */
    @PatchMapping("/password")
    public BaseResponse<Void> patchPassword(@RequestBody PatchPasswordReq patchPasswordReq) throws BaseException {
        if (patchPasswordReq.getEmail() == null || patchPasswordReq.getEmail().length() == 0) {
            return new BaseResponse<>(EMPTY_EMAIL);
        }
        if (!isRegexEmail(patchPasswordReq.getEmail())){
            return new BaseResponse<>(INVALID_EMAIL);
        }
        Boolean existEmail = userInfoProvider.retrieveEmail(patchPasswordReq.getEmail());
        if(!existEmail){
            return new BaseResponse<>(INVALID_USER);
        }
        if (patchPasswordReq.getPassword() == null || patchPasswordReq.getPassword().length() == 0) {
            return new BaseResponse<>(EMPTY_PASSWORD);
        }
        if (!isRegexPassword(patchPasswordReq.getPassword())) {
            return new BaseResponse<>(INVALID_PASSWORD);
        }
        if (patchPasswordReq.getConfirmPassword() == null || patchPasswordReq.getConfirmPassword().length() == 0) {
            return new BaseResponse<>(EMPTY_CONFIRM_PASSWORD);
        }
        if (!patchPasswordReq.getPassword().equals(patchPasswordReq.getConfirmPassword())) {
            return new BaseResponse<>(DO_NOT_MATCH_PASSWORD);
        }
        try {
            userInfoService.updatePassword(patchPasswordReq);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 회원정보 조회 API
     * [GET] /users/:userIdx
     * @PathVariable userIdx
     * @return BaseResponse<GetUserRes>
     */
    @ResponseBody
    @GetMapping("/users/{userIdx}")
    public BaseResponse<GetUserRes> getUser(@PathVariable Integer userIdx) throws BaseException {

        if (userIdx == null || userIdx <= 0) {
            return new BaseResponse<>(EMPTY_USERIDX);
        }

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
            GetUserRes getUserRes = userInfoProvider.retrieveUserInfo(userIdx);
            return new BaseResponse<>(SUCCESS, getUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


}