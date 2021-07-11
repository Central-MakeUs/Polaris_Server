package com.example.dobit.src.userIdentityColor;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.userIdentityColor.models.UserIdentityColor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.dobit.config.BaseResponseStatus.FAILED_TO_FIND_BY_USER_IDENTITY_COLORIDX;
import static com.example.dobit.config.BaseResponseStatus.INACTIVE_USER_IDENTITY_COLOR;

@Service
@RequiredArgsConstructor
public class UserIdentityColorProvider {
    private final UserIdentityColorRepository userIdentityColorRepository;
    /**
     * Idx로 컬러 조회
     * @param userIdentityColorIdx
     * @return Identity
     * @throws BaseException
     */
    public UserIdentityColor retrieveUserIdentityColorByUserIdentityColorIdx(Integer userIdentityColorIdx) throws BaseException {
        UserIdentityColor userIdentityColor;
        try {
            userIdentityColor = userIdentityColorRepository.findById(userIdentityColorIdx).orElse(null);
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_FIND_BY_USER_IDENTITY_COLORIDX);
        }

        if (userIdentityColor == null || !userIdentityColor.getStatus().equals("ACTIVE")) {
            throw new BaseException(INACTIVE_USER_IDENTITY_COLOR);
        }

        return userIdentityColor;
    }
}

