package cn.crabapples.upload.controller;

import cn.crabapples.common.base.BaseController;
import cn.crabapples.common.base.ResponseDTO;
import cn.crabapples.common.annotation.JwtIgnore;
import cn.crabapples.upload.strategy.UPLOAD_TYPE_ENUM;
import cn.crabapples.upload.service.FileInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件接口
 */
@RestController
@Slf4j
@Tag(name = "文件上传接口")
@RequestMapping("/api/file")
public class FileInfoController extends BaseController {

    private final FileInfoService fileInfoService;

    public FileInfoController(FileInfoService fileInfoService) {
        this.fileInfoService = fileInfoService;
    }

    @PostMapping("/upload/{type}")
    @Operation(summary = "上传文件", description = "上传文件接口")
    public ResponseDTO<String> upload(HttpServletRequest request, @PathVariable UPLOAD_TYPE_ENUM type) {
        log.info("收到请求->上传文件[{}]", type);
        String url = fileInfoService.upload(request, type);
        log.info("返回结果->上传文件[{}]结束:[{}]", type, url);
        return new ResponseDTO<>(url);
    }

    @GetMapping("/download/{type}/{bucket}/{fileName}")
    @Operation(summary = "获取文件", description = "获取文件接口")
    @JwtIgnore
    public void download(HttpServletResponse response, @PathVariable UPLOAD_TYPE_ENUM type, @PathVariable String bucket, @PathVariable String fileName) {
        log.info("收到请求->获取文件");
        fileInfoService.download(bucket, fileName, response, type);
        log.info("返回结果->获取文件结束:[{}]", fileName);
    }

    @PostMapping("/share/{type}/{bucket}/{fileName}")
    @Operation(summary = "分享文件", description = "分享文件接口")
    public ResponseDTO<String> share(@PathVariable UPLOAD_TYPE_ENUM type, @PathVariable String bucket, @PathVariable String fileName) {
        log.info("收到请求->分享文件");
        String url = fileInfoService.share(bucket, fileName, type);
        log.info("返回结果->分享文件结束:[{}],分享链接:[{}]", fileName, url);
        return new ResponseDTO<>(url);
    }
}
