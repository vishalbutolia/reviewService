package com.signify.reviewservice;

import com.signify.reviewservice.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReviewServiceApplicationTests {

    @Autowired
    private ReviewService reviewService;

    @Test
    void contextLoads() {
        assertThat(reviewService).isNotNull();
    }

}
