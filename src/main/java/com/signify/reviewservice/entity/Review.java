/*
 * @author vishalbutolia
 * @Project reviewService
 * @Date 12/10/22
 * */
package com.signify.reviewservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "reviews")
public class Review {
    @Id
    private String id;
    @JsonProperty(value = "review")
    private String reviewString;
    private String author;
    @JsonProperty(value = "review_source")
    private String reviewSource;
    private Integer rating;
    private String title;
    @JsonProperty(value = "product_name")
    private String productName;
    @JsonProperty(value = "reviewed_date")
    private Instant reviewDate;
}
