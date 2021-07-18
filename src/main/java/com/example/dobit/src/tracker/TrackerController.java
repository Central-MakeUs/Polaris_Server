package com.example.dobit.src.tracker;

import com.example.dobit.config.BaseException;
import com.example.dobit.config.BaseResponse;
import com.example.dobit.src.tracker.models.GetTrackerRes;
import com.example.dobit.src.user.UserInfoProvider;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.example.dobit.config.BaseResponseStatus.*;


@RestController
@RequiredArgsConstructor
@RequestMapping
public class TrackerController {
    private final JwtService jwtService;
    private final UserInfoProvider userInfoProvider;
    private final TrackerProvider trackerProvider;

    /**
     * 트래커 조회하기 API
     * [GET] /tracker?userIdentity=1&month=1
     * @RequestParam userIdentity , month
     * @return GetTrackerRes
     */

    @ResponseBody
    @GetMapping("/tracker")
    public BaseResponse<GetTrackerRes> getTrackerRes(@RequestParam(value="userIdentityIdx") int userIdentityIdx,
                                                     @RequestParam(value="month") int month) throws BaseException{
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

        //userIdentityIdx vali

//        try {
//            GetTrackerRes getTrackerRes = trackerProvider.retreiveTracker(userIdentityIdx,month,userInfo);
//            return new BaseResponse<>(SUCCESS, getTrackerRes);
            return new BaseResponse<>(SUCCESS);
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
    }

//    /**
//     * 정체성 예시 조회하기 API
//     * [POST] /identity/example
//     * @return BaseResponse<GetIdentityExampleRes>
//     */
//    @ResponseBody
//    @GetMapping("/identity/example")
//    public BaseResponse<List<GetIdentityExampleRes>> getIdentityExample() throws BaseException {
//        Integer jwtUserIdx;
//        try {
//            jwtUserIdx = jwtService.getUserIdx();
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//
//        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(jwtUserIdx);
//        if(userInfo == null){
//            return new BaseResponse<>(INVALID_USER);
//        }
//
//        try {
//            List<GetIdentityExampleRes> getIdentityExampleResList = identityProvider.retrieveOriginIdentity();
//            return new BaseResponse<>(SUCCESS, getIdentityExampleResList);
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//
//    }

}
