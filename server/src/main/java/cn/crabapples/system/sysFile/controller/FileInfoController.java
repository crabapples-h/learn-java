package cn.crabapples.system.sysFile.controller;

import cn.crabapples.common.base.BaseController;
import cn.crabapples.common.base.ResponseDTO;
import cn.crabapples.system.sysFile.entity.FileInfo;
import cn.crabapples.system.sysFile.service.FileInfoService;
import io.minio.errors.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 文件接口
 */
@RestController
@Slf4j
@Tag(name = "文件上传接口")
@RequestMapping("/api")
public class FileInfoController extends BaseController {

    private final FileInfoService fileInfoService;

    public FileInfoController(FileInfoService fileInfoService) {
        this.fileInfoService = fileInfoService;
    }

    @PostMapping("/uploadFile")
    @Operation(summary = "上传文件(对象)", description = "上传文件(返回对象)接口")
    public ResponseDTO<FileInfo> uploadFile(HttpServletRequest request) {
        log.info("收到请求->上传文件(返回对象)");
        FileInfo entity = fileInfoService.uploadFile(request);
        log.info("返回结果->上传文件(返回对象)结束:[{}]", entity);
        return new ResponseDTO<>(entity);
    }

    @PostMapping("/uploadFileV2")
    @Operation(summary = "上传文件(返回路径)", description = "上传文件(返回路径)接口")
    public ResponseDTO<String> uploadFileV2(HttpServletRequest request) {
        log.info("收到请求->上传文件(返回路径)");
        FileInfo entity = fileInfoService.uploadFile(request);
        log.info("返回结果->上传文件(返回路径)结束:[{}]", entity);
        return new ResponseDTO<>(entity.getVirtualPath());
    }

    @PostMapping("/uploadFileV3")
    @Operation(summary = "上传文件(返回路径)", description = "上传文件(返回路径)接口")
    public ResponseDTO<String> uploadFileV3(HttpServletRequest request) {
        log.info("收到请求->上传文件到对象存储(返回路径)");
        String url = fileInfoService.uploadFile2Oss(request);
        log.info("返回结果->上传文件到对象存储(返回路径)结束:[{}]", url);
        return new ResponseDTO<>(url);
    }
}
