package com.dbProject.joongo;

import com.dbProject.joongo.aws.s3.AmazonS3Manager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.amazonaws.util.ValidationUtils.assertNotNull;

@SpringBootTest
public class ConnectionTest {
    @Autowired
    private AmazonS3Manager amazonS3Manager;

/*
    @Test
    void testAmazonS3Connection() {
        assertNotNull(amazonS3Manager);
    }
    */

}
