package cn.crabapples.system.entity;

import cn.crabapples.common.base.BaseEntity;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Setter
@Getter
@Entity
public class FileInfo extends BaseEntity {
    @Column(columnDefinition = "varchar(256) default null comment '原文件名'")
    private String oldName;
    @Column(columnDefinition = "varchar(256) default null comment '文件虚拟访问路径'")
    private String virtualPath;
    @Column(columnDefinition = "varchar(256) default null comment '文件真实保存访问路径'")
    private String uploadPath;
    @Column(columnDefinition = "varchar(256) default null comment '文件类型'")
    private String contentType;
    @Column(columnDefinition = "long default null comment '文件大小'")
    private Long fileSize;


    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
