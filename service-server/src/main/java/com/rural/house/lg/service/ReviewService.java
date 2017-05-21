package com.rural.house.lg.service;


import com.rural.house.lg.model.interfaces.Review;

import java.util.List;

public interface ReviewService {

    List<Review> getReviews();

    void addReview(Review review);
}
