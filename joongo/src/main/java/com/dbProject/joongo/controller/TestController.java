package com.dbProject.joongo.controller;

import com.dbProject.joongo.aws.s3.AmazonS3Manager;
import com.dbProject.joongo.domain.Uuid;
import com.dbProject.joongo.mapper.UuidMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final UuidMapper uuidMapper;
    private final AmazonS3Manager s3Manager;

    @GetMapping("/api/hello")
    public String test() {
        return "Hello, world!";
    }

    //upload test 컨트롤러
    @PostMapping(value = "/api/products/test", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String testMultiPart(@RequestBody MultipartFile file) {
        if (file.isEmpty()) {
            return "file empty";
        }
        String randomUuid = UUID.randomUUID().toString();
        Uuid newUuid = Uuid.builder()
                .uuid(randomUuid)
                .build();
        return s3Manager.uploadFile(s3Manager.generateReviewKeyName(newUuid),
                file);
    }
}
