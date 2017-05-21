package com.rural.house.lg.resources;


import com.rural.house.lg.model.interfaces.Review;
import com.rural.house.lg.resource.ReviewResource;
import com.rural.house.lg.service.ReviewService;
import io.dropwizard.jersey.errors.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Path("/api/review")
public class ReviewResourceImpl extends ReviewResource{

    private static Logger LOGGER = LoggerFactory.getLogger(ReviewResourceImpl.class);

    private final ReviewService reviewService;

    public ReviewResourceImpl(ReviewService service){
        this.reviewService = service;
    }

    @Override
    public Response addReviewImpl(Review review) {

        try{
            reviewService.addReview(review);
        } catch(Exception e) {
            LOGGER.debug("Review service - addReview - ERROR: when submiting a review");
            throw new WebApplicationException(Response.status(INTERNAL_SERVER_ERROR)
                    .entity(new ErrorMessage("Error when submiting the review"))
                    .build());
        }


        return Response.ok().build();
    }

    @Override
    public Response getReviewListImpl() {

        List<Review> reviews;

        try {
            reviews = reviewService.getReviews();
        }catch(Exception e) {
            LOGGER.debug("Review service - get list of reviews - ERROR: when retrieving the reviews. ");
            throw new WebApplicationException(Response.status(INTERNAL_SERVER_ERROR)
                    .entity(new ErrorMessage("Error when retrieving the reviews."))
                    .build());
        }

        return Response.ok().entity(reviews).build();
    }

}
