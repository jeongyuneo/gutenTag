package com.shop.gutenTag.service.closet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.gutenTag.web.dto.RecommendResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

@RequiredArgsConstructor
@Service
public class ClosetService {
    @Transactional
    public String saveImage(MultipartFile multipartFile) {
        String uploadPath = "/Users/yurr/IdeaProjects/gutenTag/src/main/resources/Image/Closet";
        try {
            String fileName = multipartFile.getOriginalFilename();
            byte[] bytes = multipartFile.getBytes();
            String imageUploadPath = uploadPath + File.separator + fileName;
            File file = new File(imageUploadPath);
            if (file.exists()) {
                file.delete();
            }
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "upload-success";
    }

    @Transactional
    public RecommendResponseDto getRecommend(MultipartFile multipartFile) {
        String image = saveImage(multipartFile);
        if (image.isEmpty()) {
            return new RecommendResponseDto("none", Arrays.asList());
        }
        RestTemplate restTemplate = new RestTemplate();
        String body = restTemplate.exchange("http://localhost:5000/closet/request", HttpMethod.GET, HttpEntity.EMPTY, String.class).getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        RecommendResponseDto recommendResponseDto = null;
        try {
            recommendResponseDto = objectMapper.readValue(body, RecommendResponseDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return recommendResponseDto;
    }
}
