package com.signify.reviewservice.repository;

import com.signify.reviewservice.dto.AverageReview;
import com.signify.reviewservice.dto.ProductRating;
import com.signify.reviewservice.entity.Review;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface ReviewRepositoryCustom {
    Page<Review> getReviewByFilter(Integer rating, String storeType,
                                   LocalDate startDate, LocalDate endDate, int page, int size);

    List<AverageReview> getMonthlyAverageReviews(LocalDate startDate, LocalDate endDate);

    List<ProductRating> getProductsOverallRating();
}
