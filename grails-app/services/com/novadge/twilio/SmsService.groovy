package com.novadge.twilio
//import org.apache.http.impl.client.CloseableHttpClient
//import org.apache.http.impl.client.HttpClients
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair
import org.apache.http.client.methods.*
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.HttpHost;
import org.apache.http.client.*
import org.apache.http.impl.client.*
import org.apache.http.*
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.auth.AuthScope

import grails.transaction.Transactional

@Transactional
class SmsService {
def grailsApplication

    /**
     *  Send message with twilio rest api
     *  @params
     *  Map props: A map of parameters
     *  props.to: recipient number
     *  props.from: sender number
     *  props.body: message body
     *  props.mediaUrl: url to media attachement ( for MMS ) 
     *  @returns :CloseableHttpResponse
    **/
    def send(Map props) {
        send(props.to,props.from,props.body,props.mediaUrl)
    }
        
      
    /**
     *  Send message with twilio rest api
     *  @params
     *  to: recipient number
     *  from: sender number
     *  body: message body
     *  mediaUrl: url to media attachement ( for MMS ) 
     *  @returns :CloseableHttpResponse
    **/
    def send(String to, String from,String body,String mediaUrl = "") {
        String twilioHost = grailsApplication.config.twilio.host
        String apiID = grailsApplication.config.twilio.apiID
        String apiPass = grailsApplication.config.twilio.apiPass
        String url = grailsApplication.config.twilio.smsUrl
        
        send(twilioHost,apiID,apiPass,url,to,from,body,mediaUrl)
 
    }
    
    
    /**
     *  Send message with twilio rest api
     *  @params
     *  twilioHost: host address for twilio
     *  apiID : Twilio API ID
     *  apiPass : Twilio API password
     *  to: recipient number
     *  from: sender number
     *  body: message body
     *  mediaUrl: url to media attachement ( for MMS ) 
     *  @returns :CloseableHttpResponse 
    **/
    def send(String twilioHost, String apiID, String apiPass,String url,String to,String from,String body,String mediaUrl = "" ) {
         
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(apiID, apiPass);
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

        HttpPost httpPost = new HttpPost(twilioHost+url);
        List <NameValuePair> params = new ArrayList <NameValuePair>();
        params.add(new BasicNameValuePair("To", to)); // Recipients phone number.
        params.add(new BasicNameValuePair("From", from)); // Your phone number ( Twilio ).
        params.add(new BasicNameValuePair("Body", body));
        if(mediaUrl){
           params.add(new BasicNameValuePair("MediaUrl", mediaUrl)); 
        }        
        
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        CloseableHttpResponse response2 = client.execute(httpPost);
        return response2
    }
    
    
}
