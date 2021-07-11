package com.example.dobit.src.dontElse.models;

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
@Table(name = "DontElse")
public class DontElse extends BaseEntity {
    /**
     * Dont 실패대처 idx
     */
    @Id
    @Column(name = "dnElseIdx", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dnElseIdx;

    /**
     * Dont 실패대처 내용
     */
    @Column(name = "dnElseContent", nullable = false, length = 45)
    private String dnElseContent;

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


    public DontElse(String dnElseContent, DontHabit dontHabit) {
        this.dnElseContent = dnElseContent;
        this.dontHabit = dontHabit;
    }
}