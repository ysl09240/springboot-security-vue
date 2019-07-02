package com.sykean.hmhc.fastdfs;

import com.sykean.hmhc.BaseTest;
import com.sykean.hmhc.config.fastdfs.core.FastDFSTemplate;
import com.sykean.hmhc.config.fastdfs.properties.FastDFSProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

@Slf4j
public class FastDFSTemplateTest extends BaseTest {

    @Autowired
    private FastDFSTemplate fastDFSTemplate;

    @Autowired
    private FastDFSProperties fastDFSProperties;

    @Test
    public void upload() {
        String fileId = fastDFSTemplate.uploadFile(new File("F:\\项目文档\\腈纶读卡器驱动.zip"));
        log.info("fileId:{}", fastDFSProperties.getFilePrefix() + fileId);
    }
}