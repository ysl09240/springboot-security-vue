package com.sykean.hmhc.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

public class IOUtils extends org.apache.commons.io.IOUtils {

    /**
     * 下载文件
     *
     * @param response
     * @param data
     * @param fileName
     * @throws IOException
     */
    public static void downloadFile(HttpServletRequest request, HttpServletResponse response, byte[] data, String fileName) throws IOException {
        String userAgent = request.getHeader("USER-AGENT").toLowerCase();
        if (userAgent.contains("firefox")) {
            fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
        } else {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        }
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
    }
}
