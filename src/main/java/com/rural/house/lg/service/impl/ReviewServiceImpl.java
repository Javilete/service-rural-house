package com.rural.house.lg.service.impl;

import com.rural.house.lg.db.ReviewDao;
import com.rural.house.lg.model.interfaces.Review;
import com.rural.house.lg.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ReviewServiceImpl implements ReviewService {

    private static Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private ReviewDao dao;

    public ReviewServiceImpl(ReviewDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Review> getReviews() {
        return dao.getReviewList();
    }

    @Override
    public void addReview(final Review review) {
        dao.saveReview(review);
    }
}
