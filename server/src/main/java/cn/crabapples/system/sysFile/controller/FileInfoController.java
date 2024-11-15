package cn.crabapples.system.sysFile.controller;

import cn.crabapples.common.base.BaseController;
import cn.crabapples.common.base.ResponseDTO;
import cn.crabapples.system.sysFile.entity.FileInfo;
import cn.crabapples.system.sysFile.service.FileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件接口
 */
@RestController
@Slf4j
@RequestMapping("/api")
public class FileInfoController extends BaseController {

    private final FileInfoService fileInfoService;

    public FileInfoController(FileInfoService fileInfoService) {
        this.fileInfoService = fileInfoService;
    }

    @PostMapping("/uploadFile")
    public ResponseDTO<FileInfo> uploadFile(HttpServletRequest request) {
        log.info("收到请求->上传文件");
        FileInfo entity = fileInfoService.uploadFile(request);
        log.info("返回结果->上传文件结束:[{}]", entity);
        return new ResponseDTO<>(entity);
    }

    @PostMapping("/uploadFileV2")
    public ResponseDTO<String> uploadFileV2(HttpServletRequest request) {
        log.info("收到请求->上传文件");
        FileInfo entity = fileInfoService.uploadFile(request);
        log.info("返回结果->上传文件结束:[{}]", entity);
        return new ResponseDTO<>(entity.getVirtualPath());
    }
}
