package com.nabazhenov.feedbackstore.facades.feedback.impl;

import com.nabazhenov.feedbackstore.core.feedback.service.CustomerFeedbackReviewService;
import com.nabazhenov.feedbackstore.core.model.CustomerFeedbackReviewModel;
import com.nabazhenov.feedbackstore.facades.feedback.CustomerFeedbackReviewFacade;
import com.nabazhenov.feedbackstore.facades.feedback.data.CustomerFeedbackReviewData;
import de.hybris.platform.commerceservices.user.UserMatchingService;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


/**
 *  Default implementation for {@link CustomerFeedbackReviewFacade}
 */
@Component
public class DefaultCustomerFeedbackReviewFacade implements CustomerFeedbackReviewFacade {

    @Resource
    private UserMatchingService userMatchingService;
    @Resource
    private CustomerFeedbackReviewService customerFeedbackReviewService;
    @Resource
    private Converter<CustomerFeedbackReviewModel, CustomerFeedbackReviewData> customerFeedbackReviewConverter;

    @Override
    public List<CustomerFeedbackReviewData> getCustomerFeedbackReviewsForCustomer(String userId) {
        CustomerModel customerModel = userMatchingService.getUserByProperty(userId, CustomerModel.class);
        List<CustomerFeedbackReviewModel> customerFeedbackReviewModelList = customerFeedbackReviewService.getCustomerFeedbackReviewsForCustomer(customerModel);
        return Converters.convertAll(customerFeedbackReviewModelList, customerFeedbackReviewConverter);
    }
}
