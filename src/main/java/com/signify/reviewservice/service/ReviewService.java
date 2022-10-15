/*
 * @author vishalbutolia
 * @Project reviewService
 * @Date 12/10/22
 * */
package com.signify.reviewservice.service;

import com.signify.reviewservice.entity.Review;
import com.signify.reviewservice.repository.ReviewRepository;
import com.signify.reviewservice.repository.ReviewRepositoryImpl;
import com.signify.reviewservice.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository repository;

    @Autowired
    private ReviewRepositoryImpl reviewRepository;

    public List<Review> getReviews(Integer rating, String storeType, Date startDate) {
        Date endDate = null;
        if (startDate != null) {
            startDate = DateTimeUtil.resetADayTime(startDate);
            endDate = DateTimeUtil.incrementADateByOneDay(startDate);
        }
        return reviewRepository.getReviewByFilter(rating, storeType, startDate, endDate);
    }

    public int saveReviews(List<Review> reviews) {
        repository.saveAll(reviews);
        return 1;
    }
}
