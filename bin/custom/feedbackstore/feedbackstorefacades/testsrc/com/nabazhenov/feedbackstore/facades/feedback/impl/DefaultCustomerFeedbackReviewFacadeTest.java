package com.nabazhenov.feedbackstore.facades.feedback.impl;

import com.nabazhenov.feedbackstore.core.feedback.service.CustomerFeedbackReviewService;
import com.nabazhenov.feedbackstore.core.model.CustomerFeedbackReviewModel;
import com.nabazhenov.feedbackstore.facades.feedback.data.CustomerFeedbackReviewData;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.commerceservices.user.UserMatchingService;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 *  Unit test for {@link DefaultCustomerFeedbackReviewFacade}
 */
@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class DefaultCustomerFeedbackReviewFacadeTest {

    private static final String USER_ID = "testUser";

    @InjectMocks
    private DefaultCustomerFeedbackReviewFacade defaultCustomerFeedbackReviewFacade;
    @Mock
    private UserMatchingService userMatchingService;
    @Mock
    private CustomerFeedbackReviewService customerFeedbackReviewService;
    @Mock
    private Converter<CustomerFeedbackReviewModel, CustomerFeedbackReviewData> customerFeedbackReviewConverter;
    @Mock
    private CustomerModel customerModel;
    @Mock
    private CustomerFeedbackReviewModel customerFeedbackReviewModel;
    @Mock
    private CustomerFeedbackReviewData customerFeedbackReviewData;

    @Before
    public void setUp() {
        when(userMatchingService.getUserByProperty(USER_ID, CustomerModel.class)).thenReturn(customerModel);
        when(customerFeedbackReviewService.getCustomerFeedbackReviewsForCustomer(customerModel))
                .thenReturn(Collections.singletonList(customerFeedbackReviewModel));
        when(customerFeedbackReviewConverter.convert(customerFeedbackReviewModel)).thenReturn(customerFeedbackReviewData);
    }

    @Test
    public void testGetCustomerFeedbackReviewsForCustomer() {
        //given
        List<CustomerFeedbackReviewData> result = defaultCustomerFeedbackReviewFacade.getCustomerFeedbackReviewsForCustomer(USER_ID);

        // then
        assertThat(result).containsOnly(customerFeedbackReviewData);
    }
}
