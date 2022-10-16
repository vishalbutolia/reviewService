/*
 * @author vishalbutolia
 * @Project reviewService
 * @Date 12/10/22
 * */
package com.signify.reviewservice.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "reviews")
public class Review {
    @Id
    private String id;
    private String reviewString;
    private String author;
    private String reviewSource;
    private Integer rating;
    private String title;
    private String productName;
    private Instant reviewDate;
}
