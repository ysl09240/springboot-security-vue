package com.sykean.hmhc.config.fastdfs.core;

import com.sykean.hmhc.config.fastdfs.pool.ConnectionPoolFactory;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.StorageClient1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class FastDFSTemplate implements FastDFSOperations {

    private ConnectionPoolFactory connectionPoolFactory;

    public FastDFSTemplate(ConnectionPoolFactory connectionPoolFactory) {
        this.connectionPoolFactory = connectionPoolFactory;
    }

    @Override
    public String uploadFile(byte[] data, String ext, Map<String, String> metaList) {
        NameValuePair[] valuePairs = getNameValuePairs(metaList);
        StorageClient1 client = null;
        try {
            client = connectionPoolFactory.getClient();
            return client.upload_file1(data, ext, valuePairs);
        } catch (Exception e) {
            throw new RuntimeException("error msg:" + e);
        } finally {
            connectionPoolFactory.releaseConnection(client);
        }
    }

    @Override
    public String uploadFile(File file) {
        if (file == null) {
            return "";
        }
        byte[] data = null;
        try {
            try (final FileInputStream inputStream = new FileInputStream(file)) {
                data = IOUtils.toByteArray(inputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException("error msg:" + e);
        }
        return uploadFile(data, FilenameUtils.getExtension(file.getName()), null);
    }

    @Override
    public String uploadFile(byte[] data, String ext) {
        return uploadFile(data, ext, null);
    }

    @Override
    public String uploadFile(InputStream in, String ext) {
        byte[] data = null;
        try {
            data = IOUtils.toByteArray(in);
        } catch (IOException e) {
            throw new RuntimeException("error msg:" + e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return uploadFile(data, ext, null);
    }

    @Override
    public int deleteFile(String fileId) {
        StorageClient1 client = null;
        try {
            client = connectionPoolFactory.getClient();
            return client.delete_file1(fileId);
        } catch (Exception e) {
            throw new RuntimeException("");
        } finally {
            connectionPoolFactory.releaseConnection(client);
        }
    }

    @Override
    public int downloadFile(String fileId, String localFilePath) {
        StorageClient1 client = null;
        try {
            client = connectionPoolFactory.getClient();
            return client.download_file1(fileId, localFilePath);
        } catch (Exception e) {
            throw new RuntimeException("");
        } finally {
            connectionPoolFactory.releaseConnection(client);
        }
    }

    @Override
    public String uploadFile(String groupName, byte[] data, String ext, Map<String, String> metaList) {
        NameValuePair[] valuePairs = getNameValuePairs(metaList);
        StorageClient1 client = null;
        try {
            client = connectionPoolFactory.getClient();
            return client.upload_file1(groupName, data, ext, valuePairs);
        } catch (Exception e) {
            throw new RuntimeException("error msg:" + e);
        } finally {
            connectionPoolFactory.releaseConnection(client);
        }
    }

    /**
     * 根据文件元数据获取 NameValuePair[]
     *
     * @param metaList 文件元数据
     * @return
     */
    private NameValuePair[] getNameValuePairs(Map<String, String> metaList) {
        NameValuePair[] valuePairs = null;
        if (metaList != null && !metaList.isEmpty()) {
            valuePairs = new NameValuePair[metaList.size()];
            int index = 0;
            for (Map.Entry<String, String> entry : metaList.entrySet()) {
                valuePairs[index] = new NameValuePair(entry.getKey(), entry.getValue());
                index++;
            }
        }
        return valuePairs;
    }
}
