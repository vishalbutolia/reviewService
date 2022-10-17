/*
 * @author vishalbutolia
 * @Project reviewService
 * @Date 16/10/22
 * */

package com.signify.reviewservice.validation;

import com.signify.reviewservice.entity.Review;
import com.signify.reviewservice.exception.UnsupportedReviewSource;
import com.signify.reviewservice.util.StringConstantsUtil;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ReviewValidation {
    private static final List<String> supportedReviewSource = Arrays.asList(StringConstantsUtil.GOOGLE_PLAY_STORE,
            StringConstantsUtil.ITUNES);

    public void validateReviewData(List<Review> reviewList) throws UnsupportedReviewSource {
        for (Review review : reviewList) {
            if (review.getReviewString().isEmpty()) throw new IllegalArgumentException("Review string cannot be empty");
            if (review.getReviewSource().isEmpty()) throw new IllegalArgumentException("Review source cannot be empty");
            if (review.getAuthor().isEmpty()) throw new IllegalArgumentException("Author cannot be empty");
            if (review.getProductName().isEmpty()) throw new IllegalArgumentException("Product name cannot be empty");
            if (review.getRating() != null && (review.getRating() < 1 || review.getRating() > 5))
                throw new IllegalArgumentException("Rating should be between 1 to 5");
            if (!supportedReviewSource.contains(review.getReviewSource())) {
                throw new UnsupportedReviewSource("Review source '" + review.getReviewSource() + "' not supported");
            }
        }
    }

    public void validateYearAndMonth(int year, int month) {
        if (year < 1994 || (month < 1 || month > 12)) {
            throw new IllegalArgumentException("request cannot be processed due to invalid year or month values");
        }
    }

    public void validateGetReviewRequest(String storeType, Integer ratingCategory) throws UnsupportedReviewSource {
        if (ratingCategory != null && (ratingCategory < 1 || ratingCategory > 5))
            throw new IllegalArgumentException("Invalid rating category: " + ratingCategory);
        if (storeType != null && !supportedReviewSource.contains(storeType))
            throw new UnsupportedReviewSource(
                    "Review source '" + storeType +
                            "' not supported. Review source must be one of " + supportedReviewSource);
    }
}
