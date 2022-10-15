/*
 * @author vishalbutolia
 * @Project reviewService
 * @Date 12/10/22
 * */
package com.signify.reviewservice.repository;

import com.signify.reviewservice.entity.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String>, ReviewRepositoryCustom {
}
