package com.nabazhenov.feedbackstore.facades.feedback.populators;

import com.nabazhenov.feedbackstore.core.model.CustomerFeedbackReviewModel;
import com.nabazhenov.feedbackstore.facades.feedback.data.CustomerFeedbackReviewData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * Populates {@link CustomerFeedbackReviewData} with essential data fields from {@link CustomerFeedbackReviewModel}
 */
@Component("customerFeedbackReviewPopulator")
public class CustomerFeedbackReviewPopulator implements Populator<CustomerFeedbackReviewModel, CustomerFeedbackReviewData> {

    @Resource
    private Converter<UserModel, CustomerData> customerConverter;

    @Override
    public void populate(final CustomerFeedbackReviewModel source, final CustomerFeedbackReviewData target)
            throws ConversionException {
        target.setId(source.getCode());
        target.setAlias(source.getAlias());
        target.setHeadline(source.getHeadline());
        target.setComment(source.getComment());
        target.setRating(source.getRating());
        target.setDate(source.getCreationtime());
        target.setCustomer(customerConverter.convert(source.getCustomer()));
    }
}
