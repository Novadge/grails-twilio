package com.novadge.twilio

import groovy.json.JsonSlurper
import org.apache.http.HttpEntity
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.client.CredentialsProvider
import org.apache.http.client.HttpClient
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.message.BasicNameValuePair

class SmsService {

    static transactional = false

    def grailsApplication

    TwilioService twilioService
    
    /**
     * Send message with Twilio REST API.
     * @param props: A map of parameters:
     * <ul>
     * <li>twilioHost: twilio host url</li>
     * <li>apiId: twilio app api Id</li>
     * <li>apiPass: twilio api password</li>
     * <li>url: request url</li>
     * <li>to: recipient number</li>
     * <li>from: sender number ( from Twilio )</li>
     * <li>body: message body</li>
     * <li>mediaUrl: mms media url ( for MMS )</li>
     * </ul>
     * @returns Map: the response
     */
    Map send(Map props) {
        if(props.apiId && props.apiPass){
           send(props.twilioHost, props.apiID, props.apiPass,props.url,props.to,props.from,props.body,props.mediaUrl) 
        }
        else{
           send(props?.to, props?.from, props?.body, props?.mediaUrl) 
        }
        
    }

    /**
     * Send message with Twilio REST API.
     * @param to: recipient number
     * @param from: sender number ( from twilio )
     * @param body: message body
     * @param mediaUrl: url to media attachement ( for MMS )
     * @returns Map: the response
     */
    Map send(String to, String from, String body, String mediaUrl = "") {
        def conf = grailsApplication.config.twilio
        String twilioHost = conf.host
        String apiID = conf.apiID
        String apiPass = conf.apiPass
        String url = conf.smsUrl

        send(twilioHost, apiID, apiPass, url, to, from, body, mediaUrl)
    }

    /**
     * Send message with Twilio REST API.
     * @param twilioHost: host address for twilio
     * @param apiID : Twilio API ID
     * @param apiPass : Twilio API password
     * @param to: recipient number
     * @param from: sender number ( from twilio )
     * @param body: message body
     * @param mediaUrl: url to media attachement ( for MMS )
     * @returns :Map
     */
    Map send(String twilioHost, String apiID, String apiPass,String url,String to,String from,String body,String mediaUrl = "" ) {
        Map twilioProps = [:]        
        twilioProps.apiID = apiID
        twilioProps.apiPass = apiPass
        twilioProps.url = twilioHost + url
                
        Map reqParams = ["To":to,"From":from,"Body":body,"MediaUrl":mediaUrl]
        CloseableHttpResponse  result = twilioService.post(twilioProps,reqParams)
        HttpEntity entity = result.getEntity() // get result
        String responseBody = EntityUtils.toString(entity); // extract response body
        def jsonSlurper = new JsonSlurper() // for parsing response
        def responseMap = jsonSlurper.parseText(responseBody); // parse into json object
        result.close()
        
        return responseMap as Map
         

//        CredentialsProvider provider = new BasicCredentialsProvider()
//        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(apiID, apiPass)
//        provider.setCredentials(AuthScope.ANY, credentials)
//        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build()
//
//        HttpPost httpPost = new HttpPost(twilioHost + url)
//        List<BasicNameValuePair> params = [
//            new BasicNameValuePair("To", to), // Recipients phone number.
//            new BasicNameValuePair("From", from), // Your phone number ( Twilio ).
//            new BasicNameValuePair("Body", body)]
//        if (mediaUrl) { // if media url is provided.. then include it :)
//            params << new BasicNameValuePair("MediaUrl", mediaUrl)
//        }
//        
//        httpPost.entity = new UrlEncodedFormEntity(params)
//        return client.execute(httpPost)
    }
    
}
