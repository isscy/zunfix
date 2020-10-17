package cn.ff.zunfix.common.security.mapper;


import cn.ff.zunfix.common.core.entity.SysResource;
import cn.ff.zunfix.common.core.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资源聚合，包含 sys_menu 、 sys_app_page 、 sys_file 等资源
 *
 * @author fengfan 2020/5/8
 */

@Repository
public interface SysResourceMapper extends BaseMapper<SysResource> {


    /**
     * 获取全部的 包含sys_menu 和 sys_app_page 的 权限表中 有 url的数据
     */
    List<SysResource> getAllAuthority();


    /**
     * 获取 url - roles 的对应关系 缓存到Redis中用
     */
    List<SysResource> getResourceRolesCacheRel();





}
