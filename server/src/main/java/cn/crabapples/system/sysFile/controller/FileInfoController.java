package cn.crabapples.system.sysFile.controller;

import cn.crabapples.common.base.BaseController;
import cn.crabapples.common.base.ResponseDTO;
import cn.crabapples.system.sysFile.UPLOAD_TYPE;
import cn.crabapples.system.sysFile.service.FileInfoService;
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

//    @PostMapping("/upload")
//    @Operation(summary = "上传文件(对象)", description = "上传文件(返回对象)接口")
//    public ResponseDTO<FileInfo> upload(HttpServletRequest request) {
//        log.info("收到请求->上传文件(返回对象)");
//        FileInfo entity = fileInfoService.uploadFile(request, UploadFunc.LOCAL);
//        log.info("返回结果->上传文件(返回对象)结束:[{}]", entity);
//        return new ResponseDTO<>(entity);
//    }

    @PostMapping("/uploadV2")
    @Operation(summary = "上传文件(本地)", description = "上传文件(本地)接口")
    public ResponseDTO<String> uploadV2(HttpServletRequest request) {
        log.info("收到请求->上传文件(本地)");
        String url = fileInfoService.upload(request, UPLOAD_TYPE.LOCAL);
        log.info("返回结果->上传文件(本地)结束:[{}]", url);
        return new ResponseDTO<>(url);
    }

    @PostMapping("/uploadV3")
    @Operation(summary = "上传文件(Minio)", description = "上传文件(Minio)接口")
    public ResponseDTO<String> uploadV3(HttpServletRequest request) {
        log.info("收到请求->上传文件(Minio)");
        String url = fileInfoService.upload(request, UPLOAD_TYPE.MINIO);
        log.info("返回结果->上传文件(Minio)结束:[{}]", url);
        return new ResponseDTO<>(url);
    }

    @GetMapping("/download/{type}/{fileName}")
    @Operation(summary = "获取文件", description = "获取文件接口")
    public void download(HttpServletResponse response, @PathVariable UPLOAD_TYPE type, @PathVariable String fileName) {
        log.info("收到请求->获取文件");
        fileInfoService.download(fileName, response, type);
        log.info("返回结果->获取文件结束:[{}]", fileName);
    }

    @PostMapping("/share/{type}/{fileName}")
    @Operation(summary = "分享文件", description = "分享文件接口")
    public ResponseDTO<String> share(@PathVariable UPLOAD_TYPE type, @PathVariable String fileName) {
        log.info("收到请求->分享文件");
        String url = fileInfoService.share(fileName, type);
        log.info("返回结果->分享文件结束:[{}],分享链接:[{}]", fileName, url);
        return new ResponseDTO<>(url);
    }
}
