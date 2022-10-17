/*
 * @author vishalbutolia
 * @Project reviewService
 * @Date 12/10/22
 * */
package com.signify.reviewservice.service;

import com.signify.reviewservice.dto.AverageReview;
import com.signify.reviewservice.dto.ProductRating;
import com.signify.reviewservice.dto.ReviewDTO;
import com.signify.reviewservice.entity.Review;
import com.signify.reviewservice.exception.UnsupportedReviewSource;
import com.signify.reviewservice.repository.ReviewRepository;
import com.signify.reviewservice.repository.ReviewRepositoryCustom;
import com.signify.reviewservice.transformer.ReviewTransformer;
import com.signify.reviewservice.util.DateTimeUtil;
import com.signify.reviewservice.validation.ReviewValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewService {
    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    private ReviewRepository repository;

    @Autowired
    private ReviewRepositoryCustom reviewRepository;

    @Autowired
    private ReviewValidation validationService;

    public List<ReviewDTO> saveReviews(List<Review> reviews) throws UnsupportedReviewSource {
        logger.info("saving reviews");
        validationService.validateReviewData(reviews);
        return ReviewTransformer.transformFrom(repository.saveAll(reviews));
    }

    public List<ReviewDTO> getReviews(Integer rating, String storeType, LocalDate startDate, int page, int size)
            throws UnsupportedReviewSource {
        logger.info("getting reviews");
        validationService.validateGetReviewRequest(storeType, rating);
        LocalDate endDate = null;
        if (startDate != null) {
            endDate = DateTimeUtil.incrementADateByOneDay(startDate);
            startDate = DateTimeUtil.decrementADayByOne(startDate);
        }
        return ReviewTransformer.
                transformFrom(reviewRepository.getReviewByFilter(rating, storeType, startDate, endDate, page, size));
    }

    public List<AverageReview> getMonthlyAverageReview(int year, int month) {
        logger.info("getting monthly average reviews");
        validationService.validateYearAndMonth(year, month);
        LocalDate startDate = LocalDate.of(year, month, 1).minusDays(1);
        LocalDate endDate = LocalDate.of(year, month, 1).plusMonths(1);
        return reviewRepository.getMonthlyAverageReviews(startDate, endDate);
    }

    public List<ProductRating> getProductsOverallRating() {
        logger.info("getting product wise overall rating");
        return reviewRepository.getProductsOverallRating();
    }
}
