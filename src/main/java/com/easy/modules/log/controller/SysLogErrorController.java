package com.easy.modules.log.controller;

import com.easy.common.page.PageData;
import com.easy.common.utils.Result;
import com.easy.modules.log.dto.SysLogErrorDTO;
import com.easy.modules.log.model.qo.LogErrorQo;
import com.easy.modules.log.service.SysLogErrorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 异常日志
 *
 * @author Maw
 * @since 1.0.0
 */
@RestController
@RequestMapping("sys/log/error")
@Api(tags = "异常日志")
@AllArgsConstructor
public class SysLogErrorController {
    private final SysLogErrorService sysLogErrorService;

    @PostMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("sys:log:error")
    public Result<PageData<SysLogErrorDTO>> page(@RequestBody LogErrorQo qo) {
        PageData<SysLogErrorDTO> page = sysLogErrorService.page(qo);
        return Result.success(page);
    }
}
