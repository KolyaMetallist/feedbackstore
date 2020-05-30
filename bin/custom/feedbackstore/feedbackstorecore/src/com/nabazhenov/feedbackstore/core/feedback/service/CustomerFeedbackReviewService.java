package com.nabazhenov.feedbackstore.core.feedback.service;

import com.nabazhenov.feedbackstore.core.model.CustomerFeedbackReviewModel;
import de.hybris.platform.core.model.user.CustomerModel;

import java.util.List;

/**
 *  Service to provide customer feedback reviews for a customer
 */
public interface CustomerFeedbackReviewService {

    /**
     * Returns the list of customer feedback review for a given customer
     *
     * @param customerModel - CustomerModel object
     * @return the list of customer feedback reviews
     */
    List<CustomerFeedbackReviewModel> getCustomerFeedbackReviewsForCustomer(CustomerModel customerModel);
}
