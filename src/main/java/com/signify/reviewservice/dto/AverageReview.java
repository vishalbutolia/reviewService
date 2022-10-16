/*
 * @author vishalbutolia
 * @Project reviewService
 * @Date 16/10/22
 * */

package com.signify.reviewservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AverageReview {
    @JsonProperty(value = "review_source")
    private String reviewSource;
    private Double averageRating;
}
