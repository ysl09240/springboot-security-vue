package com.sykean.hmhc.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileUtil {

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + File.separator + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static void deleteAllFileByDir(File file) {
        //删除目录及下所有的文件和文件夹
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteAllFileByDir(f);
            }
        }
        file.delete();
    }

    public static String renameToUUID(String fileName) {
        return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /***
     * @Description 文件下载
     * @Param [path, fileName, resp]
     **/
    public static void downLoadFile(String path, String fileName, HttpServletResponse resp) throws Exception {

        File file = new File(path + fileName);

        //设置文件下载响应格式
        resp.reset();
        resp.setHeader("content-type", "application/octet-stream");
        resp.setHeader("Content-Disposition",
                "attachment;filename=\"" + new String(fileName.getBytes("gbk"), "iso-8859-1") + "\"");
        resp.addHeader("Content-Length", "" + file.length());
        resp.setContentType("application/octet-stream;charset=UTF-8");

        //文件输出到默认目录
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = resp.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /***
     * @Description 检查文件存在
     * @Param [path, fileName]
     **/
    public static boolean checkFileExist(String path, String fileName) {
        File file = new File(path + fileName);
        return file.exists();
    }
    /**
     * zip解压
     * @param fileZip zip源文件
     * @param destDirPath    解压后的目标路径
     * @throws RuntimeException 解压失败会抛出运行时异常
     * @return 解压后的文件夹
     */
    public static void unZip(MultipartFile fileZip, String destDirPath) throws RuntimeException, IOException {
        //复制到指定服务去目录
        String filename = destDirPath
                + fileZip.getOriginalFilename().substring(0, fileZip.getOriginalFilename().indexOf(".zip"));
        File filez = new File(filename + ".zip");
        //MultipartFile->File
        try (InputStream inputStream = fileZip.getInputStream()) {
            FileUtils.copyInputStreamToFile(inputStream, filez);
        }
        //开始解压
        try (ZipFile zipFile = new ZipFile(filez, Charset.forName("gbk"))) {
            //防止中文目录，乱码
            for (Enumeration entries = zipFile.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                //指定解压后的文件夹+当前zip文件的名称
                String outPath = (destDirPath + zipEntryName).replace("/", File.separator);
                //判断路径是否存在,不存在则创建文件路径
                File file = new File(outPath.substring(0, outPath.lastIndexOf(File.separator)));
                if (!file.exists()) {
                    file.mkdirs();
                }
                //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if (new File(outPath).isDirectory()) {
                    continue;
                }
                //保存文件路径信息（可利用md5.zip名称的唯一性，来判断是否已经解压）
                System.err.println("当前zip解压之后的路径为：" + outPath);
                try (InputStream in = zipFile.getInputStream(entry); OutputStream out = new FileOutputStream(outPath)) {
                    byte[] buf1 = new byte[2048];
                    int len;
                    while ((len = in.read(buf1)) > 0) {
                        out.write(buf1, 0, len);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MultipartFile file2MulFile(File file) {
        MultipartFile multipartFile = null;
        OutputStream os = null;
        InputStream input = null;

        try {
            FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()), false,
                    file.getName(), (int) file.length(), file.getParentFile());
            input = new FileInputStream(file);
            os = fileItem.getOutputStream();
            IOUtils.copy(input, os);
            multipartFile = new CommonsMultipartFile(fileItem);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return multipartFile;
    }

}
