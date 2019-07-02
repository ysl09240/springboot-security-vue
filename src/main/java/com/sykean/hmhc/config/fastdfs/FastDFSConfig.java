package com.sykean.hmhc.config.fastdfs;

import com.sykean.hmhc.config.fastdfs.core.FastDFSTemplate;
import com.sykean.hmhc.config.fastdfs.pool.ConnectionPoolFactory;
import com.sykean.hmhc.config.fastdfs.properties.FastDFSProperties;
import com.sykean.hmhc.config.fastdfs.properties.PoolConfigProperties;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.TrackerGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

@Configuration
@EnableConfigurationProperties({FastDFSProperties.class, PoolConfigProperties.class})
public class FastDFSConfig {

    @Autowired
    private FastDFSProperties fastDFSProperties;

    @Autowired
    private PoolConfigProperties poolConfigProperties;

    @PostConstruct
    private void initFastDFS() throws RuntimeException {
        if (fastDFSProperties.getConnectTimeout() != null) {
            ClientGlobal.setG_connect_timeout(fastDFSProperties.getConnectTimeout());
        }
        if (fastDFSProperties.getNetworkTimeout() != null) {
            // 网络超时的时限，单位为毫秒
            ClientGlobal.setG_network_timeout(fastDFSProperties.getNetworkTimeout());
        }
        if (fastDFSProperties.getAntiStealToken() != null) {
            ClientGlobal.setG_anti_steal_token(fastDFSProperties.getAntiStealToken());

        }
        if (fastDFSProperties.getCharset() != null) {
            // 字符集
            ClientGlobal.setG_charset(fastDFSProperties.getCharset());
        }
        if (fastDFSProperties.getSecretKey() != null) {
            ClientGlobal.setG_secret_key(fastDFSProperties.getSecretKey());
        }
        if (fastDFSProperties.getTrackerHttpPort() != null) {
            // HTTP访问服务的端口号
            ClientGlobal.setG_tracker_http_port(fastDFSProperties.getTrackerHttpPort());
        }
        if (StringUtils.isBlank(fastDFSProperties.getTrackerServer())) {
            throw new RuntimeException("trackerServer must be not null");
        }
        // Tracker服务器列表
        String[] trackerServers = fastDFSProperties.getTrackerServer().split(",");
        InetSocketAddress[] trackerServerAddresses = new InetSocketAddress[trackerServers.length];

        for (int i = 0; i < trackerServers.length; ++i) {
            String[] parts = trackerServers[i].split("\\:", 2);
            if (parts.length != 2) {
                throw new RuntimeException(
                        "the value of item \"tracker_server\" is invalid, the correct format is host:port");
            }
            trackerServerAddresses[i] = new InetSocketAddress(parts[0].trim(), Integer.parseInt(parts[1].trim()));
        }
        ClientGlobal.setG_tracker_group(new TrackerGroup(trackerServerAddresses));
    }

    @Bean
    public FastDFSTemplate fastDFSTemplate(ConnectionPoolFactory connectionPoolFactory) {
        return new FastDFSTemplate(connectionPoolFactory);
    }

    @Bean
    public ConnectionPoolFactory connectionPoolFactory() {
        return new ConnectionPoolFactory(poolConfigProperties);
    }
}
