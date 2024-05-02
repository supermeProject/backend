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
@Setter
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
    private String name;

    @Column(name = "profileImage")
    private String profileImage;

    @Column(name = "country")
    private String country;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private MemberType type = MemberType.USER;

    @Builder
    public Member(String profileImage, String name, String email, String password, String country, String address, String city, String phoneNumber, Gender gender, String postcode) {
        this.profileImage = profileImage;
        this.name = name;
        this.email = email;
        this.password = password;
        this.country = country;
        this.address = address;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.postcode = postcode;
    }

    public void profileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
