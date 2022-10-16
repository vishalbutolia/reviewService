/*
 * @author vishalbutolia
 * @Project reviewService
 * @Date 16/10/22
 * */

package com.signify.reviewservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductRating {
    private String productName;
    private List<Rating> ratingGroup;
}
