package com.novadge.twilio

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

    /**
     * Send message with Twilio REST API.
     * @param props: A map of parameters:
     * <ul>
     * <li>to: recipient number</li>
     * <li>from: sender number ( from Twilio )</li>
     * <li>body: message body</li>
     * <li>url to media attachement ( for MMS )</li>
     * </ul>
     * @returns the response
     */
    CloseableHttpResponse send(Map props) {
        send(props?.to, props?.from, props?.body, props?.mediaUrl)
    }

    /**
     * Send message with Twilio REST API.
     * @param to: recipient number
     * @param from: sender number ( from twilio )
     * @param body: message body
     * @param mediaUrl: url to media attachement ( for MMS )
     * @returns the response
     */
    CloseableHttpResponse send(String to, String from, String body, String mediaUrl = "") {
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
     * @returns :CloseableHttpResponse
     */
    CloseableHttpResponse send(String twilioHost, String apiID, String apiPass,String url,String to,String from,String body,String mediaUrl = "" ) {

        CredentialsProvider provider = new BasicCredentialsProvider()
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(apiID, apiPass)
        provider.setCredentials(AuthScope.ANY, credentials)
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build()

        HttpPost httpPost = new HttpPost(twilioHost + url)
        List<BasicNameValuePair> params = [
            new BasicNameValuePair("To", to), // Recipients phone number.
            new BasicNameValuePair("From", from), // Your phone number ( Twilio ).
            new BasicNameValuePair("Body", body)]
        if (mediaUrl) { // if media url is provided.. then include it :)
            params << new BasicNameValuePair("MediaUrl", mediaUrl)
        }

        httpPost.entity = new UrlEncodedFormEntity(params)
        return client.execute(httpPost)
    }
}
