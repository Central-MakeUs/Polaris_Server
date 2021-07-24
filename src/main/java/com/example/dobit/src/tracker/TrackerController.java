package com.example.dobit.src.tracker;

import com.example.dobit.config.BaseException;
import com.example.dobit.config.BaseResponse;
import com.example.dobit.src.tracker.models.GetTrackerRes;
import com.example.dobit.src.user.UserInfoProvider;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userIdentity.UserIdentityProvider;
import com.example.dobit.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.dobit.config.BaseResponseStatus.*;


@RestController
@RequiredArgsConstructor
@RequestMapping
public class TrackerController {
    private final JwtService jwtService;
    private final UserInfoProvider userInfoProvider;
    private final TrackerProvider trackerProvider;
    private final UserIdentityProvider userIdentityProvider;

//    /**
//     * 트래커 조회하기 API - 하나만 조회
//     * [GET] /tracker?userIdentityIdx=1&year=2021&month=7
//     * @RequestParam userIdentity, year, month
//     * @return GetTrackerRes
//     */
//
//    @ResponseBody
//    @GetMapping("/tracker")
//    public BaseResponse<GetTrackerRes> getTrackerRes(@RequestParam(value="userIdentityIdx") int userIdentityIdx,
//                                                     @RequestParam(value ="year") int year,
//                                                     @RequestParam(value="month") int month) throws BaseException{
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
//        try {
//            GetTrackerRes getTrackerRes = trackerProvider.retreiveTracker(userIdentity,year,month,userInfo);
//            return new BaseResponse<>(SUCCESS, getTrackerRes);
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }

    /**
     * 트래커 조회하기 API
     * [GET] /tracker?year=2021&month=7
     * @RequestParam year, month
     * @return List<GetTrackerRes>
     */

    @ResponseBody
    @GetMapping("/tracker")
    public BaseResponse<List<GetTrackerRes>> getTrackerRes(@RequestParam(value ="year") int year,
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


        try {
            List<GetTrackerRes> getTrackerResList = trackerProvider.retreiveTracker(year,month,userInfo);
            return new BaseResponse<>(SUCCESS, getTrackerResList);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
