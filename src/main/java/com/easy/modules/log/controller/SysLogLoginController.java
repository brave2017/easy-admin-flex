package com.easy.modules.log.controller;

import com.easy.common.page.PageData;
import com.easy.common.utils.Result;
import com.easy.modules.log.dto.SysLogLoginDTO;
import com.easy.modules.log.model.qo.LogLoginQo;
import com.easy.modules.log.service.SysLogLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 登录日志
 *
 * @author Maw
 * @since 1.0.0
 */
@RestController
@RequestMapping("sys/log/login")
@Api(tags = "登录日志")
@AllArgsConstructor
public class SysLogLoginController {
    private final SysLogLoginService sysLogLoginService;

    @PostMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("sys:log:login")
    public Result<PageData<SysLogLoginDTO>> page(@RequestBody LogLoginQo qo) {
        PageData<SysLogLoginDTO> page = sysLogLoginService.page(qo);

        return Result.success(page);
    }

}
