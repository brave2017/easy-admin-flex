package com.easy.modules.sys.controller;

import com.easy.common.annotation.LogOperation;
import com.easy.common.page.PageData;
import com.easy.common.utils.Result;
import com.easy.common.validator.ValidatorUtils;
import com.easy.common.validator.group.AddGroup;
import com.easy.common.validator.group.DefaultGroup;
import com.easy.common.validator.group.UpdateGroup;
import com.easy.modules.sys.model.dto.SysParamsDTO;
import com.easy.modules.sys.model.qo.DeleteQo;
import com.easy.modules.sys.model.qo.ParamsQo;
import com.easy.modules.sys.service.SysParamsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;


/**
 * 参数管理
 *
 * @author Maw
 * @since 1.0.0
 */
@RestController
@RequestMapping("sys/params")
@Api(tags = "参数管理")
@AllArgsConstructor
public class SysParamsController {
    private final SysParamsService sysParamsService;

    @PostMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("sys:params:page")
    public Result<PageData<SysParamsDTO>> page(@RequestBody ParamsQo qo) {
        PageData<SysParamsDTO> page = sysParamsService.page(qo);
        return Result.success(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("sys:params:info")
    public Result<SysParamsDTO> get(@PathVariable("id") Long id) {
        SysParamsDTO data = sysParamsService.get(id);
        return Result.success(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("sys:params:save")
    public Result<Void> save(@RequestBody SysParamsDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);
        sysParamsService.save(dto);
        return Result.success();
    }

    @PostMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("sys:params:update")
    public Result<Void> update(@RequestBody SysParamsDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);
        sysParamsService.update(dto);
        return Result.success();
    }

    @PostMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("sys:params:delete")
    public Result<Void> delete(@RequestBody DeleteQo qo) {
        sysParamsService.delete(qo);
        return Result.success();
    }
}
