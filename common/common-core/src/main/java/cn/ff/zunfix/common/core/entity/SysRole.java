package cn.ff.zunfix.common.core.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class SysRole implements Serializable {

    private String id;
    private String code;
    private String name;
    private String level;
    private String dataScope;
    private String createBy;
    private Date createTime;
    private String delFlag;
}
