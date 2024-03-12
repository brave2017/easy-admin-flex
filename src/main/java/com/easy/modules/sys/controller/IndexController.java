package com.easy.modules.sys.controller;

import com.easy.common.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页提示
 *
 * @author Maw
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public Result<String> index(){
        String tips = "你好，easy-admin已启动，请启动easy-ui，才能访问页面！";
        return Result.success(tips);
    }
}
