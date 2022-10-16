/*
 * @author vishalbutolia
 * @Project reviewService
 * @Date 16/10/22
 * */

package com.signify.reviewservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class ReviewDTO {
    private String id;
    private String review;
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
