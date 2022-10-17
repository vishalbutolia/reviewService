/*
 * @author vishalbutolia
 * @Project reviewService
 * @Date 17/10/22
 * */

package com.signify.reviewservice.TestUtil;

import com.signify.reviewservice.dto.AverageReview;
import com.signify.reviewservice.dto.ProductRating;
import com.signify.reviewservice.dto.Rating;
import com.signify.reviewservice.dto.ReviewDTO;
import com.signify.reviewservice.entity.Review;
import com.signify.reviewservice.util.StringConstantsUtil;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MockDataUtil {
    public static List<Review> getValidReviewData() {
        Review review1 = new Review();
        review1.setReviewString("Mock review string");
        review1.setReviewSource(StringConstantsUtil.GOOGLE_PLAY_STORE);
        review1.setAuthor("vishal");
        review1.setRating(4);
        review1.setProductName("Amazon Alexa");
        review1.setTitle("Alexa Review");
        LocalDate date = LocalDate.of(2022, 10, 16);
        review1.setReviewDate(date);

        Review review2 = new Review();
        review2.setReviewString("Mock review string");
        review2.setReviewSource(StringConstantsUtil.ITUNES);
        review2.setAuthor("Ronaldo");
        review2.setRating(3);
        review2.setProductName("Amazon Alexa");
        review2.setTitle("Alexa Review");
        review2.setReviewDate(date);
        return Arrays.asList(review1, review2);
    }

    public static List<Review> getInValidReviewData() {
        Review review1 = new Review();
        review1.setReviewString("Mock review string");
        review1.setReviewSource("MyStore");
        review1.setAuthor("vishal");
        review1.setRating(4);
        review1.setProductName("Amazon Alexa");
        review1.setTitle("Alexa Review");
        LocalDate date = LocalDate.of(2022, 10, 16);
        review1.setReviewDate(date);

        Review review2 = new Review();
        review2.setReviewString("Mock review string");
        review2.setReviewSource(StringConstantsUtil.ITUNES);
        review2.setAuthor("Ronaldo");
        review2.setRating(10);
        review2.setProductName("Amazon Alexa");
        review2.setTitle("Alexa Review");
        review2.setReviewDate(date);
        return Arrays.asList(review1, review2);
    }

    public static List<Review> getFilteredList(Integer rating, String storeType) {
        List<Review> list = getValidReviewData();
        if (rating != null) {
            list = list.stream().filter(e -> e.getRating().equals(rating)).collect(Collectors.toList());
        }
        if (storeType != null) {
            list = list.stream().filter(e -> e.getReviewSource().equals(storeType)).collect(Collectors.toList());
        }
        return list;
    }

    public static List<AverageReview> getAverageReviewData() {
        AverageReview itunesReview = new AverageReview();
        itunesReview.setReviewSource(StringConstantsUtil.ITUNES);
        itunesReview.setAverageRating(3.5);
        AverageReview googleReview = new AverageReview();
        googleReview.setReviewSource(StringConstantsUtil.GOOGLE_PLAY_STORE);
        googleReview.setAverageRating(2.5);
        return Arrays.asList(itunesReview, googleReview);
    }

    public static List<ProductRating> getOverallRatingData() {
        Rating fourStarRating = new Rating();
        fourStarRating.setCategory(4);
        fourStarRating.setCount(10);
        Rating threeStarRating = new Rating();
        threeStarRating.setCategory(3);
        threeStarRating.setCount(250);

        ProductRating productRating = new ProductRating();
        productRating.setProductName("Amazon Alexa");
        productRating.setRatingGroup(Arrays.asList(fourStarRating, threeStarRating));
        return Collections.singletonList(productRating);
    }

    public static List<ReviewDTO> getValidReviewDTOData() {
        ReviewDTO review1 = new ReviewDTO();
        review1.setReview("Mock review string");
        review1.setReviewSource(StringConstantsUtil.GOOGLE_PLAY_STORE);
        review1.setAuthor("vishal");
        review1.setRating(4);
        review1.setProductName("Amazon Alexa");
        review1.setTitle("Alexa Review");
        LocalDate date = LocalDate.of(2022, 10, 16);
        review1.setReviewDate(date);

        ReviewDTO review2 = new ReviewDTO();
        review2.setReview("Mock review string");
        review2.setReviewSource(StringConstantsUtil.ITUNES);
        review2.setAuthor("Ronaldo");
        review2.setRating(3);
        review2.setProductName("Amazon Alexa");
        review2.setTitle("Alexa Review");
        review2.setReviewDate(date);
        return Arrays.asList(review1, review2);
    }

    public static List<ReviewDTO> getFilteredDTOList(Integer rating, String storeType) {
        List<ReviewDTO> list = getValidReviewDTOData();
        if (rating != null) {
            list = list.stream().filter(e -> e.getRating().equals(rating)).collect(Collectors.toList());
        }
        if (storeType != null) {
            list = list.stream().filter(e -> e.getReviewSource().equals(storeType)).collect(Collectors.toList());
        }
        return list;
    }

}
