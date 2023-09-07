package cn.crabapples.system.entity;

import cn.crabapples.common.dic.Dict;
import cn.crabapples.common.base.BaseEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID;


@Setter
@Getter
@TableName("file_info")
@ToString
public class FileInfo extends BaseEntity<FileInfo> {
    // id 为自增主键
    @TableId(type = ASSIGN_UUID)
    private String id;

    // 原文件名
    private String oldName;

    // 文件虚拟访问路径
    private String virtualPath;

    // 文件真实保存访问路径
    private String uploadPath;

    // 文件类型
    private String contentType;

    // 文件大小
    private Long fileSize;

    // 创建时间
    @CreatedDate
    @JSONField(format = "yyyy-MM-dd HH:mm:ss E")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    // 更新时间
    @LastModifiedDate
    @JSONField(format = "yyyy-MM-dd HH:mm:ss E")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    // 删除标记 (0:正常 1:删除)
    @TableLogic
    @Dict(dictCode = "delFlag")
    private Integer delFlag;

    //创建人
    @CreatedBy
    private String createBy;
}
