package com.example.dobit.src.auth;

import com.example.dobit.config.BaseException;
import com.example.dobit.config.BaseResponse;
import com.example.dobit.src.auth.models.PostMailAuthReq;
import com.example.dobit.src.auth.models.PostMailAuthRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.example.dobit.config.BaseResponseStatus.*;
import static com.example.dobit.utils.ValidationRegex.isRegexEmail;


@RestController
@RequiredArgsConstructor
@RequestMapping
public class AuthController {
    private final AuthService authService;


    /**
     * 인증번호 발송하기 API
     * [POST] /mail/auth
     * @RequestBody postMailAuthReq
     * @return BaseResponse<Void>
     */

    @PostMapping("/mail/auth")
    public BaseResponse<PostMailAuthRes> postMailAuth(HttpServletRequest request, @RequestBody PostMailAuthReq postMailAuthReq) throws BaseException {
        String email = postMailAuthReq.getEmail();

        if (email == null || email.length() == 0) {
            return new BaseResponse<>(EMPTY_EMAIL);
        }
        if (!isRegexEmail(email)) {
            return new BaseResponse<>(INVALID_EMAIL);
        }


        try {
            PostMailAuthRes postMailAuthRes =authService.sendMailAuth(email);
            return new BaseResponse<>(SUCCESS,postMailAuthRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }



    }
}