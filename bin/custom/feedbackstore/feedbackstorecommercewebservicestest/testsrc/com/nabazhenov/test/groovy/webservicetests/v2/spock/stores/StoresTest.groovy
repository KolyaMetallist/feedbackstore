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
package com.nabazhenov.test.groovy.webservicetests.v2.spock.stores


import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.ContentType.URLENC
import static groovyx.net.http.ContentType.XML
import static org.apache.http.HttpStatus.SC_BAD_REQUEST
import static org.apache.http.HttpStatus.SC_CREATED
import static org.apache.http.HttpStatus.SC_OK
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED

import de.hybris.bootstrap.annotations.ManualTest
import de.hybris.platform.commerceservices.storefinder.impl.DefaultStoreFinderService
import de.hybris.platform.core.Registry
import de.hybris.platform.storelocator.GeoWebServiceWrapper
import com.nabazhenov.test.groovy.webservicetests.v2.spock.AbstractSpockFlowTest

import java.text.DecimalFormatSymbols

import org.junit.AfterClass
import org.junit.BeforeClass

import spock.lang.Unroll
import groovyx.net.http.HttpResponseDecorator

/**
 *
 *
 */
@Unroll
@ManualTest
class StoresTest extends AbstractSpockFlowTest {
    private static GeoWebServiceWrapper originalServiceWrapper;

    @BeforeClass
    public static void initGeoServiceWrapper() {
        final DefaultStoreFinderService storeFinderService = Registry.getApplicationContext().getBean("defaultStoreFinderService", DefaultStoreFinderService.class);
        originalServiceWrapper = Registry.getApplicationContext().getBean("geoServiceWrapper", GeoWebServiceWrapper.class);
        GeoWebServiceWrapper webServiceWrapper = Registry.getApplicationContext().getBean("mockedGeoServiceWrapper", GeoWebServiceWrapper.class);

        // set mock
        storeFinderService.setGeoWebServiceWrapper(webServiceWrapper);

    }

    @AfterClass
    public static void restoreGeoServiceWrapper() {
        if (originalServiceWrapper != null) {
            final DefaultStoreFinderService storeFinderService = Registry.getApplicationContext().getBean("defaultStoreFinderService", DefaultStoreFinderService.class);
            storeFinderService.setGeoWebServiceWrapper(originalServiceWrapper);
        }
    }

    def "Get all stores: #format"() {

        when: "a list of stores is requested"
        HttpResponseDecorator response = restClient.get(
                path: getBasePathWithSite() + '/stores',
                query: [
                        'pageSize': 10
                ],
                contentType: format,
                requestContentType: URLENC
        )

        then: "all stores are returned"
        with(response) {
            if (isNotEmpty(data) && isNotEmpty(data.errors)) println(data)
            status == SC_OK
            data.stores.size() == 10
            data.pagination.pageSize == 10
            data.pagination.totalResults == 49
            data.pagination.totalPages == 5
            response.containsHeader(HEADER_TOTAL_COUNT)
            response.getFirstHeader(HEADER_TOTAL_COUNT).getValue() == '49'
        }

        where:
        format << [JSON, XML]
    }

    def "Get number of all stores: #format"() {

        when: "a list of stores is requested"
        HttpResponseDecorator response = restClient.get(
                path: getBasePathWithSite() + '/stores',
                contentType: format,
                requestContentType: URLENC
        )

        then: "he gets proper header"
        with(response) {
            if (isNotEmpty(data) && isNotEmpty(data.errors)) println(data)
            status == SC_OK
            response.containsHeader(HEADER_TOTAL_COUNT)
            response.getFirstHeader(HEADER_TOTAL_COUNT).getValue() == '49'
        }

        where:
        format << [JSON, XML]
    }

    def "Get stores in area with no stores: #format"() {

        when: "a list of stores is requested for area with no stores"
        HttpResponseDecorator response = restClient.get(
                path: getBasePathWithSite() + '/stores',
                query: [
                        'query': 'munich'
                ],
                contentType: format,
                requestContentType: URLENC
        )

        then: "no results are returned, but no error is thrown"
        with(response) {
            if (isNotEmpty(data) && isNotEmpty(data.errors)) println(data)
            status == SC_OK
            data.pagination.totalResults == 0
            response.containsHeader(HEADER_TOTAL_COUNT)
            response.getFirstHeader(HEADER_TOTAL_COUNT).getValue() == '0'
        }

        where:
        format << [JSON, XML]
    }

    def "Get stores in area with some stores with no radius specified: #format"() {

        when: "a list of stores is requested for area with some stores"
        HttpResponseDecorator response = restClient.get(
                path: getBasePathWithSite() + '/stores',
                query: [
                        'query' : 'tokyo',
                        'fields': FIELD_SET_LEVEL_FULL,
                ],
                contentType: format,
                requestContentType: URLENC
        )

        then: "a list of stores is returned"
        with(response) {
            if (isNotEmpty(data) && isNotEmpty(data.errors)) println(data)
            status == SC_OK
            data.pagination.totalResults == 23
            response.containsHeader(HEADER_TOTAL_COUNT)
            response.getFirstHeader(HEADER_TOTAL_COUNT).getValue() == '23'
            isNotEmpty(data.stores)
        }

        where:
        format << [JSON, XML]
    }

    def "Get stores in area with some stores with radius specified: #format"() {

        when: "a list of stores is requested for area with some stores specifying radius to take into account"
        HttpResponseDecorator response = restClient.get(
                path: getBasePathWithSite() + '/stores',
                query: [
                        'query' : 'tokyo',
                        'fields': FIELD_SET_LEVEL_FULL,
                        'radius': 500
                ],
                contentType: format,
                requestContentType: URLENC
        )

        then: "some results are returned, less than with no radius"
        with(response) {
            if (isNotEmpty(data) && isNotEmpty(data.errors)) println(data)
            status == SC_OK
            data.pagination.totalResults == 11
            response.containsHeader(HEADER_TOTAL_COUNT)
            response.getFirstHeader(HEADER_TOTAL_COUNT).getValue() == '11'
        }

        where:
        format << [JSON, XML]
    }

    def "Get stores in area with some stores with large radius specified: #format"() {

        when: "a list of stores is requested for area with with extremely large radius to take into account"
        HttpResponseDecorator response = restClient.get(
                path: getBasePathWithSite() + '/stores',
                query: [
                        'query' : 'tokyo',
                        'fields': FIELD_SET_LEVEL_FULL,
                        'radius': 1000000
                ],
                contentType: format,
                requestContentType: URLENC
        )

        then: "all stores are returned"
        with(response) {
            if (isNotEmpty(data) && isNotEmpty(data.errors)) println(data)
            status == SC_OK
            data.pagination.totalResults == 49
            response.containsHeader(HEADER_TOTAL_COUNT)
            response.getFirstHeader(HEADER_TOTAL_COUNT).getValue() == '49'
        }

        where:
        format << [JSON, XML]
    }

    def "Get specific store by name : #format"() {
        when: "a specific store is requested"
        HttpResponseDecorator response = restClient.get(
                path: getBasePathWithSite() + '/stores/' + "WS-Choshi",
                contentType: format,
                query: [
                        'fields': FIELD_SET_LEVEL_FULL,
                ],
                requestContentType: URLENC
        )

        then: "store details are returned"
        with(response) {
            if (isNotEmpty(data) && isNotEmpty(data.errors)) println(data)
            status == SC_OK
        }
        with(response.data) {
            geoPoint != null
            name == "WS-Choshi"
            address.country.name == "Japan"
            address.country.isocode == "JP"
            address.town == "Choshi"
            address.line1 == "Chiba-ken Choshi-shi"
            isNotEmpty(features)
            /* commented out code makes the test pass with wrapped collections */
            //leaving test failing as a reminder that maps should not be wrapped either
            //			if (format == JSON ){
            def storeFeatures = features.entry.collect {
                it.key.toString()
            }
            storeFeatures.containsAll([
                    'sundayWorkshops',
                    'creche',
                    'buyOnlinePickupInStore'
            ])
            //			}
            //			if (format == XML ){
            //				def storeFeaturesxml = features.entry.collect {
            //					it.key.toString()
            //				}
            //				storeFeaturesxml.containsAll([
            //					'sundayWorkshops',
            //					'creche',
            //					'buyOnlinePickupInStore'
            //				])
            //			}
        }

        where:
        format << [JSON, XML]
    }

    def "Get stores by latitude and longitude: #format"() {
        when: "stores are requested by latitude and longitude"
        HttpResponseDecorator response = restClient.get(
                path: getBasePathWithSite() + '/stores/',
                contentType: format,
                query: [
                        'fields'   : FIELD_SET_LEVEL_FULL,
                        'latitude' : '35.65',
                        'longitude': '139.69',
                        'radius'   : 4500
                ],
                requestContentType: URLENC
        )

        then: "list of matching stores is returned"
        with(response) {
            if (isNotEmpty(data) && isNotEmpty(data.errors)) println(data)
            status == SC_OK
        }
        char decimalSeparator = new DecimalFormatSymbols().getDecimalSeparator();
        with(response.data) {
            stores.size() == 11
            stores[0].formattedDistance == '4' + decimalSeparator + '4 km'
            stores[1].formattedDistance == '4' + decimalSeparator + '4 km'
            pagination.pageSize == 20
            pagination.totalResults == 11
            pagination.totalPages == 1
            response.containsHeader(HEADER_TOTAL_COUNT)
            response.getFirstHeader(HEADER_TOTAL_COUNT).getValue() == '11'
        }

        where:
        format << [JSON, XML]
    }


    def "Paginate through results: #format"() {
        when: "paginated list of stores is requested"
        HttpResponseDecorator response1 = restClient.get(
                path: getBasePathWithSite() + '/stores/',
                contentType: format,
                query: [
                        'query'   : 'tokyo',
                        'pageSize': 10,
                        'radius'  : 1000
                ],
                requestContentType: URLENC
        )
        and: "second page of results is requested"
        HttpResponseDecorator response2 = restClient.get(
                path: getBasePathWithSite() + '/stores/',
                contentType: format,
                query: [
                        'currentPage': 1,
                        'query'      : 'tokyo',
                        'pageSize'   : 10,
                        'radius'     : 1000
                ],
                requestContentType: URLENC
        )
        then: "responses are correctly sent over"
        with(response1) {
            if (isNotEmpty(data) && isNotEmpty(data.errors)) println(data)
            status == SC_OK
            if (isNotEmpty(data) && isNotEmpty(data.errors)) println(data)
            data.stores.size() == 10
            data.pagination.pageSize == 10
            data.pagination.currentPage == 0
            data.pagination.totalResults == 11
            data.pagination.totalPages == 2
            response1.containsHeader(HEADER_TOTAL_COUNT)
            response1.getFirstHeader(HEADER_TOTAL_COUNT).getValue() == '11'
        }
        with(response2) {
            if (isNotEmpty(data) && isNotEmpty(data.errors)) println(data)
            status == SC_OK
            if (isNotEmpty(data) && isNotEmpty(data.errors)) println(data)
            data.stores.size() == 1
            data.pagination.pageSize == 10
            data.pagination.currentPage == 1
            data.pagination.totalResults == 11
            data.pagination.totalPages == 2
            response2.containsHeader(HEADER_TOTAL_COUNT)
            response2.getFirstHeader(HEADER_TOTAL_COUNT).getValue() == '11'
        }


        where:
        format << [JSON, XML]
    }

    def "Get stores specifying fields: #format"() {
        when: "stores are requested with some fields specified"
        HttpResponseDecorator response = restClient.get(
                path: getBasePathWithSite() + '/stores/',
                contentType: format,
                query: [
                        'query' : 'choshi',
                        'fields': 'stores(formattedDistance,openingHours),pagination',
                        'radius': 500
                ],
                requestContentType: URLENC
        )

        then: "store data is returned with specified fields"
        with(response) {
            if (isNotEmpty(data) && isNotEmpty(data.errors)) println(data)
            status == SC_OK
            isNotEmpty(data.stores)
            data.stores.size() == 1
            data.stores[0].formattedDistance == '0 km'
            isNotEmpty(data.stores[0].openingHours)
            data.pagination.pageSize == 20
            data.pagination.totalResults == 1
            data.pagination.totalPages == 1
            response.containsHeader(HEADER_TOTAL_COUNT)
            response.getFirstHeader(HEADER_TOTAL_COUNT).getValue() == '1'
        }

        where:
        format << [JSON, XML]
    }


    def "Get stores by latitude and longitude with accuracy: #format"() {
        when: "stores are requested with some fields specified"
        HttpResponseDecorator response = restClient.get(
                path: getBasePathWithSite() + '/stores/',
                contentType: format,
                query: [
                        'fields'   : FIELD_SET_LEVEL_FULL,
                        'accuracy' : 500,
                        'latitude' : 35.65,
                        'longitude': 139.69,
                        'radius'   : 4000
                ],
                requestContentType: URLENC
        )

        then: "store data is returned with specified fields"
        char decimalSeparator = new DecimalFormatSymbols().getDecimalSeparator();
        with(response) {
            if (isNotEmpty(data) && isNotEmpty(data.errors)) println(data)
            status == SC_OK
            data.stores.size() == 11
            data.stores[0].formattedDistance == '4' + decimalSeparator + '4 km'
            data.stores[1].formattedDistance == '4' + decimalSeparator + '4 km'
            data.pagination.pageSize == 20
            data.pagination.totalResults == 11
            data.pagination.totalPages == 1
            response.containsHeader(HEADER_TOTAL_COUNT)
            response.getFirstHeader(HEADER_TOTAL_COUNT).getValue() == '11'
        }

        where:
        format << [JSON, XML]
    }

    def 'Get stores in Japan: #format'() {
        when: 'stores located in Japan are requested'
        HttpResponseDecorator response = restClient.get(
                path: getBasePathWithSite() + '/stores/country/JP',
                contentType: format,
                requestContentType: URLENC
        )

        then: 'stores in Japan are returned'

        with(response) {
            data.pointOfServices.size() > 0
            data.pointOfServices.each { store ->
                store.address.country.isocode == 'JP'
            }
        }

        where:
        format << [JSON, XML]
    }

    def 'Get stores in region JP-20: #format'() {
        when: 'stores located in JP-20 are requested'
        HttpResponseDecorator response = restClient.get(
                path: getBasePathWithSite() + '/stores/country/JP/region/JP-20',
                contentType: format,
                requestContentType: URLENC
        )

        then: 'stores in Japan are returned'

        with(response) {
            data.pointOfServices.size() > 0
            data.pointOfServices.each { store ->
                store.address.country.isocode == 'JP'
                store.address.region.isocode == 'JP-20'
            }
        }

        where:
        format << [JSON, XML]
    }

    def 'Get stores in a fake region: #format'() {
        when: 'stores in a nonexistent region are requested'
        HttpResponseDecorator response = restClient.get(
                path: getBasePathWithSite() + '/stores/country/JP/region/JP-99',
                contentType: format,
                requestContentType: URLENC
        )

        then: 'stores in a fake Japanese region are returned'

        with(response) {
            data.pointOfServices == null || data.pointOfServices.size() == 0
        }

        where:
        format << [JSON, XML]
    }

    def "Get stores counts: #format"() {
        when: "store counts are requested"
        HttpResponseDecorator response = restClient.get(
                path: getBasePathWithSite() + '/stores/storescounts',
                contentType: format,
                query: [
                        'fields': FIELD_SET_LEVEL_FULL
                ],
                requestContentType: URLENC
        )

        then: "store counts is returned with specified fields"
        with(response) {
            if (isNotEmpty(data) && isNotEmpty(data.errors)) println(data)
            status == SC_OK
            data.countriesAndRegionsStoreCount.size() == 1
            data.countriesAndRegionsStoreCount[0].count == 49
            data.countriesAndRegionsStoreCount[0].isoCode == "JP"
            data.countriesAndRegionsStoreCount[0].name == "Japan"
        }

        where:
        format << [JSON, XML]
    }
}
