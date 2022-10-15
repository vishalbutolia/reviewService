package com.signify.reviewservice.repository;

import com.signify.reviewservice.entity.Review;

import java.util.Date;
import java.util.List;

public interface ReviewRepositoryCustom {
    List<Review> getReviewByFilter(Integer rating, String storeType, Date startDate, Date endDate);
}
