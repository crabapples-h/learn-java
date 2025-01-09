package cn.crabapples.system.sysFile.entity;

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
    //原文件名
    private String oldName;

    //文件虚拟访问路径
    private String virtualPath;

    //文件真实保存访问路径
    private String uploadPath;

    // 文件类型
    private String contentType;

    //文件大小
    private Long fileSize;

    @Column(isLogicDelete = true)
    private byte delFlag;

    // 租户, 多个用逗号隔开
    private String tenantId;
}
