package cn.crabapples.system.sysFile;

public enum UPLOAD_TYPE {
    LOCAL("upload_local"),
    MINIO("upload_minio"),
    RUST_FS("upload_rustfs");

    public final String type;

    UPLOAD_TYPE(String type) {
        this.type = type;
    }
}
