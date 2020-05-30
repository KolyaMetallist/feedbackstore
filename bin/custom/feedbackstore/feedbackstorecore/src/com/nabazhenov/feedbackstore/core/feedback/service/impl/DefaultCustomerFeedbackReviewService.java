package com.nabazhenov.feedbackstore.core.feedback.service.impl;

import com.nabazhenov.feedbackstore.core.feedback.dao.CustomerFeedbackReviewDao;
import com.nabazhenov.feedbackstore.core.feedback.service.CustomerFeedbackReviewService;
import com.nabazhenov.feedbackstore.core.model.CustomerFeedbackReviewModel;
import de.hybris.platform.core.model.user.CustomerModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 *  Default implementation for {@link CustomerFeedbackReviewService}
 */
@Component("customerFeedbackReviewService")
public class DefaultCustomerFeedbackReviewService implements CustomerFeedbackReviewService {

    @Resource
    private CustomerFeedbackReviewDao customerFeedbackReviewDao;

    @Override
    public List<CustomerFeedbackReviewModel> getCustomerFeedbackReviewsForCustomer(final CustomerModel customerModel) {
        return customerFeedbackReviewDao.getCustomerFeedbackReviewsForCustomer(customerModel);
    }
}
