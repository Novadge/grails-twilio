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

    def send(String to, String from,String body,String mediaUrl = "") {
        String twilioHost = grailsApplication.config.twilio.host
        String apiID = grailsApplication.config.twilio.apiID
        String apiPass = grailsApplication.config.twilio.apiPass
        String url = grailsApplication.config.twilio.smsUrl
        
        send(twilioHost,apiID,apiPass,url,to,from,body,mediaUrl)
 
    }
    
    def send(String twilioHost, String apiID, String apiPass,String url,String to,String from,String body,String mediaUrl = "" ) {
         
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(apiID, apiPass);
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

        HttpPost httpPost = new HttpPost(twilioHost+url);
        List <NameValuePair> params = new ArrayList <NameValuePair>();
        params.add(new BasicNameValuePair("To", to)); // Replace with a valid phone number for your account.
        params.add(new BasicNameValuePair("From", from)); // Replace with a valid phone number for your account.
        params.add(new BasicNameValuePair("Body", body));
        if(mediaUrl){
           params.add(new BasicNameValuePair("MediaUrl", mediaUrl)); 
        }        
        
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        CloseableHttpResponse response2 = client.execute(httpPost);
        return response2
    }
    
    
}
