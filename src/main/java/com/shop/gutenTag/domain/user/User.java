package com.shop.gutenTag.domain.user;

//import com.shop.gutenTag.domain.order.Order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    //@OneToMany(mappedBy = "order_id")
    //private List<Order> orders = new ArrayList<Order>();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false, length = 300)
    private String address;

    @Builder
    public User(String name, String email, String password, String telephone, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.address = address;
    }

    public User update(String telephone, String address) {
        this.telephone = telephone;
        this.address = address;
        return this;
    }
}
