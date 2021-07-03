package com.example.dobit.src.user.models;

import com.example.dobit.config.BaseEntity;
import com.example.dobit.src.userToIdentity.models.UserToIdentity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PUBLIC) // Unit Test 를 위해 PUBLIC
@EqualsAndHashCode(callSuper = false)
@Data // from lombok
@Entity // 필수, Class 를 Database Table화 해주는 것이다
@Table(name = "UserInfo") // Table 이름을 명시해주지 않으면 class 이름을 Table 이름으로 대체한다.
public class UserInfo extends BaseEntity {
    /**
     * 유저 ID
     */
    @Id // PK를 의미하는 어노테이션
    @Column(name = "userIdx", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userIdx;

    /**
     * 이메일
     */
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    /**
     * 비밀번호
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * 닉네임
     */
    @Column(name = "nickname", nullable = false, length = 30)
    private String nickname;


    /**
     * 상태
     */
    @Column(name = "status", nullable = false, length = 10)
    private String status = "ACTIVE";


    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private List<UserToIdentity> userToIdentitys = new ArrayList<>();

    public UserInfo(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
