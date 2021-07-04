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

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "Identity")
public class Identity extends BaseEntity {
    /**
     * 정체성 idx
     */
    @Id
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