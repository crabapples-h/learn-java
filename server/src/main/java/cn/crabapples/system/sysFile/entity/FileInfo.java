package cn.crabapples.system.sysFile.entity;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.activerecord.Model;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@Table("file_info")
@Accessors(chain = true)
public class FileInfo extends Model<FileInfo> {

    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;

    private String oldName; // 原文件名
    private String virtualPath; // 文件虚拟访问路径
    private String uploadPath; // 文件保存路径
    private String contentType;// 文件类型
    private Long fileSize; // 文件大小
    private String saveType; // 存储方式

    @Column(isLogicDelete = true)
    private byte delFlag;

    // 租户, 多个用逗号隔开
    private String tenantId;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
