/*
 * @author vishalbutolia
 * @Project reviewService
 * @Date 12/10/22
 * */
package com.signify.reviewservice.controller;

import com.signify.reviewservice.entity.Review;
import com.signify.reviewservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("reviews")
    public int saveReviews(@RequestBody List<Review> reviews) {
        reviewService.saveReviews(reviews);
        return 200;
    }

    @GetMapping("review")
    public List<Review> getReview(@RequestParam(name = "rating", required = false) Integer rating,
                                  @RequestParam(name = "store", required = false) String storeType,
                                  @RequestParam(name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return reviewService.getReviews(rating, storeType, date);
    }


}
