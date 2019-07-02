package com.sykean.hmhc.config.fastdfs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "fastdfs")
public class FastDFSProperties {

    //连接tracker服务器超时时长，默认5000ms
    private Integer connectTimeout = 5000;

    //socket连接超时时长，默认30000ms
    private Integer networkTimeout = 30000;

    //文件内容编码，默认UTF-8
    private String charset = "UTF-8";

    private Integer trackerHttpPort = 80;

    private Boolean antiStealToken = false;

    private String secretKey = "FastDFS1234567890";

    //tracker服务器IP和端口，多个逗号隔开
    private String trackerServer = "localhost:22122";

    //nginx 代理端口
    private String filePrefix = "http://localhost:80/";
}
