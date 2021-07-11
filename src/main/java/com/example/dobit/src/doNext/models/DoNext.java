package com.example.dobit.src.doNext.models;

import com.example.dobit.config.BaseEntity;
import com.example.dobit.src.doHabit.models.DoHabit;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "DoNext")
public class DoNext extends BaseEntity {
    /**
     * Do 다음단계 idx
     */
    @Id
    @Column(name = "dNextIdx", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dNextIdx;

    /**
     * Do 다음단계 내용
     */
    @Column(name = "dNextContent", nullable = false, length = 45)
    private String dNextContent;

    /**
     * Do 습관 idx
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dhIdx", nullable = false)
    private DoHabit doHabit;

    /**
     * 상태
     */
    @Column(name = "status", nullable = false, length = 10)
    private String status = "ACTIVE";


    public DoNext(String dNextContent, DoHabit doHabit) {
        this.dNextContent = dNextContent;
        this.doHabit = doHabit;
    }
}