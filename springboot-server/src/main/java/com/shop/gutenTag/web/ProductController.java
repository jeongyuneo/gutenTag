package com.shop.gutenTag.web;

import com.shop.gutenTag.service.product.ProductService;
import com.shop.gutenTag.web.dto.ProductRequestDto;
import com.shop.gutenTag.web.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;

    @PostMapping("/category")
    public List<ProductResponseDto> getProductId(@RequestBody ProductRequestDto productRequestDto) {
        return productService.findMainList(productRequestDto.getCategory());
    }

    @GetMapping("/product/image")
    public ResponseEntity getImage(@RequestParam Long id) throws IOException {
        return productService.findProductUrl(id);
    }
}
