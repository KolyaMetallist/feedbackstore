package com.nabazhenov.v2.controller;

import com.nabazhenov.dto.feedback.CustomerFeedbackReviewListWsDTO;
import com.nabazhenov.feedbackstore.facades.feedback.CustomerFeedbackReviewFacade;
import com.nabazhenov.feedbackstore.facades.feedback.data.CustomerFeedbackReviewDataList;
import de.hybris.platform.webservicescommons.cache.CacheControl;
import de.hybris.platform.webservicescommons.cache.CacheControlDirective;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;
import de.hybris.platform.webservicescommons.swagger.ApiFieldsParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
@RequestMapping(value = "/{baseSiteId}/feedbacks")
@CacheControl(directive = CacheControlDirective.PRIVATE)
@Api(tags = "Customer Feedback Reviews")
public class CustomerFeedbackReviewController extends BaseCommerceController {

    @Resource
    private CustomerFeedbackReviewFacade customerFeedbackReviewFacade;

    @Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
    @RequestMapping(value = "/customer/{userId:.+}", method = RequestMethod.GET)
    @ApiOperation(nickname = "getCustomerFeedbackReviews", value = "Get all feedback reviews of a customer.", notes = "Get all feedback reviews of a customer.")
    @ApiBaseSiteIdParam
    @ResponseBody
    public CustomerFeedbackReviewListWsDTO getCustomerFeedbackReviews(
            @ApiParam(value = "User identifier.", required = true) @PathVariable final String userId,
            @ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields) {
        final CustomerFeedbackReviewDataList customerFeedbackReviewDataList = new CustomerFeedbackReviewDataList();
        customerFeedbackReviewDataList.setCustomerFeedbackReviews(customerFeedbackReviewFacade.getCustomerFeedbackReviewsForCustomer(userId));
        return getDataMapper().map(customerFeedbackReviewDataList, CustomerFeedbackReviewListWsDTO.class, fields);
    }
}
