package com.sykean.hmhc.config.fastdfs.core;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

/**
 * FastDFS文件操作
 */
public interface FastDFSOperations {

    /**
     * 上传文件
     *
     * @param data     文件的字节数组
     * @param ext      文件后缀，比如 png txt，没有点
     * @param metaList 元数据
     * @return
     */
    String uploadFile(byte[] data, String ext, Map<String, String> metaList);

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    String uploadFile(File file);

    /**
     * 上传文件
     *
     * @param data
     * @param ext  文件后缀，比如 png txt，没有点
     * @return
     */
    String uploadFile(byte[] data, String ext);

    /**
     * 上传文件
     *
     * @param in  文件流
     * @param ext 文件后缀
     * @return
     */
    String uploadFile(InputStream in, String ext);

    /**
     * 删除文件
     *
     * @param fileId 文件id
     * @return
     */
    int deleteFile(String fileId);

    /**
     * 下载文件
     *
     * @param fileId        文件id
     * @param localFilePath 本地文件地址
     * @return
     */
    int downloadFile(String fileId, String localFilePath);

    /**
     * 上传文件
     *
     * @param groupName 组名
     * @param data      文件的字节数组
     * @param fileName  文件名称
     * @param metaList  元数据
     * @return
     */
    String uploadFile(String groupName, byte[] data, String fileName, Map<String, String> metaList);
}
