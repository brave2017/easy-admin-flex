package com.easy.modules.sys.controller;

import com.easy.common.annotation.LogOperation;
import com.easy.common.utils.Result;
import com.easy.common.validator.AssertUtils;
import com.easy.common.validator.ValidatorUtils;
import com.easy.common.validator.group.AddGroup;
import com.easy.common.validator.group.DefaultGroup;
import com.easy.common.validator.group.UpdateGroup;
import com.easy.modules.sys.model.dto.SysDeptDTO;
import com.easy.modules.sys.model.qo.DeleteQo;
import com.easy.modules.sys.service.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 部门管理
 *
 * @author Maw
 */
@RestController
@RequestMapping("/sys/dept")
@Api(tags = "部门管理")
@AllArgsConstructor
public class SysDeptController {
    private final SysDeptService sysDeptService;

    @GetMapping("list")
    @ApiOperation("列表")
    @RequiresPermissions("sys:dept:list")
    public Result<List<SysDeptDTO>> list() {
        List<SysDeptDTO> list = sysDeptService.list(new HashMap<>(1));
        return Result.success(list);
    }

    @PostMapping("pageList")
    @ApiOperation("列表")
    @RequiresPermissions("sys:dept:list")
    public Result<List<SysDeptDTO>> pageList() {
        List<SysDeptDTO> list = sysDeptService.list(new HashMap<>(1));
        return Result.success(list);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("sys:dept:info")
    public Result<SysDeptDTO> get(@PathVariable("id") Long id) {
        SysDeptDTO data = sysDeptService.get(id);
        return Result.success(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("sys:dept:save")
    public Result<Void> save(@RequestBody SysDeptDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);
        sysDeptService.save(dto);
        return Result.success();
    }

    @PostMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("sys:dept:update")
    public Result<Void> update(@RequestBody SysDeptDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);
        sysDeptService.update(dto);
        return Result.success();
    }

    @PostMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("sys:dept:delete")
    public Result<Void> delete(@RequestBody DeleteQo dto) {
        //效验数据
        AssertUtils.isNull(dto.getId());
        sysDeptService.delete(dto.getId());
        return Result.success();
    }

}
