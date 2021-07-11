package com.example.dobit.src.doElse.models;

import com.example.dobit.config.BaseEntity;
import com.example.dobit.src.doHabit.models.DoHabit;
import com.example.dobit.src.userIdentity.models.UserIdentity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "DoElse")
public class DoElse extends BaseEntity {
    /**
     * Do 실패대처 idx
     */
    @Id
    @Column(name = "dElseIdx", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dElseIdx;

    /**
     * Do 실패대처 내용
     */
    @Column(name = "dElseContent", nullable = false, length = 45)
    private String dElseContent;

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


    public DoElse(String dElseContent, DoHabit doHabit) {
        this.dElseContent = dElseContent;
        this.doHabit = doHabit;
    }
}