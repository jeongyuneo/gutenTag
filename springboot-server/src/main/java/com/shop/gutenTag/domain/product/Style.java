package com.shop.gutenTag.domain.product;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Style {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "style_id")
    private Long id;    // 0: 포멀, 1: 캐주얼, 2: 스트릿

    @OneToMany(mappedBy = "style", cascade = CascadeType.ALL)
    private List<StyleTag> styleTags;

    @OneToMany(mappedBy = "style", cascade = CascadeType.ALL)
    private List<Product> products;

    @Column(nullable = false)
    private String name;
}
