package com.rural.house.lg.db;


import com.rural.house.lg.model.interfaces.Review;

import java.util.List;

public interface ReviewDao {

    List<Review> getReviewList();

    void saveReview(Review review);
}
