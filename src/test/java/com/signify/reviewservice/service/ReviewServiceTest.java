/*
 * @author vishalbutolia
 * @Project reviewService
 * @Date 17/10/22
 * */

package com.signify.reviewservice.service;

import com.signify.reviewservice.TestUtil.MockDataUtil;
import com.signify.reviewservice.dto.AverageReview;
import com.signify.reviewservice.dto.ProductRating;
import com.signify.reviewservice.dto.ReviewDTO;
import com.signify.reviewservice.entity.Review;
import com.signify.reviewservice.exception.UnsupportedReviewSource;
import com.signify.reviewservice.repository.ReviewRepository;
import com.signify.reviewservice.repository.ReviewRepositoryCustom;
import com.signify.reviewservice.util.DateTimeUtil;
import com.signify.reviewservice.util.StringConstantsUtil;
import com.signify.reviewservice.validation.ReviewValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@SpringBootTest
class ReviewServiceTest {

    static final List<Review> mockedValidReviewList = MockDataUtil.getValidReviewData();
    static final List<Review> mockedInvalidReviewList = MockDataUtil.getInValidReviewData();
    @Mock
    private ReviewRepository repository;
    @Mock
    private ReviewRepositoryCustom reviewRepository;
    @Mock
    private ReviewValidation validationService;
    @InjectMocks
    private ReviewService reviewService;

    @Test
    void saveAccountTest_WhenValidData() throws UnsupportedReviewSource {
        when(repository.saveAll(anyList())).thenReturn(mockedValidReviewList);
        List<ReviewDTO> savedReviews = reviewService.saveReviews(mockedValidReviewList);
        assertThat(savedReviews.size()).isEqualTo(2);
        Mockito.verify(repository).saveAll(anyList());
        Mockito.verify(validationService, Mockito.times(1)).validateReviewData(anyList());
    }

    @Test
    void saveAccountTest_WhenInValidData() throws UnsupportedReviewSource {
        doThrow(UnsupportedReviewSource.class).when(validationService).validateReviewData(anyList());
        Assertions.assertThrows(UnsupportedReviewSource.class, () -> reviewService.saveReviews(mockedInvalidReviewList));
        Mockito.verify(repository, times(0)).saveAll(anyList());
        Mockito.verify(validationService, Mockito.times(1)).validateReviewData(anyList());
    }

    @Test
    void getReviewTest_WithRating_WithStoreType_WithDate() throws UnsupportedReviewSource {
        doNothing().when(validationService).validateGetReviewRequest(anyString(), anyInt());
        LocalDate date = LocalDate.of(2022, 10, 16);
        LocalDate endDate = DateTimeUtil.incrementADateByOneDay(date);
        when(reviewRepository.
                getReviewByFilter(4, StringConstantsUtil.GOOGLE_PLAY_STORE, date, endDate, 0, 5)).
                thenReturn(MockDataUtil.getFilteredList(4, StringConstantsUtil.GOOGLE_PLAY_STORE));
        List<ReviewDTO> response =
                reviewService.getReviews(4, StringConstantsUtil.GOOGLE_PLAY_STORE, date, 0, 5);
        assertThat(response.size()).isEqualTo(1);
        assertThat(response.get(0).getRating()).isEqualTo(4);
    }

    @Test
    void getMonthlyAverageReviewTest() {
        LocalDate startDate = LocalDate.of(2022, 10, 1).minusDays(1);
        LocalDate endDate = LocalDate.of(2022, 10, 1).plusMonths(1);
        doNothing().when(validationService).validateYearAndMonth(anyInt(), anyInt());
        when(reviewRepository.getMonthlyAverageReviews(startDate, endDate))
                .thenReturn(MockDataUtil.getAverageReviewData());
        List<AverageReview> response = reviewService.getMonthlyAverageReview(2022, 10);
        assertThat(response).hasSize(2);
        assertThat(response.
                stream().filter(e->e.getReviewSource().equals(StringConstantsUtil.ITUNES)).findFirst().get()
                .getAverageRating()).
                isEqualTo(3.5);
    }

    @Test
    void getProductsOverallRatingTest() {
        when(reviewRepository.getProductsOverallRating()).thenReturn(MockDataUtil.getOverallRatingData());
        List<ProductRating> response = reviewService.getProductsOverallRating();
        assertThat(response).isNotNull();
        assertThat(response.get(0).getProductName()).isEqualTo("Amazon Alexa");
        assertThat(response.get(0).getRatingGroup()).hasSize(2);
        assertThat(response.get(0).getRatingGroup()
                .stream()
                .filter(e -> e.getCategory().equals(4)).findFirst().get().getCount())
                .isEqualTo(10);
    }


}
