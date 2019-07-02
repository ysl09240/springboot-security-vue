package com.sykean.hmhc.util;


import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class HttpClientUtilTest {

    @Test
    public void send() throws IOException {
        String b = FileUtils.readFileToString(
                new File("src\\test\\java\\com\\sykean\\hmhc\\util\\iris.json"),
                Charset.forName("UTF-8"));
        String str = HttpClientUtil.sendPostUrl("http://192.168.0.166:8089/iris/feature/extract", b);
        System.out.println(str);
    }
}
