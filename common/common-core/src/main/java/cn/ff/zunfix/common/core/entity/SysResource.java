package cn.ff.zunfix.common.core.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *  PC的菜单、页面、按钮
 *  @author fengfan 2020/5/6
 */
@Data
public class SysResource {

    private String id;
    private String  clazz;
    private String type;
    private String name;
    private String title;
    private String parentId;
    private int level;
    private String url;
    private String method;
    private String serverId;
    private int orderNum;
    private String perms;
    private String icon;
    private String createBy;
    private Date createTime;
    private String remark;

    //@TableField(exist = false)
    private String ofRoles;
    private Set<SysRole> requiredRoles;
    private List<SysResource> children = new ArrayList<>();



}
