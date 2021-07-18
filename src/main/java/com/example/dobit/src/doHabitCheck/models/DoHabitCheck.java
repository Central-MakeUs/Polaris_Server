package com.example.dobit.src.doHabitCheck.models;

import com.example.dobit.config.BaseEntity;
import com.example.dobit.src.doHabit.models.DoHabit;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userIdentity.models.UserIdentity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;


@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "DoHabitCheck")
public class DoHabitCheck extends BaseEntity {
    /**
     * idx
     */
    @Id
    @Column(name = "dhCheckIdx", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dhCheckIdx;

    /**
     * Do 습관 idx
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dhIdx", nullable = false)
    private DoHabit doHabit;

    /**
     * 유저 idx
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx", nullable = false)
    private UserInfo userInfo;

    @CreationTimestamp
    @Column(name = "checkDate", nullable = false, updatable = false)
    private Date checkDate;

    /**
     * 유저 정체성 인덱스
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdentityIdx", nullable = false)
    private UserIdentity userIdentity;

    /**
     * 체크한 날짜 - 년
     */
    @Column(name = "year", nullable = false)
    private int year;

    /**
     * 체크한 날짜 - 월
     */
    @Column(name = "month", nullable = false)
    private int month;


    /**
     * 체크한 날짜 - 일
     */
    @Column(name = "day", nullable = false)
    private int day;

    /**
     * 상태
     */
    @Column(name = "status", nullable = false, length = 10)
    private String status = "ACTIVE";


    public DoHabitCheck(DoHabit doHabit, UserInfo userInfo,UserIdentity userIdentity,int year, int month, int day) {
        this.doHabit = doHabit;
        this.userInfo = userInfo;
        this.userIdentity = userIdentity;
        this.year = year;
        this.month = month;
        this.day = day;
    }
}