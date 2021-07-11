package com.example.dobit.src.dontMotive.models;

import com.example.dobit.config.BaseEntity;
import com.example.dobit.src.dontHabit.models.DontHabit;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;



@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "DontMotive")
public class DontMotive extends BaseEntity {
    /**
     * Dont 동기파악 idx
     */
    @Id
    @Column(name = "dnMotiveIdx", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dnMotiveIdx;

    /**
     * Dont 동기파악 내용
     */
    @Column(name = "dnMotiveContent", nullable = false, length = 45)
    private String dnMotiveContent;

    /**
     * Dont 습관 idx
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dnhIdx", nullable = false)
    private DontHabit dontHabit;

    /**
     * 상태
     */
    @Column(name = "status", nullable = false, length = 10)
    private String status = "ACTIVE";


    public DontMotive(String dnMotiveContent, DontHabit dontHabit) {
        this.dnMotiveContent = dnMotiveContent;
        this.dontHabit = dontHabit;
    }
}