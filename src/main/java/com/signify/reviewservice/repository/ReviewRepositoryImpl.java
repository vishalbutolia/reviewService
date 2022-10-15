/*
 * @author vishalbutolia
 * @Project reviewService
 * @Date 12/10/22
 * */

package com.signify.reviewservice.repository;

import com.signify.reviewservice.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private static final String RATING = "rating";
    private static final String REVIEW_SOURCE = "reviewSource";
    private static final String REVIEW_DATE = "reviewDate";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Review> getReviewByFilter(Integer rating, String storeType, Date startDate, Date endDate) {
        Query query = new Query();
        if (rating != null) query.addCriteria(Criteria.where(RATING).is(rating));
        if (storeType != null) query.addCriteria(Criteria.where(REVIEW_SOURCE).is(storeType));
        if (startDate != null) {
            query.addCriteria(Criteria.where(REVIEW_DATE).gt(startDate).lt(endDate));
        }
        return mongoTemplate.find(query, Review.class);
    }
}
