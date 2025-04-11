package cn.crabapples.system.sysFile;

public enum UploadTypeEnum {
    LOCAL("upload_local"),
    MINIO("upload_minio");

    public final String type;

    UploadTypeEnum(String type) {
        this.type = type;
    }
}
