# Customer feedback Hybris store
 **Hybris version 1905.11**
 
 Requirement:
 
 
 1. Set up new hybris project
  - [feedbackstore](bin/custom/feedbackstore)
 2. Define new item type CustomerFeedbackReview with few properties (your choice)
  - [feedbackstorecore-items.xml](bin/custom/feedbackstore/feedbackstorecore/resources/feedbackstorecore-items.xml)
 3. Relate CustomerFeedbackReview to Customer item (one customer may have multiple feedbacks)
  - [feedbackstorecore-items.xml](bin/custom/feedbackstore/feedbackstorecore/resources/feedbackstorecore-items.xml)
 4. New extension to host the new code
  - [feedbackstore](bin/custom/feedbackstore)
 5. OCC endpoint to return CustomerFeedbackReview for a given customer
  - [CustomerFeedbackReviewController.java](bin/custom/feedbackstore/feedbackstorecommercewebservices/web/src/com/nabazhenov/v2/controller/CustomerFeedbackReviewController.java)
  - [DefaultCustomerFeedbackReviewFacade.java](bin/custom/feedbackstore/feedbackstorefacades/src/com/nabazhenov/feedbackstore/facades/feedback/impl/DefaultCustomerFeedbackReviewFacade.java)
  - [CustomerFeedbackReviewPopulator.java](bin/custom/feedbackstore/feedbackstorefacades/src/com/nabazhenov/feedbackstore/facades/feedback/populators/CustomerFeedbackReviewPopulator.java)
  - [DefaultCustomerFeedbackReviewService.java](bin/custom/feedbackstore/feedbackstorecore/src/com/nabazhenov/feedbackstore/core/feedback/service/impl/DefaultCustomerFeedbackReviewService.java)
  - [DefaultCustomerFeedbackReviewDao.java](bin/custom/feedbackstore/feedbackstorecore/src/com/nabazhenov/feedbackstore/core/feedback/dao/impl/DefaultCustomerFeedbackReviewDao.java)
  
  
 Do the following backoffice customization:
 
 6. include all defined properties in CustomerFeedbackReview in advanced search
  - [feedbackstorebackoffice-backoffice-config.xml](bin/custom/feedbackstore/feedbackstorebackoffice/resources/feedbackstorebackoffice-backoffice-config.xml)
 7. place all defined properties in CustomerFeedbackReview in additional tab in editor area
   - [feedbackstorebackoffice-backoffice-config.xml](bin/custom/feedbackstore/feedbackstorebackoffice/resources/feedbackstorebackoffice-backoffice-config.xml)
 8. in listviewactions remove/disable possibility to delete CustomerFeedbackReview
   - [feedbackstorebackoffice-backoffice-config.xml](bin/custom/feedbackstore/feedbackstorebackoffice/resources/feedbackstorebackoffice-backoffice-config.xml)
   
   
   Tests:
   - [CustomerFeedbackReviewTest.groovy](bin/custom/feedbackstore/feedbackstorecommercewebservicestest/testsrc/com/nabazhenov/test/groovy/webservicetests/v2/spock/feedback/CustomerFeedbackReviewTest.groovy)
   - [DefaultCustomerFeedbackReviewServiceTest.java](bin/custom/feedbackstore/feedbackstorecore/testsrc/com/nabazhenov/feedbackstore/core/feedback/service/impl/DefaultCustomerFeedbackReviewServiceTest.java)
   - [DefaultCustomerFeedbackReviewFacadeTest.java](bin/custom/feedbackstore/feedbackstorefacades/testsrc/com/nabazhenov/feedbackstore/facades/feedback/impl/DefaultCustomerFeedbackReviewFacadeTest.java)
   - [CustomerFeedbackReviewPopulatorTest.java](bin/custom/feedbackstore/feedbackstorefacades/testsrc/com/nabazhenov/feedbackstore/facades/feedback/populators/CustomerFeedbackReviewPopulatorTest.java)
