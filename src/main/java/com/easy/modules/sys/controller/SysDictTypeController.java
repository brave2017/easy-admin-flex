package com.easy.modules.sys.controller;

import com.easy.common.annotation.LogOperation;
import com.easy.common.constant.Constant;
import com.easy.common.page.PageData;
import com.easy.common.utils.Result;
import com.easy.common.validator.AssertUtils;
import com.easy.common.validator.ValidatorUtils;
import com.easy.common.validator.group.DefaultGroup;
import com.easy.common.validator.group.UpdateGroup;
import com.easy.modules.sys.model.dto.SysDictTypeDTO;
import com.easy.modules.sys.entity.DictType;
import com.easy.modules.sys.model.qo.DeleteQo;
import com.easy.modules.sys.model.qo.DictTypeQo;
import com.easy.modules.sys.service.SysDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 字典类型
 *
 * @author Maw
 */
@RestController
@RequestMapping("sys/dict/type")
@Api(tags = "字典类型")
@AllArgsConstructor
public class SysDictTypeController {
    private final SysDictTypeService sysDictTypeService;

    @PostMapping("page")
    @ApiOperation("字典类型")
    @RequiresPermissions("sys:dict:page")
    public Result<PageData<SysDictTypeDTO>> page(@RequestBody DictTypeQo qo) {
        PageData<SysDictTypeDTO> page = sysDictTypeService.page(qo);
        return Result.success(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("sys:dict:info")
    public Result<SysDictTypeDTO> get(@PathVariable("id") Long id) {
        SysDictTypeDTO data = sysDictTypeService.get(id);
        return Result.success(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("sys:dict:save")
    public Result<Void> save(@RequestBody SysDictTypeDTO dto) {
        ValidatorUtils.validateEntity(dto, DefaultGroup.class);
        sysDictTypeService.save(dto);
        return Result.success();
    }

    @PostMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("sys:dict:update")
    public Result<Void> update(@RequestBody SysDictTypeDTO dto) {
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);
        sysDictTypeService.update(dto);
        return Result.success();
    }

    @PostMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("sys:dict:delete")
    public Result<Void> delete(@RequestBody DeleteQo qo) {
        sysDictTypeService.delete(qo);
        return Result.success();
    }

    @GetMapping("all")
    @ApiOperation("所有字典数据")
    public Result<List<DictType>> all() {
        List<DictType> list = sysDictTypeService.getAllList();
        return Result.success(list);
    }

}
