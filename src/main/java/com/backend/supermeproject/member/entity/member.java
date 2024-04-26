package com.backend.supermeproject.member.entity;

import com.backend.supermeproject.global.BaseEntity;
import com.backend.supermeproject.member.role.Gender;
import com.backend.supermeproject.member.role.Role;
import jakarta.persistence.*;

@Entity
@Table(name = "member")
public class member extends BaseEntity {
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

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role;




}
