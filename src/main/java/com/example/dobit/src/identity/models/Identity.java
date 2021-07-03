package com.example.dobit.src.identity.models;

import com.example.dobit.config.BaseEntity;
import com.example.dobit.src.userToIdentity.models.UserToIdentity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PUBLIC) // Unit Test 를 위해 PUBLIC
@EqualsAndHashCode(callSuper = false)
@Data // from lombok
@Entity // 필수, Class 를 Database Table화 해주는 것이다
@Table(name = "Identity") // Table 이름을 명시해주지 않으면 class 이름을 Table 이름으로 대체한다.
public class Identity extends BaseEntity {
    /**
     * 정체성 idx
     */
    @Id // PK를 의미하는 어노테이션
    @Column(name = "identityIdx", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int identityIdx;

    /**
     * 정체성명
     */
    @Column(name = "identityName", nullable = false, length = 45)
    private String identityName;


    /**
     * 상태
     */
    @Column(name = "status", nullable = false, length = 10)
    private String status = "ACTIVE";

    @OneToMany(mappedBy = "identity", cascade = CascadeType.ALL)
    private List<UserToIdentity> userToIdentitys = new ArrayList<>();

    public Identity(String identityName) {
        this.identityName = identityName;
    }
}