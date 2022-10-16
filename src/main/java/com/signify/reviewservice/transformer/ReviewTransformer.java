/*
 * @author vishalbutolia
 * @Project reviewService
 * @Date 16/10/22
 * */

package com.signify.reviewservice.transformer;

import com.signify.reviewservice.dto.ReviewDTO;
import com.signify.reviewservice.entity.Review;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ReviewTransformer {
    private ReviewTransformer() {
        throw new IllegalArgumentException("Cannot instantiate Transformer");
    }
    public static ReviewDTO transformFrom(Optional<Review> reviews) {
        Review review = reviews.orElse(null);
        return transformFrom(review);
    }

    public static List<ReviewDTO> transformFrom(Collection<Review> reviews) {
        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        if (reviews != null && !reviews.isEmpty()) {
            for (Review review : reviews) {
                reviewDTOS.add(transformFrom(review));
            }
        }
        return reviewDTOS;
    }

    public static ReviewDTO transformFrom(Review review) {
        ReviewDTO reviewDTO = null;
        if (review != null) {
            reviewDTO = new ReviewDTO();
            BeanUtils.copyProperties(review, reviewDTO);
            reviewDTO.setReview(review.getReviewString());
        }
        return reviewDTO;
    }

    public static Review transformTo(ReviewDTO reviewDTO) {
        Review review = null;
        if (reviewDTO != null) {
            review = new Review();
            BeanUtils.copyProperties(reviewDTO, review);
            review.setReviewString(reviewDTO.getReview());
        }
        return review;
    }

    public static List<Review> transformTo(Collection<ReviewDTO> reviewDTOS) {
        List<Review> reviews = new ArrayList<>();
        if (reviewDTOS != null && !reviewDTOS.isEmpty()) {
            for (ReviewDTO reviewDTO : reviewDTOS) {
                reviews.add(transformTo(reviewDTO));
            }
        }
        return reviews;
    }


}
