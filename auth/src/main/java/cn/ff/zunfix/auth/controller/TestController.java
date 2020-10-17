package cn.ff.zunfix.auth.controller;

import cn.ff.zunfix.common.core.entity.R;
import cn.ff.zunfix.common.core.utils.Rs;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * _
 *
 * @author fengfan 2020/9/1
 */
@RestController
@RequestMapping("pk")
@AllArgsConstructor
public class TestController {


    @GetMapping("tester")
    public R tester() {
        return Rs.ok("aHaha");
    }

}
