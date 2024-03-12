package com.easy.modules.log.controller;

import com.easy.common.page.PageData;
import com.easy.common.utils.Result;
import com.easy.modules.log.dto.SysLogOperationDTO;
import com.easy.modules.log.model.qo.LogOperationQo;
import com.easy.modules.log.service.SysLogOperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 操作日志
 *
 * @author Maw
 * @since 1.0.0
 */
@RestController
@RequestMapping("sys/log/operation")
@Api(tags = "操作日志")
@AllArgsConstructor
public class SysLogOperationController {
    private final SysLogOperationService sysLogOperationService;

    @PostMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("sys:log:operation")
    public Result<PageData<SysLogOperationDTO>> page(@RequestBody LogOperationQo qo) {
        PageData<SysLogOperationDTO> page = sysLogOperationService.page(qo);
        return Result.success(page);
    }

}
