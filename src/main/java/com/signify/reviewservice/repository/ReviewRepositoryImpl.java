/*
 * @author vishalbutolia
 * @Project reviewService
 * @Date 12/10/22
 * */

package com.signify.reviewservice.repository;

import com.mongodb.BasicDBObject;
import com.signify.reviewservice.dto.AverageReview;
import com.signify.reviewservice.dto.ProductRating;
import com.signify.reviewservice.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private static final String RATING = "rating";
    private static final String REVIEW_SOURCE = "reviewSource";
    private static final String REVIEW_DATE = "reviewDate";
    private static final String AVERAGE_RATING = "averageRating";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Review> getReviewByFilter(Integer rating, String storeType,
                                          LocalDate startDate, LocalDate endDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Query query = new Query().with(pageable);
        if (rating != null) query.addCriteria(Criteria.where(RATING).is(rating));
        if (storeType != null) query.addCriteria(Criteria.where(REVIEW_SOURCE).is(storeType));
        if (startDate != null) {
            query.addCriteria(Criteria.where(REVIEW_DATE).gt(startDate).lt(endDate));
        }
        List<Review> filteredReviewList = mongoTemplate.find(query, Review.class);
        return PageableExecutionUtils.getPage(
                filteredReviewList,
                pageable,
                () -> mongoTemplate.count(query, Review.class)).getContent();
    }

    public List<AverageReview> getMonthlyAverageReviews(LocalDate startDate, LocalDate endDate) {
        MatchOperation matchOperation = Aggregation.match(Criteria.where(REVIEW_DATE).gt(startDate).lt(endDate));
        GroupOperation groupOperation = Aggregation.group("$" + REVIEW_SOURCE).avg("$" + RATING).as(AVERAGE_RATING);
        ProjectionOperation projectionOperation = Aggregation.project()
                .andExpression("_id").as(REVIEW_SOURCE)
                .andExpression(AVERAGE_RATING).as(AVERAGE_RATING)
                .and(ArithmeticOperators.Round.roundValueOf(AVERAGE_RATING).place(1)).as(AVERAGE_RATING);
        Aggregation aggregation = Aggregation.newAggregation(matchOperation, groupOperation, projectionOperation);
        return mongoTemplate.aggregate(aggregation, "reviews", AverageReview.class).getMappedResults();

    }

    public List<ProductRating> getProductsOverallRating() {
        GroupOperation groupByProductAndRating = Aggregation.group("$productName", "$rating").count().as("count");
        GroupOperation groupByProduct = Aggregation.group("$_id.productName")
                .push(new BasicDBObject("category", "$_id.rating").append("count", "$count")).as("counts");
        ProjectionOperation projection = Aggregation.project()
                .andExpression("_id").as("productName")
                .andExpression("counts").as("ratingGroup");
        Aggregation aggregation = Aggregation.newAggregation(groupByProductAndRating, groupByProduct, projection);
        return mongoTemplate.aggregate(aggregation, "reviews", ProductRating.class).getMappedResults();
    }
}
