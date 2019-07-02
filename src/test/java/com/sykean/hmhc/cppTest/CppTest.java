package com.sykean.hmhc.cppTest;

import com.alibaba.fastjson.JSONObject;
import com.sykean.hmhc.BaseTest;
import com.sykean.hmhc.config.fastdfs.core.FastDFSTemplate;
import com.sykean.hmhc.config.fastdfs.properties.FastDFSProperties;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate.HttpClientOption;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class CppTest extends BaseTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void addF() throws IOException {
    	HttpHeaders header = new HttpHeaders();
    	header.setContentType(MediaType.APPLICATION_JSON);
    	 String b = FileUtils.readFileToString(
                 new File("src\\test\\java\\com\\sykean\\hmhc\\util\\iris.json"),
                 Charset.forName("UTF-8"));
         HttpEntity httpEntity = new HttpEntity(b,header);
        ResponseEntity<String> request = restTemplate.postForEntity("http://192.168.0.166:8089/iris/feature/extract",httpEntity,String.class);
        System.err.println("====================" + request.getBody());
    }
    
    
    
}