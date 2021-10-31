package com.shop.gutenTag.domain.product;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<StyleTag> styleTags;    // color, style, category

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<ProductTag> productTags;

    @Column(length = 100, nullable = false)
    private String name;
}
