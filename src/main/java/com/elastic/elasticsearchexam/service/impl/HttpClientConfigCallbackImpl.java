package com.elastic.elasticsearchexam.service.impl;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.io.File;

@Service
public class HttpClientConfigCallbackImpl implements RestClientBuilder.HttpClientConfigCallback {
    @Value("${elasticsearch.username}")
    private String username;

    @Value("${elasticsearch.password}")
    private String password;

    @Value("${elasticsearch.trustStoreLocation}")
    private String trustStoreLocation;

    @Value("${elasticsearch.trustStorePassword}")
    private String trustStorePassword;

    @Override
    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
        try {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            UsernamePasswordCredentials usernamePasswordCredentials = new UsernamePasswordCredentials( username,password );
            credentialsProvider.setCredentials(AuthScope.ANY, usernamePasswordCredentials);
            httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);

            File trustStoreLocationFile = new File(trustStoreLocation);
            SSLContextBuilder sslContextBuilder = SSLContexts.custom().loadTrustMaterial(trustStoreLocationFile, trustStorePassword.toCharArray()
            );
            SSLContext sslContext = sslContextBuilder.build();
            httpAsyncClientBuilder.setSSLContext(sslContext);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return httpAsyncClientBuilder;
    }
}
