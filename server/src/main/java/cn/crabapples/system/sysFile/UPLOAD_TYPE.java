package cn.crabapples.system.sysFile;

public enum UPLOAD_TYPE {
    LOCAL("upload_local"),
    MINIO("upload_minio");

    public final String type;

    UPLOAD_TYPE(String type) {
        this.type = type;
    }
}
