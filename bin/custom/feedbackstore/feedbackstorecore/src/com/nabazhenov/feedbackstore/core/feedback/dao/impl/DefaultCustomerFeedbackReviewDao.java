package com.nabazhenov.feedbackstore.core.feedback.dao.impl;

import com.nabazhenov.feedbackstore.core.feedback.dao.CustomerFeedbackReviewDao;
import com.nabazhenov.feedbackstore.core.model.CustomerFeedbackReviewModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 *  Default implementation for {@link CustomerFeedbackReviewDao}
 */
@Component("customerFeedbackReviewDao")
public class DefaultCustomerFeedbackReviewDao extends DefaultGenericDao<CustomerFeedbackReviewModel> implements CustomerFeedbackReviewDao {

    public DefaultCustomerFeedbackReviewDao() {
        super(CustomerFeedbackReviewModel._TYPECODE);
    }

    @Override
    public List<CustomerFeedbackReviewModel> getCustomerFeedbackReviewsForCustomer(CustomerModel customerModel) {
        return find(Collections.singletonMap(CustomerFeedbackReviewModel.CUSTOMER, customerModel));
    }
}
