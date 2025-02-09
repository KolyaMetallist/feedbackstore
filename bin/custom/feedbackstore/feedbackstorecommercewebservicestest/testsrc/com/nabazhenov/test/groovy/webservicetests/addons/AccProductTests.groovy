/*
 * [y] hybris Platform
 *
 * Copyright (c) 2018 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.nabazhenov.test.groovy.webservicetests.addons
import de.hybris.bootstrap.annotations.ManualTest
import de.hybris.platform.util.Config
import com.nabazhenov.test.groovy.webservicetests.markers.AvoidCollectingOutputFromTest
import com.nabazhenov.test.groovy.webservicetests.markers.CollectOutputFromTest
import com.nabazhenov.test.groovy.webservicetests.v1.BaseWSTest

import org.junit.Before
import org.junit.Test
import org.junit.experimental.categories.Category

@Category(CollectOutputFromTest.class)
@ManualTest
class AccProductTests extends BaseWSTest {

	final firstName = "Sven"
	final lastName = "Haiges"
	final titleCode = "dr"
	final public static password = "test"

	@Before
	public void ignoreIf(){
		org.junit.Assume.assumeTrue(Config.getBoolean("feedbackstorecommercewebservicestest.enableAccTest", false)
				&& Config.getBoolean("feedbackstorecommercewebservicestest.enableV1", false))
	}

	@Test
	@Category(AvoidCollectingOutputFromTest.class)
	void testSearchProductsFacetJSON() {
		def con = testUtil.getSecureConnection("/products?query=camera&sort=topRated", 'GET', 'JSON', HttpURLConnection.HTTP_OK)
		def response = testUtil.verifiedJSONSlurper(con, false, false)

		// Verify facets
		assert response.facets.size() == 6

		def facet = response.facets[0]
		assert facet.name == 'Stores'
		assert facet.multiSelect
		assert facet.visible

		facet = response.facets[1]
		assert !facet.multiSelect
		assert facet.category
		assert facet.visible
		assert facet.priority == 6000
		assert facet.name == 'Category'
		assert facet.values.size() == 8
		assert facet.topValues.size() == 5
	}

	@Test
	void testSearchProductsFacetLocationJSON() {
		def uid = registerUserJSON()
		def access_token = testUtil.getAccessToken(uid, password);
		def postBody = "location=munich"

		def con = testUtil.getSecureConnection("/customers/current/location", 'PUT', 'JSON', HttpURLConnection.HTTP_OK, postBody, null, access_token)
		def cookie = con.getHeaderField('Set-Cookie')

		assert cookie: 'No cookie present, cannot keep session'

		def cookieNoPath = cookie.split(';')[0]

		con = testUtil.getSecureConnection("/products?query=camera&pageSize=5", 'GET', 'JSON', HttpURLConnection.HTTP_OK, null, cookieNoPath, access_token)
		def response = testUtil.verifiedJSONSlurper(con)

		// Verify facets
		assert response.facets.size() == 6
		assert response.facets[0].values[0].query ==  'camera:relevance:availableInStores:WS-Sapporo Ana Hotel Sapporo'
		assert response.facets[0].values[0].count == 20

		postBody = "location=tokio"

		con = testUtil.getSecureConnection("/customers/current/location", 'PUT', 'JSON', HttpURLConnection.HTTP_OK, postBody, cookieNoPath, access_token)

		con = testUtil.getSecureConnection("/products?query=camera&pageSize=5", 'GET', 'JSON', HttpURLConnection.HTTP_OK, null, cookieNoPath, access_token)
		response = testUtil.verifiedJSONSlurper(con)

		// Verify facets
		assert response.facets.size() == 6
		assert response.facets[0].values[0].query ==  'camera:relevance:availableInStores:WS-Ichikawa'
		assert response.facets[0].values[0].count == 17
	}

	def registerUserJSON() {
		def client_credentials_token = testUtil.getClientCredentialsToken()
		def randomUID = System.currentTimeMillis()
		def body = "login=${randomUID}@sven.de&password=${password}&firstName=${firstName}&lastName=${lastName}&titleCode=${titleCode}"
		def con = testUtil.getSecureConnection("/customers", 'POST', 'JSON', HttpURLConnection.HTTP_CREATED, body, null, client_credentials_token)
		return "${randomUID}@sven.de"
	}
}