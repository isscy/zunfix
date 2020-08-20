package cn.ff.zunfix.common.core.entity;

import cn.ff.zunfix.common.core.constant.CommonConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 基础实体类
 * @author fengfan at 2020/4/12
 * @todo 加上获取当前用户ID的方法； 处理日期格式LoadDatetime；
 */
@Data
@Slf4j
public class BaseEntity implements Serializable {

    /**
     * 创建人
     */
    public Integer createdBy;
    /**
     * 创建人姓名
     */
    public String createdByName;
    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    // todo 将 LocalDateTime 转为 字符串 返回给前端
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 对前端传入的日期进行格式化
    private LocalDateTime createdTime;
    /*@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date createdTime;
     */
    /**
     * 更新人
     */
    public Integer updatedBy;
    /**
     * 更新人姓名
     */
    public String updatedByName;
    /**
     * 更新时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime updatedTime;
    /**
     * 删除标志
     */
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String delFlag;

    /**
     * 当前页数
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Integer current = 1;
    /**
     * 每页显示行数
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Integer size = 10;


    public void preInsert() {
        createdBy = targetUserId();
        createdTime = LocalDateTime.now();//new Date();
        delFlag = CommonConstant.DEL_NORMAL;
    }

    public void preUpdate() {
        updatedBy = targetUserId();
        updatedTime = LocalDateTime.now();//new Date();
    }

    public void preDelete() {
        updatedBy = targetUserId();
        updatedTime = LocalDateTime.now();//new Date();
        delFlag = CommonConstant.DEL_YES;
    }

    /**
     * 获取用户ID的方法
     */
    private Integer targetUserId() {
       /* Integer userId = null;
        try {
            userId = SecurityUtil.getUserId();
        } catch (Exception e) {
            log.error("[通过SecurityContextHolder获取UserId错误]: " + e.getMessage());
            try {
                UserTokenUtil userTokenUtil = SpringContextUtil.getBean("userTokenUtil", UserTokenUtil.class);
                userId = userTokenUtil.getUserId();
            } catch (NullPointerException ignored) {
            } catch (Exception e1) {
                log.error("", e);
//                e.printStackTrace(); // 再报错就没办法了
            }
        }
        return userId;*/
        return null; //
    }
}
