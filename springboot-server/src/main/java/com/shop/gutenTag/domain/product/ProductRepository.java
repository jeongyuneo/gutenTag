package com.shop.gutenTag.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product as p where p.id = ?1 order by p.id")
    Product findProductById(Long id);

    @Query("select p from Product as p where p.category = ?1")
    List<Product> findByCategory(String category);

    @Query("select p from Product as p where p.minorCategory = ?1")
    List<Product> findIdByMinorCategory(String minorCategory);
}
