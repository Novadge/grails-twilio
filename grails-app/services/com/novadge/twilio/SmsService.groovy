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
import org.apache.http.entity.StringEntity
import org.apache.http.util.EntityUtils
import org.apache.http.HttpEntity
import groovy.json.JsonSlurper

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
        if(props.apiId && props.apiPass && props.twilioHost && props.url) {
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
        Map reqParams =[:]    
        if(mediaUrl){
            reqParams = ["To":to,"From":from,"Body":body,"MediaUrl":mediaUrl]
        }
        else{
           reqParams = ["To":to,"From":from,"Body":body] 
        }
        
        CloseableHttpResponse  result = twilioService.post(twilioProps,reqParams)
        HttpEntity entity = result.getEntity() // get result
        String responseBody = EntityUtils.toString(entity); // extract response body
        def jsonSlurper = new JsonSlurper() // for parsing response
        def responseMap = jsonSlurper.parseText(responseBody); // parse into json object
        result.close()
        
        return responseMap as Map
         
    }

     /**
     * Send message with Twilio REST API.
     *
     * <h3>A usage example:</h3>
     *
     * <p>When the mediaUrl is not included:
     * <pre> {@code
     *  Map result = SmsService.send("twilioHost", "apiID", "apiPass", "url", "to", "from", "body","statusCallback")
     * } </pre>
     *
     * <p>When the mediaUrl is included:
     * <pre> {@code
     *  Map result = SmsService.send("twilioHost", "apiID", "apiPass", "url", "to", "from", "body","statusCallback","mediaUrl)
     * } </pre>
     *
     * @param twilioHost: host address for twilio
     * @param apiID : Twilio API ID
     * @param apiPass : Twilio API password
     * @param to: recipient number
     * @param from: sender number ( from twilio )
     * @param body: message body
     * @param statusCallback: A callback URL that Twilio will POST to each time the message status changes
     * @param mediaUrl: url to media attachement ( for MMS )
     * @returns :Map
     */
    Map send(String twilioHost, String apiID, String apiPass,String url,String to,String from,String body, String statusCallback, String mediaUrl = "" ) {

        Map twilioProps = [:]
        twilioProps.apiID = apiID
        twilioProps.apiPass = apiPass
        twilioProps.url = twilioHost + url
        twilioProps.callbackUrl = statusCallback

        Map reqParams = [:]

        if (mediaUrl) {
            reqParams = ["To":to,"From":from,"Body":body,"MediaUrl":mediaUrl,"StatusCallback": statusCallback]
        } else {
            reqParams = ["To":to,"From":from,"Body":body,"StatusCallback": statusCallback]
        }

        CloseableHttpResponse result = twilioService.post(twilioProps,reqParams)
        HttpEntity entity = result.getEntity() // get result
        String responseBody = EntityUtils.toString(entity); // extract response body
        def jsonSlurper = new JsonSlurper() // for parsing response
        def responseMap = jsonSlurper.parseText(responseBody); // parse into json object
        result.close()

        return responseMap as Map
    }
    
}
