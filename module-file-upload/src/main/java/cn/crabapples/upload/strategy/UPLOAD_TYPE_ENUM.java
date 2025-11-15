package cn.crabapples.upload.strategy;

public enum UPLOAD_TYPE_ENUM {
    LOCAL("upload_local"),
    MINIO("upload_minio"),
    RUST_FS("upload_rustfs");

    public final String type;

    UPLOAD_TYPE_ENUM(String type) {
        this.type = type;
    }
}
