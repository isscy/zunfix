package cn.ff.zunfix.common.core.entity;

import cn.ff.zunfix.common.core.utils.helper.StringJoinHelp;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 系统用户表
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity {
    private final static Logger LOGGER = LoggerFactory.getLogger(SysUser.class);
    private static final long serialVersionUID = 110L;


    private String id;
    private String userName;
    private String password;
    private String nickName;
    private String phone;
    private Date createTime;
    private String type;
    private String status;
    private String delFlag;


    private List<String> hadRoleCodes;
    private List<SysRole> ofRoles;


    public String getRolesString() {
        List<String> codes = new ArrayList<>();
        Optional.ofNullable(ofRoles).orElse(new ArrayList<>()).forEach(e -> codes.add(e.getCode()));
        if (codes.isEmpty()) {
            codes.addAll(Optional.ofNullable(hadRoleCodes).orElse(new ArrayList<>()));
        }
        String str = StringJoinHelp.on(",").join(codes);
        LOGGER.debug("用户拥有的角色列表:{}", str);
        return str;
    }

}
