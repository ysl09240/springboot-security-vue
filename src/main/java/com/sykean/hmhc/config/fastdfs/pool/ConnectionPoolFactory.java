package com.sykean.hmhc.config.fastdfs.pool;

import com.sykean.hmhc.config.fastdfs.properties.PoolConfigProperties;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.csource.fastdfs.StorageClient1;

import javax.annotation.PostConstruct;

/**
 * FastDFS 连接池工厂
 */
public class ConnectionPoolFactory {

    private PoolConfigProperties poolConfigProperties;
    private GenericObjectPool<StorageClient1> pool;

    public ConnectionPoolFactory(PoolConfigProperties poolConfigProperties) {
        this.poolConfigProperties = poolConfigProperties;
    }

    @PostConstruct
    public void init() {
        //连接池配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        if (poolConfigProperties.getMaxTotal() != null) {
            poolConfig.setMaxTotal(poolConfigProperties.getMaxTotal());
        }
        if (poolConfigProperties.getMaxIdle() != null) {
            poolConfig.setMaxIdle(poolConfigProperties.getMaxIdle());
        }
        if (poolConfigProperties.getMinIdle() != null) {
            poolConfig.setMinIdle(poolConfigProperties.getMinIdle());
        }
        pool = new GenericObjectPool<>(new StorageClientFactory(), poolConfig);
    }

    /**
     * 获取连接池连接
     *
     * @return
     * @throws Exception
     */
    public StorageClient1 getClient() throws Exception {
        return pool.borrowObject();
    }

    /**
     * 释放连接
     *
     * @param client
     */
    public void releaseConnection(StorageClient1 client) {
        try {
            pool.returnObject(client);
        } catch (Exception ignored) {
        }
    }
}
