package com.example.dobit.src.userIdentityColor.models;

import com.example.dobit.config.BaseEntity;
import com.example.dobit.src.user.models.UserInfo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "UserIdentityColor")
public class UserIdentityColor extends BaseEntity {
    /**
     * 유저 정체성 컬러 idx
     */
    @Id
    @Column(name = "userIdentityColorIdx", nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userIdentityColorIdx;

    /**
     * 컬러
     */
    @Column(name = "userIdentityColorName",nullable = false,length = 45)
    private String userIdentityColorName;

    /**
     * 상태
     */
    @Column(name = "status", nullable = false, length = 10)
    private String status = "ACTIVE";


    public UserIdentityColor(String userIdentityColorName){
        this.userIdentityColorName = userIdentityColorName;
    }
}
