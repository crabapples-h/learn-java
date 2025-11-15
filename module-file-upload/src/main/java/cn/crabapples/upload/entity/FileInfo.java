package cn.crabapples.upload.entity;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;


@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@TableName("file_info")
@Accessors(chain = true)
public class FileInfo extends Model<FileInfo> {

//    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    @Id
    private String id;

    private String oldName; // 原文件名
    private String virtualPath; // 文件虚拟访问路径
    private String uploadPath; // 文件保存路径
    private String contentType;// 文件类型
    private Long fileSize; // 文件大小
    private String saveType; // 存储方式

    @TableField
    @TableLogic
    private byte delFlag;

    // 租户, 多个用逗号隔开
    private String tenantId;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
