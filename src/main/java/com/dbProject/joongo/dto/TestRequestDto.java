package com.dbProject.joongo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TestRequestDto {
    private MultipartFile productPicture;
}
