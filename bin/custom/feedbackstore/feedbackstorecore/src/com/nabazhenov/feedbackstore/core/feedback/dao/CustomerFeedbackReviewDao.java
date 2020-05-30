package com.nabazhenov.feedbackstore.core.feedback.dao;

import com.nabazhenov.feedbackstore.core.model.CustomerFeedbackReviewModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.internal.dao.Dao;

import java.util.List;


/**
 * Dao to retrieve customer feedabck review data for
 * {@link com.nabazhenov.feedbackstore.core.feedback.service.CustomerFeedbackReviewService}.
 */
public interface CustomerFeedbackReviewDao extends Dao {

    List<CustomerFeedbackReviewModel> getCustomerFeedbackReviewsForCustomer(CustomerModel customerModel);
}
