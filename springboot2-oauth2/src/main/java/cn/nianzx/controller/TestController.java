package cn.nianzx.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 测试用controller
 * Created by nianzx on 2019/1/28.
 */
@RestController
public class TestController {

    @RequestMapping("/home")
    public String home() {
        return "访问了home";
    }

    @RequestMapping("/userHome")
    public String userHome() {
        return "访问了userHome";
    }

    @RequestMapping("/adminHome")
    public String adminHome() {
        return "访问了adminHome";
    }
}
