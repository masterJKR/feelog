package com.feelog.Entity;

import com.feelog.constant.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
//@Table(name = "member_aaaa")
public class Member {
    @Id     //  primary key 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Long id;

    @Column(nullable = false , unique = true) //not null, unique
    private String email; // 로그인 아이디

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name; // 회원 이름

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
