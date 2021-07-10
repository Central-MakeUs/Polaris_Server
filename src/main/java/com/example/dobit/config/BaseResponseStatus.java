package com.example.dobit.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    // 1000 : 요청 성공
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    // 2000 : Request 오류
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2010, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2011, "유효하지 않은 JWT입니다."),
    EMPTY_EMAIL(false, 2020, "이메일을 입력해주세요."),
    INVALID_EMAIL(false, 2021, "이메일 형식이 올바르지 않습니다."),
    EMPTY_PASSWORD(false, 2030, "비밀번호를 입력해주세요."),
    EMPTY_CONFIRM_PASSWORD(false, 2031, "비밀번호 확인을 입력해주세요."),
    WRONG_PASSWORD(false, 2032, "비밀번호를 다시 입력해주세요."),
    DO_NOT_MATCH_PASSWORD(false, 2033, "비밀번호가 일치하지 않습니다."),
    EMPTY_NICKNAME(false, 2034, "닉네임을 입력해주세요."),
    INVALID_PASSWORD(false, 2035, "유효하지 않은 패스워드입니다. 최소 8자리면서 숫자, 문자, 특수문자 각각 1개 이상 포함하십시오."),
    EXIST_EMAIL(false, 2036, "존재하는 이메일입니다."),
    INVALID_USERIDX(false, 2037, "유효하지 않은 userIdx입니다."),
    INVALID_USER(false, 2038, "유효하지 않은 user입니다."),
    INVALID_IDENTITYIDX(false, 2039, "유효하지 않은 정체성입니다."),
    EMPTY_IDENTITYLIST(false, 2040, "정체성 리스트를 입력하세요."),
    EMPTY_IDENTITY_NAME(false, 2041, "정체성을 입력하세요."),
    INVALID_IDENTITY_NAME(false, 2042, "정체성은 20자이내로 입력하세요."),
    EMPTY_USERIDX(false, 2043, "userIdx를 입력하세요."),

    // 3000 : Response 오류
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),
    NOT_FOUND_USER(false, 3010, "존재하지 않는 회원입니다."),
    DUPLICATED_USER(false, 3011, "이미 존재하는 회원입니다."),
    FAILED_TO_FIND_BY_EMAIL_AND_STATUS(false, 3012, "email, status로 회원 정보 조회에 실패하였습니다."),
    FAILED_TO_ENCRYPT_PASSWORD(false, 3013, "password 암호화에 실패하였습니다."),
    FAILED_TO_SAVE_USER_INFO(false, 3014, "유저 정보 저장에 실패했습니다."),
    FAILED_TO_DECRYPT_PASSWORD(false, 3015, "password 복호화에 실패하였습니다."),
    FAILED_TO_EXIST_BY_EMAIL_AND_STATUS(false, 3016, "email,status로 회원 존재 여부 조회에 실패하였습니다."),
    FAILED_TO_SAVE_USERINFO(false, 3017, "UserInfo 저장에 실패했습니다."),
    FAILED_TO_FIND_BY_USERIDX(false, 3018, "userIdx로 UserInfo 조회에 실패했습니다."),
    INACTIVE_USER(false, 3019, "비활성화된 유저입니다."),
    FAILED_TO_FIND_BY_STATUS(false, 3020, "status로 목표(정체성) 조회에 실패했습니다."),
    FAILED_TO_FIND_BY_IDENTITYIDX(false, 3021, "identityIdx로 목표(정체성) 조회에 실패했습니다."),
    INACTIVE_IDENTITY(false, 3022, "비활성화된 정체성입니다."),
    FAILED_TO_SAVE_USER_IDENTITY(false, 3023, "유저 정체성 저장에 실패했습니다."),
    FAILED_TO_FIND_BY_USERINFO_AND_STATUS(false, 3024, "userInfo, status로 유저 정체성 저장에 실패했습니다."),
//    FAILED_TO_GET_USER(false, 3012, "회원 정보 조회에 실패하였습니다."),
//    FAILED_TO_POST_USER(false, 3013, "회원가입에 실패하였습니다."),
//    FAILED_TO_LOGIN(false, 3014, "로그인에 실패하였습니다."),
//    FAILED_TO_DELETE_USER(false, 3015, "회원 탈퇴에 실패하였습니다."),
//    FAILED_TO_PATCH_USER(false, 3016, "개인정보 수정에 실패하였습니다."),



    // 4000 : Database 오류
    SERVER_ERROR(false, 4000, "서버와의 통신에 실패하였습니다."),
    DATABASE_ERROR(false, 4001, "데이터베이스 연결에 실패하였습니다."),

    // 5000 : 기타
    FAILED_TO_SEND_MAIL(false, 5000, "메일 전송에 실패했습니다.");

    // 6000 : 필요시 만들어서 쓰세요

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
