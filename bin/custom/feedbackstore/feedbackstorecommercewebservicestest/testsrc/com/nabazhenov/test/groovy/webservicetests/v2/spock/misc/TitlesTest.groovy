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
/**
 *
 */
package com.nabazhenov.test.groovy.webservicetests.v2.spock.misc

import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.ContentType.URLENC
import static groovyx.net.http.ContentType.XML
import static org.apache.http.HttpStatus.SC_OK

import de.hybris.bootstrap.annotations.ManualTest
import com.nabazhenov.test.groovy.webservicetests.v2.spock.AbstractSpockFlowTest

import spock.lang.Unroll
import groovyx.net.http.HttpResponseDecorator

@ManualTest
@Unroll
/**
 *
 *
 */
class TitlesTest extends AbstractSpockFlowTest {

	def "Client retrieves available titles: #format"() {

		when:
		HttpResponseDecorator response = restClient.get(path: getBasePathWithSite() + '/titles', contentType: format)

		then:
		with(response) {
			if(isNotEmpty(data)&&isNotEmpty(data.errors))println(data)
			status == SC_OK
			isNotEmpty(data.titles)
			data.titles.size() > 0
		}

		where:
		format << [JSON, XML]
	}
}
