package cn.crabapples.system.service.impl;

import cn.crabapples.system.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件功能实现类
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {
    @Value("${virtualPath}")
    private String virtualPath;
    @Value("${filePath}")
    private String filePath;

    /**
     * 上传文件
     */
    @Override
    public Map<String, String> uploadFile(HttpServletRequest request) {
        String path = getFilePath(request, filePath, virtualPath);
        Map<String, String> result = new HashMap<>();
        result.put("path", path);
        return result;
    }

}
