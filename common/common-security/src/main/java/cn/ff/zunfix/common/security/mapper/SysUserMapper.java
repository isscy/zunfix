package cn.ff.zunfix.common.security.mapper;


import cn.ff.zunfix.common.core.entity.SysRole;
import cn.ff.zunfix.common.core.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户信息
 *
 * @author fengfan at 2019-07-27
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 获取用户的列表
     */
    Page<SysUser> getList(Page<SysUser> page, @Param("status") String status,
                          @Param("search") String search);

    /**
     * 通过id获取一个用户信息
     */
    SysUser getById(Integer userId);

    /**
     * 通过用户名获取一个用户
     * @param isUsing 是否启用 为true则限制用户的status为1
     */
    SysUser getByUserName(@Param("userName") String userName, @Param("isUsing") boolean isUsing);

    /**
     * 通过手机号获取一个用户
     * @param isUsing 是否启用 为true则限制用户的status为1
     */
    SysUser getByPhone(@Param("phone") String phone, @Param("isUsing") boolean isUsing);


    /**
     * 通过id仅更新密码
     */
    int updatePwdById(@Param("id") int id, @Param("pwd") String pwd);

    /**
     * 根据用户id 批量删除用户
     * @param userIds 用户ID数组
     */
    void deleteBatch(@Param("userIds") Long[] userIds);


    /**
     * 获取一个用户的所有角色
     */
    List<SysRole> getOneUserAllRoles(String userId);


    /**
     * 通过角色的code获取下面的用户
     */
    List<SysUser> getListByRoleCode(String roleCode);

    /**
     * 通过角色的ID获取下面的用户
     */
    List<SysUser> getListByRole(Integer roleId);


}
