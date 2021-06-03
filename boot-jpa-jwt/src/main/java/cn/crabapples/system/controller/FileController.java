package cn.crabapples.system.controller;

import cn.crabapples.common.base.BaseController;
import cn.crabapples.common.dto.ResponseDTO;
import cn.crabapples.system.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 文件接口
 */
@RestController
@Slf4j
@RequestMapping("/api")
public class FileController extends BaseController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @RequestMapping("/uploadFile")
    public ResponseDTO uploadFile(HttpServletRequest request) {
        log.info("收到请求->上传文件");
        Map<String, String> filePath = fileService.uploadFile(request);
        log.info("返回结果->上传文件结束:[{}]", filePath);
        return ResponseDTO.returnSuccess("上传成功", filePath);
    }


//    @RequestMapping("/saveFileData")
//    public ResponseDTO savePapers(@RequestBody List<FileForm> form) {
//        log.info("收到请求->保存文件:[{}]", form);
//        fileDataService.saveFile(form);
//        log.info("返回结果->保存文件完成");
//        return ResponseDTO.returnSuccess("操作成功");
//    }

}
