package cn.ff.zunfix.common.security.component.annotation;

import java.lang.annotation.*;

/**
 * 用于内部服务调用不鉴权的 注解
 *
 * @author fengfan 2020/10/13
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Inner {


}
