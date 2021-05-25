package com.shop.gutenTag.domain.product;

import com.shop.gutenTag.domain.closet.Closet;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "closet_id")
    private Closet closet;

    @Column(length = 100, nullable = false)
    private String name;
}
