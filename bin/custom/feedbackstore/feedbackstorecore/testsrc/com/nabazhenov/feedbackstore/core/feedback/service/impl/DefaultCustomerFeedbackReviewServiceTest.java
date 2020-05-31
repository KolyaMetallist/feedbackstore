package com.nabazhenov.feedbackstore.core.feedback.service.impl;

import com.nabazhenov.feedbackstore.core.feedback.dao.CustomerFeedbackReviewDao;
import com.nabazhenov.feedbackstore.core.model.CustomerFeedbackReviewModel;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.core.model.user.CustomerModel;
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
 *  Unit test for {@link DefaultCustomerFeedbackReviewService}
 */
@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class DefaultCustomerFeedbackReviewServiceTest {

    @InjectMocks
    private DefaultCustomerFeedbackReviewService defaultCustomerFeedbackReviewService;
    @Mock
    private CustomerFeedbackReviewDao customerFeedbackReviewDao;
    @Mock
    private CustomerModel customerModel;
    @Mock
    private CustomerFeedbackReviewModel customerFeedbackReviewModel;


    @Before
    public void setUp() {
        when(customerFeedbackReviewDao.getCustomerFeedbackReviewsForCustomer(customerModel))
                .thenReturn(Collections.singletonList(customerFeedbackReviewModel));
    }

    @Test
    public void testGetCustomerFeedbackReviewsForCustomer() {
        // given
        List<CustomerFeedbackReviewModel> result =  defaultCustomerFeedbackReviewService.getCustomerFeedbackReviewsForCustomer(customerModel);

        // then
        assertThat(result).containsOnly(customerFeedbackReviewModel);
    }
}
