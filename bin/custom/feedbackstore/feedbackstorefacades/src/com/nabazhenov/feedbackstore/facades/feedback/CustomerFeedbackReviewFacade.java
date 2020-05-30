package com.nabazhenov.feedbackstore.facades.feedback;

import com.nabazhenov.feedbackstore.facades.feedback.data.CustomerFeedbackReviewData;

import java.util.List;


/**
 *  Facade to provide customer feedback reviews for a customer
 */
public interface CustomerFeedbackReviewFacade {

    /**
     * Returns the list of customer feedback review for a given customer
     *
     * @param customerId - customer id
     * @return the list of customer feedback reviews
     */
    List<CustomerFeedbackReviewData> getCustomerFeedbackReviewsForCustomer(String customerId);

}
