package com.backend.supermeproject.member.entity;

import com.backend.supermeproject.global.BaseEntity;
import com.backend.supermeproject.global.role.Gender;
import com.backend.supermeproject.global.role.MemberType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId")
    private Long memberId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "myimage")
    private String myimage;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private MemberType type = MemberType.USER;


    @Builder
    public Member(String email, String password, String nickname, String address1, String phoneNumber, Gender gender) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.address1 = address1;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }
}
