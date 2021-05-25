package com.shop.gutenTag.web;

import com.shop.gutenTag.service.closet.ClosetService;
import com.shop.gutenTag.service.product.ProductService;
import com.shop.gutenTag.web.dto.ProductResponseDto;
import com.shop.gutenTag.web.dto.RecommendRequestDto;
import com.shop.gutenTag.web.dto.RecommendResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ClosetController {
    private final ClosetService closetService;
    private final ProductService productService;

    @PostMapping("/image/upload")
    public RecommendResponseDto uploadImage(@RequestParam("file") MultipartFile multipartFile) {
        return closetService.getRecommend(multipartFile);
    }

    @PostMapping("/closet/product")
    public List<ProductResponseDto> getResult(@RequestBody RecommendRequestDto recommendRequestDto) {
        return productService.findResultList(recommendRequestDto.getMinorCategory());
    }

    @GetMapping("/closet/product/image")
    public ResponseEntity getResultImage(@RequestParam Long id) throws IOException {
        return productService.findProductUrl(id);
    }
}
