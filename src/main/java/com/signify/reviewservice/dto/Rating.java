/*
 * @author vishalbutolia
 * @Project reviewService
 * @Date 16/10/22
 * */

package com.signify.reviewservice.dto;

import lombok.Data;

@Data
public class Rating {
    private Integer category;
    private Integer count;
}
