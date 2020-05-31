package com.nabazhenov.feedbackstore.facades.feedback.populators;

import com.nabazhenov.feedbackstore.core.model.CustomerFeedbackReviewModel;
import com.nabazhenov.feedbackstore.facades.feedback.data.CustomerFeedbackReviewData;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *  Unit test for {@link CustomerFeedbackReviewPopulator}
 */
@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class CustomerFeedbackReviewPopulatorTest {

    protected static final String CODE = "code";
    protected static final String ALIAS = "alias";
    protected static final String HEADLINE = "headline";
    protected static final String COMMENT = "comment";
    @InjectMocks
    private CustomerFeedbackReviewPopulator customerFeedbackReviewPopulator;
    @Mock
    private Converter<UserModel, CustomerData> customerConverter;
    @Mock
    private CustomerFeedbackReviewModel source;
    @Mock
    private CustomerFeedbackReviewData target;
    @Mock
    private CustomerModel customerModel;
    @Mock
    private CustomerData customerData;

    private Date dateTime;

    @Before
    public void setUp() {
        dateTime = new Date();
        when(source.getCode()).thenReturn(CODE);
        when(source.getAlias()).thenReturn(ALIAS);
        when(source.getHeadline()).thenReturn(HEADLINE);
        when(source.getComment()).thenReturn(COMMENT);
        when(source.getRating()).thenReturn(5D);
        when(source.getCreationtime()).thenReturn(dateTime);
        when(source.getCustomer()).thenReturn(customerModel);
        when(customerConverter.convert(customerModel)).thenReturn(customerData);
    }

    @Test
    public void testPopulate() {
        // given
        customerFeedbackReviewPopulator.populate(source, target);

        // then
        verify(target).setId(CODE);
        verify(target).setAlias(ALIAS);
        verify(target).setHeadline(HEADLINE);
        verify(target).setComment(COMMENT);
        verify(target).setRating(5D);
        verify(target).setDate(dateTime);
        verify(target).setCustomer(customerData);
    }
}
