package com.example.dobit.src.tracker;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
@RequestMapping
public class TrackerController {

    /**
     *
     */

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
