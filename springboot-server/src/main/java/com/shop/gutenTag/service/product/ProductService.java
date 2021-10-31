package com.shop.gutenTag.service.product;

import com.shop.gutenTag.domain.product.Product;
import com.shop.gutenTag.domain.product.ProductRepository;
import com.shop.gutenTag.web.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private static final Random RANDOM = new Random();
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ResponseEntity findProductUrl(Long id) throws IOException {
        InputStream inputStream = new FileInputStream(findProduct(id).getImageUrl());
        byte[] imageByte = IOUtils.toByteArray(inputStream);
        inputStream.close();
        return new ResponseEntity(imageByte, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public Product findProduct(Long id) {
        return productRepository.findProductById(id);
    }

    @Transactional
    public List<ProductResponseDto> findMainList(String category) {
        List<Product> products = productRepository.findByCategory(category);
        List<ProductResponseDto> productList = new ArrayList<ProductResponseDto>();
        for (int i = 0; i < 5; i++) {
            productList.add(new ProductResponseDto(products.get(RANDOM.nextInt(products.size()))));
        }
        return productList;
    }

    @Transactional
    public List<ProductResponseDto> findResultList(String minorCategory) {
        return productRepository.findIdByMinorCategory(minorCategory)
                .stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }
}
