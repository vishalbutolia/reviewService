/*
 * @author vishalbutolia
 * @Project reviewService
 * @Date 17/10/22
 * */

package com.signify.reviewservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.signify.reviewservice.TestUtil.MockDataUtil;
import com.signify.reviewservice.dto.ReviewDTO;
import com.signify.reviewservice.entity.Review;
import com.signify.reviewservice.exception.UnsupportedReviewSource;
import com.signify.reviewservice.service.ReviewService;
import com.signify.reviewservice.util.StringConstantsUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ContextConfiguration(classes = ReviewController.class)
@WebMvcTest
class ReviewControllerTest {
    static final List<Review> mockedValidReviewList = MockDataUtil.getValidReviewData();
    static final List<ReviewDTO> mockedValidReviewDTOs = MockDataUtil.getValidReviewDTOData();
    @Autowired
    ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReviewService reviewService;

    @Test
    void saveReviewsTest() throws Exception {
        Mockito.when(reviewService.saveReviews(mockedValidReviewList)).thenReturn(mockedValidReviewDTOs);
        mockMvc.perform(MockMvcRequestBuilders.post("/review-service/api/v1/review")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(mockedValidReviewDTOs)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
        Mockito.verify(reviewService).saveReviews(mockedValidReviewList);
    }

    @Test
    void getReviewTest() throws Exception {
        Mockito.when(
                        reviewService.getReviews(4, StringConstantsUtil.GOOGLE_PLAY_STORE, null, 0, 5))
                .thenReturn(MockDataUtil.getFilteredDTOList(4, StringConstantsUtil.GOOGLE_PLAY_STORE));
        mockMvc.perform(MockMvcRequestBuilders.get("/review-service/api/v1/review")
                        .param("rating", "4")
                        .param("store", StringConstantsUtil.GOOGLE_PLAY_STORE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
        Mockito.verify(reviewService)
                .getReviews(4, StringConstantsUtil.GOOGLE_PLAY_STORE, null, 0, 5);
    }

    @Test
    void getReviewTest_WhenExceptionIsThrown() throws Exception {
        Mockito.doThrow(UnsupportedReviewSource.class).when(reviewService)
                .getReviews(4, StringConstantsUtil.GOOGLE_PLAY_STORE, null, 0, 5);
        mockMvc.perform(MockMvcRequestBuilders.get("/review-service/api/v1/review")
                        .param("rating", "4")
                        .param("store", StringConstantsUtil.GOOGLE_PLAY_STORE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(reviewService)
                .getReviews(4, StringConstantsUtil.GOOGLE_PLAY_STORE, null, 0, 5);
    }

    @Test
    void getMonthlyAverageReviewTest() throws Exception {
        Mockito.when(reviewService.getMonthlyAverageReview(2020, 10)).thenReturn(MockDataUtil.getAverageReviewData());
        mockMvc.perform(MockMvcRequestBuilders.get("/review-service/api/v1/review/average")
                        .param("year", "2020")
                        .param("month", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
        Mockito.verify(reviewService)
                .getMonthlyAverageReview(2020, 10);
    }

    @Test
    void getProductsRatingTest() throws Exception {
        Mockito.when(reviewService.getProductsOverallRating()).thenReturn(MockDataUtil.getOverallRatingData());
        mockMvc.perform(MockMvcRequestBuilders.get("/review-service/api/v1/review/all/category"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].productName", is("Amazon Alexa")))
                .andExpect(jsonPath("$[0].ratingGroup", hasSize(2)));
    }


}
