package com.shop.gutenTag.domain.user;

import com.shop.gutenTag.domain.posts.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    //@Column(nullable = false)
    //private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String userId, String password, String email, Role role) {
        this.name = name;
        this.userId = userId;
        this.password = password;
        //this.email = email;
        this.role = role;
    }

    public User update(String name, String password, String email) {
        this.name = name;
        this.password = password;
        //this.email = email;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
