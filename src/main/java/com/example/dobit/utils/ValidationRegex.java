package com.example.dobit.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationRegex {
    public static boolean isRegexEmail(String target) {
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    public static boolean isRegexPassword(String target){
        // 가장 많이 사용되는 최소 8자리에 숫자, 문자, 특수문자 각각 1개 이상 포함
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }


}
