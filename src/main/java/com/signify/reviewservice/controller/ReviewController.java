/*
 * @author vishalbutolia
 * @Project reviewService
 * @Date 12/10/22
 * */
package com.signify.reviewservice.controller;

import com.signify.reviewservice.dto.AverageReview;
import com.signify.reviewservice.dto.ProductRating;
import com.signify.reviewservice.dto.ReviewDTO;
import com.signify.reviewservice.entity.Review;
import com.signify.reviewservice.exception.UnsupportedReviewSource;
import com.signify.reviewservice.service.ReviewService;
import com.signify.reviewservice.transformer.ReviewTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("review-service/api/v1/")
public class ReviewController {
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewService reviewService;

    @PostMapping("review")
    public ResponseEntity<List<ReviewDTO>> saveReviews(@RequestBody @Valid List<ReviewDTO> reviewDTOS) throws UnsupportedReviewSource {
        logger.info("save reviews request");
        List<Review> reviews = ReviewTransformer.transformTo(reviewDTOS);
        List<ReviewDTO> savedReviews = reviewService.saveReviews(reviews);
        return ResponseEntity.ok(savedReviews);
    }

    @GetMapping("review")
    public List<ReviewDTO> getReview(@RequestParam(name = "rating", required = false) Integer rating,
                                     @RequestParam(name = "store", required = false) String storeType,
                                     @RequestParam(name = "date", required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                     @RequestParam(required = false, defaultValue = "0") int page,
                                     @RequestParam(required = false, defaultValue = "5") int size) throws UnsupportedReviewSource {
        logger.info("getting reviews with params rating: {}," +
                " storeType:{}, date:{}, page:{}, size:{}", rating, storeType, date, page, size);
        return reviewService.getReviews(rating, storeType, date, page, size).getContent();
    }

    @GetMapping("review/average")
    public List<AverageReview> getMonthlyAverageReview(
            @RequestParam(required = false) int year,
            @RequestParam(required = false) int month) {
        logger.info("getting average review for year: {} month: {}", year, month);
        return reviewService.getMonthlyAverageReview(year, month);
    }

    @GetMapping("review/category")
    public List<ProductRating> getProductsRating() {
        logger.info("getting product wise overall rating");
        return reviewService.getProductsOverallRating();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UnsupportedReviewSource.class})
    public Map<String, Object> handleValidationExceptions(UnsupportedReviewSource ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        errors.put("message", ex.getMessage());
        return errors;
    }

}
