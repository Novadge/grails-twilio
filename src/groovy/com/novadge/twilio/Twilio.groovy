package com.novadge.twilio

/**
 * @author Omasiri
 */
//import groovy.transform.CompileStatic
//
//import org.apache.http.auth.AuthScope
//import org.apache.http.auth.UsernamePasswordCredentials
//import org.apache.http.client.CredentialsProvider
//import org.apache.http.client.HttpClient
//import org.apache.http.client.entity.UrlEncodedFormEntity
//import org.apache.http.client.methods.CloseableHttpResponse
//import org.apache.http.client.methods.HttpPost
//import org.apache.http.impl.client.BasicCredentialsProvider
//import org.apache.http.impl.client.HttpClientBuilder
//import org.apache.http.message.BasicNameValuePair
//
//@CompileStatic
//class Twilio {
//
//    static CloseableHttpResponse send(String twilioHost, String apiID, String apiPass, String url, String to,
//                                      String from, String body, String mediaUrl = "") {
//
//        CredentialsProvider provider = new BasicCredentialsProvider()
//        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(apiID, apiPass)
//        provider.setCredentials(AuthScope.ANY, credentials)
//        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build()
//
//        HttpPost httpPost = new HttpPost(twilioHost + url)
//        List<BasicNameValuePair> params = [
//            new BasicNameValuePair("To", to), // Replace with a valid phone number for your account.
//            new BasicNameValuePair("From", from), // Replace with a valid phone number for your account.
//            new BasicNameValuePair("Body", body)]
//        if (mediaUrl) {
//            params << new BasicNameValuePair("MediaUrl", mediaUrl)
//        }
//
//        httpPost.entity = new UrlEncodedFormEntity(params)
//        client.execute httpPost
//    }
//}
