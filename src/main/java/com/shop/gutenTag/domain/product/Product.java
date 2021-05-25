package com.shop.gutenTag.domain.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductTag> productTags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "style_id")
    private Style style;

    @Column(columnDefinition = "varchar", nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String price;

    @Column(nullable = false)
    private String category;

    @Column(name = "minor_category")
    private String minorCategory;

    @Builder
    public Product(String name, String imageUrl, String price, String category, String minorCategory) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
        this.minorCategory = minorCategory;
    }
}
