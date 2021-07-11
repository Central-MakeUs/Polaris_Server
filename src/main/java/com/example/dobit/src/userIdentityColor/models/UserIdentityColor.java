package com.example.dobit.src.userIdentityColor.models;

import com.example.dobit.config.BaseEntity;
import com.example.dobit.src.userIdentity.models.UserIdentity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


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
    private String userIdentityColorName = "black";

    /**
     * 상태
     */
    @Column(name = "status", nullable = false, length = 10)
    private String status = "ACTIVE";


    @OneToMany(mappedBy = "userIdentityColor", cascade = CascadeType.ALL)
    private List<UserIdentity> userIdentities = new ArrayList<>();

    public UserIdentityColor(String userIdentityColorName){
        this.userIdentityColorName = userIdentityColorName;
    }
}
