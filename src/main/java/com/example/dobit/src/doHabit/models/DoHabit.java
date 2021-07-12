package com.example.dobit.src.doHabit.models;

import com.example.dobit.config.BaseEntity;
import com.example.dobit.src.doElse.models.DoElse;
import com.example.dobit.src.doEnv.models.DoEnv;
import com.example.dobit.src.doNext.models.DoNext;
import com.example.dobit.src.doRoutine.models.DoRoutine;
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
@Table(name = "DoHabit")
public class DoHabit extends BaseEntity {
    /**
     * Do 습관 idx
     */
    @Id
    @Column(name = "dhIdx", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dhIdx;

    /**
     * Do 습관명
     */
    @Column(name = "dhName", nullable = false, length = 45)
    private String dhName;

    /**
     * Do 습관 일시
     */
    @Column(name = "dhWhen", nullable = false, length = 45)
    private String dhWhen;

    /**
     * Do 습관 장소
     */
    @Column(name = "dhWhere", nullable = false, length = 45)
    private String dhWhere;


    /**
     * Do 습관 시작
     */
    @Column(name = "dhStart", nullable = false, length = 45)
    private String dhStart;

    /**
     * 유저 정체성 인덱스
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdentityIdx", nullable = false)
    private UserIdentity userIdentity;

    /**
     * 상태
     */
    @Column(name = "status", nullable = false, length = 10)
    private String status = "ACTIVE";

    @OneToMany(mappedBy = "doHabit", cascade = CascadeType.ALL)
    private List<DoElse> doElseList = new ArrayList<>();

    @OneToMany(mappedBy = "doHabit", cascade = CascadeType.ALL)
    private List<DoEnv> doEnvList = new ArrayList<>();

    @OneToMany(mappedBy = "doHabit", cascade = CascadeType.ALL)
    private List<DoNext> doNextList = new ArrayList<>();


    @OneToMany(mappedBy = "doHabit", cascade = CascadeType.ALL)
    private List<DoRoutine> doRoutineList = new ArrayList<>();


    public DoHabit(String dhName, String dhWhen, String dhWhere, String dhStart, UserIdentity userIdentity) {
        this.dhName = dhName;
        this.dhWhen = dhWhen;
        this.dhWhere = dhWhere;
        this.dhStart = dhStart;
        this.userIdentity = userIdentity;
    }
}