package cn.crabapples.common.utils.file;

import cn.crabapples.common.base.ApplicationException;
import cn.crabapples.system.sysFile.UPLOAD_TYPE;
import cn.crabapples.system.sysFile.entity.FileInfo;
import cn.hutool.core.lang.Snowflake;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.file.Files;

@Slf4j
public class FileUtils {

    private final String UPLOAD_PATH;
    private final String VIRTUAL_PATH;

    /**
     * @param uploadPath 文件保存路径
     */
    public FileUtils(String uploadPath, String virtualPath) {
        this.UPLOAD_PATH = uploadPath;
        this.VIRTUAL_PATH = virtualPath;
    }

    private String genRandomFileName(String fileName) {
        String[] split = fileName.split("\\.");
        String suffix = split.length > 0 ? "." + split[split.length - 1] : "";
        return new Snowflake(1, 1).nextIdStr() + suffix;
    }

    /**
     * 保存文件,返回文件路径
     *
     * @param fileName    上传的文件名
     * @param in          传入的文件流
     * @param contentType 文件类型
     * @return 返回上传的文件信息
     */
    public FileInfo saveFile(String fileName, InputStream in, String contentType) {
        log.debug("保存文件,路径[{}]", UPLOAD_PATH);
        String randomName = genRandomFileName(fileName);
        log.debug("保存文件,文件名[{}]", randomName);
        File fold = new File(UPLOAD_PATH);
        try {
            if (!(fold.exists() || fold.mkdirs())) {
                throw new ApplicationException("文件保存失败: 目录异常");
            }
            log.debug("生成全文件名[{}],文件后缀为:[{}]", randomName, fileName);
            File file = new File(UPLOAD_PATH + "/" + randomName.toLowerCase());
            long fileSize = writeFile(in, file);
            String uploadPath = file.getPath();
            log.debug("文件保存完成,路径[{}]", uploadPath);
            log.debug("文件保存完成,虚拟路径[{}]", randomName);
            return createFileInfo(uploadPath, randomName, fileName, fileSize, contentType);
        } catch (IOException e) {
            log.error("保存文件时出现错误[]", e);
            return new FileInfo();
        }
    }

    /**
     * 生成文件信息
     *
     * @param uploadPath  文件真实保存路径
     * @param virtualPath 文件虚拟访问路径
     * @param fileName    文件名
     * @param fileSize    文件大小
     * @param contentType 文件类型
     * @return 生成的文件信息
     */
    private FileInfo createFileInfo(String uploadPath, String virtualPath, String fileName, long fileSize, String contentType) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setOldName(fileName);
        fileInfo.setVirtualPath(virtualPath);
        fileInfo.setUploadPath(uploadPath);
        fileInfo.setFileSize(fileSize);
        fileInfo.setContentType(contentType);
        fileInfo.setSaveType(UPLOAD_TYPE.LOCAL.type);
        return fileInfo;
    }

    /**
     * NIO写入文件
     *
     * @param is   读取的文件流
     * @param file 需要保存到的文件
     * @return 文件大小
     * @throws IOException 文件IO异常
     */
    private static long writeFileV2(InputStream is, File file) throws IOException {
        log.debug("开始写入文件");
        BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(file.toPath()));
        if (!file.exists()) {
            if (!file.createNewFile()) {
                log.error("文件写入失败,文件创建失败");
                throw new ApplicationException("文件写入失败");
            }
        }
        BufferedInputStream bis = new BufferedInputStream(is);
        byte[] data = new byte[1024];
        long size = 0;
        for (int i = bis.read(data); i != -1; i = bis.read(data)) {
            bos.write(data, 0, i);
            size += i;
        }
        bos.flush();
        bos.close();
        log.debug("文件写入完成");
        return size;
    }

    /**
     * 写入文件
     *
     * @param is   读取的文件流
     * @param file 需要保存到的文件
     * @return 文件大小
     * @throws IOException 文件IO异常
     */
    private static long writeFile(InputStream is, File file) throws IOException {
        log.debug("开始写入文件");
        BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(file.toPath()));
        if (!file.exists()) {
            if (!file.createNewFile()) {
                log.error("文件写入失败,文件创建失败");
                throw new ApplicationException("文件写入失败");
            }
        }
        BufferedInputStream bis = new BufferedInputStream(is);
        byte[] data = new byte[1024];
        long size = 0;
        for (int i = bis.read(data); i != -1; i = bis.read(data)) {
            bos.write(data, 0, i);
            size += i;
        }
        bos.flush();
        bos.close();
        log.debug("文件写入完成");
        return size;
    }

    /**
     * 根据地址获取名字
     *
     * @param path 文件地址
     * @return 文件名
     */
    public static String getNameByPath(String path) {
        String fileName = "/file" + path.split("file")[1];
        System.out.println("文件名为: " + fileName);
        return fileName;
    }
}
