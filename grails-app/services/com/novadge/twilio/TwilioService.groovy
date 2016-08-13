package com.novadge.twilio


import org.apache.http.auth.AuthScope



import grails.transaction.Transactional
import groovyx.net.http.HTTPBuilder
import org.apache.http.HttpEntity
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.client.CredentialsProvider
import org.apache.http.client.HttpClient
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.util.EntityUtils
import org.apache.http.message.BasicNameValuePair
import org.apache.http.params.HttpParams
import org.apache.http.impl.client.HttpClientBuilder

import groovy.json.JsonSlurper;


@Transactional
class TwilioService {

    /**
     * Lookup information about a phone number.
     * @param twilioProps: A map of twilio parameters:
     * <ul>
     * <li>apiId: twilio app api Id</li>
     * <li>apiPass: twilio api password</li>
     * <li>url: request url for number lookup. eg "http://lookups.twilio.com/v1/PhoneNumbers/${phoneNumber}?Type=carrier&Type=caller-name"</li>
     * </ul>
     * @returns Map : the response
     */
    Map lookup(Map twilioProps){
        CloseableHttpResponse result = get(twilioProps)
        HttpEntity entity = result.getEntity()
        String responseBody = EntityUtils.toString(entity);
        JsonSlurper slurper = new JsonSlurper()
        return slurper.parseText(responseBody) as Map
    }
    
   
    
//    Buying phone numbers: POST /2010-04-01/Accounts/{TestAccountSid}/IncomingPhoneNumbers
//Sending SMS messages: POST /2010-04-01/Accounts/{TestAccountSid}/SMS/Messages
//Making calls: POST /2010-04-01/Accounts/{TestAccountSid}/Calls
//    
    /**
     * Send post request to Twilio REST API.
     * @param twilioProps: A map of twilio parameters:
     * <ul>
     * <li>apiId: twilio app api Id</li>
     * <li>apiPass: twilio api password</li>
     * <li>url: request url</li>
     * </ul>
     * @param reqParams: A map of twilio parameters:
     * <ul>
     * <li>request parameters</li>
     * </ul>
     * @returns the response
     */
    CloseableHttpResponse post(Map twilioProps,Map reqParams) {

        CredentialsProvider provider = new BasicCredentialsProvider()
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(twilioProps.apiID, twilioProps.apiPass)
        provider.setCredentials(AuthScope.ANY, credentials)
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build()

        HttpPost httpPost = new HttpPost(twilioProps.url)
        List<BasicNameValuePair> params = []
        // for each item in reqParams
        reqParams.each{key, val ->
                // add to params list
                
                BasicNameValuePair item = new BasicNameValuePair("${key}",val);
                params.add(item)
            }
        

        httpPost.entity = new UrlEncodedFormEntity(params)
        return client.execute(httpPost)
    }
    
    /**
     * Send get request to Twilio REST API.
     * @param twilioProps: A map of twilio parameters:
     * <ul>
     * <li>apiId: twilio app api Id</li>
     * <li>apiPass: twilio api password</li>
     * <li>url: request url</li>
     * </ul>
     * @param reqParams: A map of twilio parameters:
     * <ul>
     * <li>request parameters</li>
     * </ul>
     * @returns the response
     */
    CloseableHttpResponse get(Map twilioProps) {

        CredentialsProvider provider = new BasicCredentialsProvider()
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(twilioProps.apiID, twilioProps.apiPass)
        provider.setCredentials(AuthScope.ANY, credentials)
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build()

        HttpGet httpGet = new HttpGet(twilioProps.url)


        return client.execute(httpGet)
        
      
    }

    
}
