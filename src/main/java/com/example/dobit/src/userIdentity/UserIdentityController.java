package com.example.dobit.src.userIdentity;


import com.example.dobit.config.BaseException;
import com.example.dobit.config.BaseResponse;
import com.example.dobit.src.identity.IdentityProvider;
import com.example.dobit.src.identity.models.Identity;
import com.example.dobit.src.user.UserInfoProvider;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userIdentity.models.*;
import com.example.dobit.src.userIdentityColor.UserIdentityColorProvider;
import com.example.dobit.src.userIdentityColor.models.UserIdentityColor;
import com.example.dobit.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.dobit.config.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserIdentityController {
    private final UserIdentityProvider userIdentityProvider;
    private final UserIdentityService userIdentityService;
    private final UserInfoProvider userInfoProvider;
    private final IdentityProvider identityProvider;
    private final UserIdentityColorProvider userIdentityColorProvider;
    private final JwtService jwtService;


    /**
     * 정체성 생성하기 API
     * [POST] /identity
     * @RequestBody postIdentityReq
     * @return BaseResponse<Void>
     */
    @ResponseBody
    @PostMapping("/identity")
    public BaseResponse<Void> postIdentity(@RequestBody PostIdentityReq postIdentityReq) throws BaseException {
        Integer jwtUserIdx;
        try {
            jwtUserIdx = jwtService.getUserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(jwtUserIdx);
        if(userInfo == null){
            return new BaseResponse<>(INVALID_USER);
        }

        List<Integer> identityList = postIdentityReq.getIdentityList();

        if(identityList.isEmpty() || identityList == null){
            return new BaseResponse<>(EMPTY_IDENTITYLIST);
        }

        for (int i = 0; i < identityList.size(); i++) {
            Integer identityIdx = identityList.get(i);

            Identity identity = identityProvider.retrieveIdentityByIdentityIdx(identityIdx);
            if(identity == null){
                return new BaseResponse<>(INVALID_IDENTITYIDX);
            }

        }
        try {
            userIdentityService.createIdentity(userInfo,postIdentityReq);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }



    /**
     * 정체성 직접 생성하기 API
     * [POST] /direct-identity
     * @RequestBody postDirectIdentityReq
     * @return BaseResponse<PostDirectIdentityRes>
     */
    @ResponseBody
    @PostMapping("/direct-identity")
    public BaseResponse<PostIdentityRes> postDirectIdentity(@RequestBody PostDirectIdentityReq postDirectIdentityReq) throws BaseException {
        Integer jwtUserIdx;
        try {
            jwtUserIdx = jwtService.getUserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(jwtUserIdx);
        if(userInfo == null){
            return new BaseResponse<>(INVALID_USER);
        }


        if(postDirectIdentityReq.getIdentityName() == null || postDirectIdentityReq.getIdentityName().length() == 0){
            return new BaseResponse<>(EMPTY_IDENTITY_NAME);
        }

        if(postDirectIdentityReq.getIdentityName().length() >= 45){
            return new BaseResponse<>(INVALID_IDENTITY_NAME);
        }


        try {
            PostIdentityRes postIdentityRes = userIdentityService.createDirectIdentity(userInfo,postDirectIdentityReq);
            return new BaseResponse<>(SUCCESS, postIdentityRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }

    /**
     * 정체성 추가하기 API
     * [POST] /identity/plus
     * @RequestBody postIdentityPlusReq
     * @return BaseResponse<PostIdentityRes>
     */
    @ResponseBody
    @PostMapping("/identity/plus")
    public BaseResponse<PostIdentityRes> postIdentityPlus(@RequestBody PostIdentityPlusReq postIdentityPlusReq) throws BaseException {
        Integer jwtUserIdx;
        try {
            jwtUserIdx = jwtService.getUserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(jwtUserIdx);
        if(userInfo == null){
            return new BaseResponse<>(INVALID_USER);
        }


        if(postIdentityPlusReq.getIdentityName() == null || postIdentityPlusReq.getIdentityName().length() == 0){
            return new BaseResponse<>(EMPTY_IDENTITY_NAME);
        }

        if(postIdentityPlusReq.getUserIdentityColorIdx()!=null){
            ArrayList<Integer> colorList = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
            if(!colorList.contains(postIdentityPlusReq.getUserIdentityColorIdx())){
                return new BaseResponse<>(INVALID_USER_IDENTITY_COLOR);
            }
        }



        try {
            PostIdentityRes postIdentityRes = userIdentityService.createIdentityPlus(userInfo,postIdentityPlusReq);
            return new BaseResponse<>(SUCCESS, postIdentityRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }

    /**
     * 정체성 조회하기 API
     * [POST] /identity
     * @return BaseResponse<GetIdentitiesRes>
     */
    @ResponseBody
    @GetMapping("/identity")
    public BaseResponse<List<GetIdentitiesRes>> getIdentities() throws BaseException {
        Integer jwtUserIdx;
        try {
            jwtUserIdx = jwtService.getUserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(jwtUserIdx);
        if(userInfo == null){
            return new BaseResponse<>(INVALID_USER);
        }

        try {
            List<GetIdentitiesRes> getIdentitiesResList = userIdentityProvider.retrieveIdentities(userInfo);
            return new BaseResponse<>(SUCCESS, getIdentitiesResList);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }

    /**
     * 정체성 상세 조회하기 API
     * [POST] /identity/:userIdentityIdx
     * @return BaseResponse<GetIdentitiesRes>
     */
    @ResponseBody
    @GetMapping("/identity/{userIdentityIdx}")
    public BaseResponse<GetIdentityRes> getIdentity(@PathVariable int userIdentityIdx) throws BaseException {
        Integer jwtUserIdx;
        try {
            jwtUserIdx = jwtService.getUserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(jwtUserIdx);
        if(userInfo == null){
            return new BaseResponse<>(INVALID_USER);
        }

        UserIdentity userIdentity = userIdentityProvider.retrieveUserIdentityByUserIdentityIdx(userIdentityIdx);
        if(userIdentity==null){
            return new BaseResponse<>(INVALID_USER_IDENTITY);
        }

        Boolean existUserIdentity = userIdentityProvider.retrieveExistingUserIdentity(userInfo,userIdentityIdx);
        if (existUserIdentity == null){
            return new BaseResponse<>(DO_NOT_MATCH_USER_AND_USERIDENTITYIDX);
        }

        try {
            GetIdentityRes getIdentityRes = userIdentityProvider.retrieveIdentity(userIdentity);
            return new BaseResponse<>(SUCCESS, getIdentityRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }


    /**
     * 정체성 삭제하기 API
     * [PATCH] /identity/:userIdentityIdx/status
     * @PathVariable userIdentityIdx
     * @return BaseResponse<Void>
     */
    @ResponseBody
    @PatchMapping("/identity/{userIdentityIdx}/status")
    public BaseResponse<Void> patchIdentityStatus(@PathVariable Integer userIdentityIdx) throws BaseException {
        Integer jwtUserIdx;
        try {
            jwtUserIdx = jwtService.getUserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(jwtUserIdx);
        if(userInfo == null){
            return new BaseResponse<>(INVALID_USER);
        }

        UserIdentity userIdentity = userIdentityProvider.retrieveUserIdentityByUserIdentityIdx(userIdentityIdx);
        if(userIdentity==null){
            return new BaseResponse<>(INVALID_USER_IDENTITY);
        }

        Boolean existUserIdentity = userIdentityProvider.retrieveExistingUserIdentity(userInfo,userIdentityIdx);
        if (existUserIdentity == null){
            return new BaseResponse<>(DO_NOT_MATCH_USER_AND_USERIDENTITYIDX);
        }


        try {
            userIdentityService.updateIdentityStatus(userIdentityIdx);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * 정체성 수정하기 API
     * [PATCH] /identity/:userIdentityIdx
     * @PathVariable userIdentityIdx
     * @RequestBody patchIdentityReq
     * @return BaseResponse<Void>
     */
    @ResponseBody
    @PatchMapping("/identity/{userIdentityIdx}")
    public BaseResponse<Void> patchIdentityColor(@PathVariable Integer userIdentityIdx, @RequestBody PatchIdentityReq patchIdentityReq) throws BaseException {
        Integer jwtUserIdx;
        try {
            jwtUserIdx = jwtService.getUserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(jwtUserIdx);
        if(userInfo == null){
            return new BaseResponse<>(INVALID_USER);
        }

        UserIdentity userIdentity = userIdentityProvider.retrieveUserIdentityByUserIdentityIdx(userIdentityIdx);
        if(userIdentity==null){
            return new BaseResponse<>(INVALID_USER_IDENTITY);
        }

        Boolean existUserIdentity = userIdentityProvider.retrieveExistingUserIdentity(userInfo,userIdentityIdx);
        if (existUserIdentity == null){
            return new BaseResponse<>(DO_NOT_MATCH_USER_AND_USERIDENTITYIDX);
        }

        // 추가
        if(patchIdentityReq.getUserIdentityName() == null || patchIdentityReq.getUserIdentityName().length() == 0){
            return new BaseResponse<>(EMPTY_IDENTITY_NAME);
        }

        if(patchIdentityReq.getUserIdentityName().length() >= 45){
            return new BaseResponse<>(INVALID_IDENTITY_NAME);
        }

        if (patchIdentityReq.getUserIdentityColorIdx() == null || patchIdentityReq.getUserIdentityColorIdx() <= 0) {
            return new BaseResponse<>(EMPTY_USERIDENTITYCOLORIDX);
        }

        ArrayList<Integer> colorList = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        if(!colorList.contains(patchIdentityReq.getUserIdentityColorIdx())){
            return new BaseResponse<>(INVALID_USER_IDENTITY_COLOR);
        }

        UserIdentityColor userIdentityColor = userIdentityColorProvider.retrieveUserIdentityColorByUserIdentityColorIdx(patchIdentityReq.getUserIdentityColorIdx());
//        if(userIdentityColor == null){
//            return new BaseResponse<>(INVALID_USER_IDENTITY_COLOR);
//        }


        try {
            userIdentityService.updateIdentityColor(userIdentity,userIdentityColor,patchIdentityReq.getUserIdentityName() );
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
//    /**
//     * 정체성 컬러 수정하기 API
//     * [PATCH] /identity/:userIdentityIdx/color
//     * @PathVariable userIdentityIdx
//     * @RequestBody patchIdentityColorReq
//     * @return BaseResponse<Void>
//     */
//    @ResponseBody
//    @PatchMapping("/identity/{userIdentityIdx}/color")
//    public BaseResponse<Void> patchIdentityColor(@PathVariable Integer userIdentityIdx, @RequestBody PatchIdentityColorReq patchIdentityColorReq) throws BaseException {
//        Integer jwtUserIdx;
//        try {
//            jwtUserIdx = jwtService.getUserIdx();
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(jwtUserIdx);
//        if(userInfo == null){
//            return new BaseResponse<>(INVALID_USER);
//        }
//
//        UserIdentity userIdentity = userIdentityProvider.retrieveUserIdentityByUserIdentityIdx(userIdentityIdx);
//        if(userIdentity==null){
//            return new BaseResponse<>(INVALID_USER_IDENTITY);
//        }
//
//        Boolean existUserIdentity = userIdentityProvider.retrieveExistingUserIdentity(userInfo,userIdentityIdx);
//        if (existUserIdentity == null){
//            return new BaseResponse<>(DO_NOT_MATCH_USER_AND_USERIDENTITYIDX);
//        }
//
//
//        if (patchIdentityColorReq.getUserIdentityColorIdx() == null || patchIdentityColorReq.getUserIdentityColorIdx() <= 0) {
//            return new BaseResponse<>(EMPTY_USERIDENTITYCOLORIDX);
//        }
//
//        UserIdentityColor userIdentityColor = userIdentityColorProvider.retrieveUserIdentityColorByUserIdentityColorIdx(patchIdentityColorReq.getUserIdentityColorIdx());
//        if(userIdentityColor == null){
//            return new BaseResponse<>(INVALID_USER_IDENTITY_COLOR);
//        }
//
//
//        try {
//            userIdentityService.updateIdentityColor(userIdentity,userIdentityColor);
//            return new BaseResponse<>(SUCCESS);
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }

}
