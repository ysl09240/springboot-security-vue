package com.sykean.hmhc.config.fastdfs.pool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

/**
 * StorageClient工厂类
 */
public class StorageClientFactory extends BasePooledObjectFactory<StorageClient1> {

    /**
     * 创建StorageClient1对象
     *
     * @return
     * @throws Exception
     */
    @Override
    public StorageClient1 create() throws Exception {
        TrackerClient trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
        TrackerServer trackerServer = trackerClient.getConnection();
        return new StorageClient1(trackerServer, trackerClient.getStoreStorage(trackerServer));
    }

    /**
     * 包装实例
     *
     * @param storageClient
     * @return
     */
    @Override
    public PooledObject<StorageClient1> wrap(StorageClient1 storageClient) {
        return new DefaultPooledObject<StorageClient1>(storageClient);
    }
}
