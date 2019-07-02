package com.sykean.hmhc.config.fastdfs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "fastdfs.pool")
public class PoolConfigProperties {
    public Integer maxIdle = 5;
    public Integer minIdle = 2;
    public Integer maxTotal = 10;
}