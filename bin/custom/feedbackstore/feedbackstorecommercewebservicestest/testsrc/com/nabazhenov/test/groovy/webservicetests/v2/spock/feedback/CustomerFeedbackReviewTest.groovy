package com.nabazhenov.test.groovy.webservicetests.v2.spock.feedback

import com.nabazhenov.test.groovy.webservicetests.v2.spock.users.AbstractUserTest
import de.hybris.bootstrap.annotations.ManualTest
import spock.lang.Unroll

import static groovyx.net.http.ContentType.*
import static org.apache.http.HttpStatus.SC_OK

/**
 * Test for CustomerFeedbackReview endpoint
 */
@Unroll
@ManualTest
class CustomerFeedbackReviewTest extends AbstractUserTest {

    def "A trusted client request feedback reviews for customer : #format"() {

        given: "a trusted client"
        authorizeTrustedClient(restClient)
        def userId = "democustomer"

        when: "user requests feedback reviews for democustomer"
        def response = restClient.get(
                path: getBasePathWithSite() + "/feedbacks/customer/" + userId,
                contentType: format,
                query: ["fields": FIELD_SET_LEVEL_FULL],
                requestContentType: URLENC)

        then: "he receives requested list"
        with(response) {
            status == SC_OK
            isNotEmpty(data)
            data.customerFeedbackReviews.size() == 2
        }

        where:
        format << [XML, JSON]
    }
}
