package cn.crabapples.system.sysFile.strategy;

import javax.servlet.http.HttpServletRequest;

public interface UploadFileStrategy {
    String upload(HttpServletRequest request);
}
