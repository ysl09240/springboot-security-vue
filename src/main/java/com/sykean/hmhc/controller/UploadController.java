package com.sykean.hmhc.controller;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.config.fastdfs.core.FastDFSTemplate;
import com.sykean.hmhc.config.fastdfs.properties.FastDFSProperties;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "上传接口", description = "上传接口")
@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private FastDFSProperties fastDFSProperties;

    @Autowired
    private FastDFSTemplate fastDFSTemplate;

    @ApiOperation(value = "上传文件")
    @PostMapping("file")
    public RestResponse<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileId = fastDFSTemplate.uploadFile(file.getInputStream(), FilenameUtils.getExtension(file.getOriginalFilename()));
        return RestResponse.success(fastDFSProperties.getFilePrefix() + fileId);
    }
}