package com.easy.modules.sys.controller;

import com.easy.common.annotation.LogOperation;
import com.easy.common.constant.Constant;
import com.easy.common.page.PageData;
import com.easy.common.utils.Result;
import com.easy.common.validator.AssertUtils;
import com.easy.common.validator.ValidatorUtils;
import com.easy.common.validator.group.DefaultGroup;
import com.easy.common.validator.group.UpdateGroup;
import com.easy.modules.sys.model.dto.SysDictDataDTO;
import com.easy.modules.sys.model.qo.DeleteQo;
import com.easy.modules.sys.model.qo.DictDataQo;
import com.easy.modules.sys.service.SysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 字典数据
 *
 * @author Maw
 */
@RestController
@RequestMapping("sys/dict/data")
@Api(tags = "字典数据")
@AllArgsConstructor
public class SysDictDataController {
    private final SysDictDataService sysDictDataService;

    @PostMapping("page")
    @ApiOperation("字典数据")
    @RequiresPermissions("sys:dict:page")
    public Result<PageData<SysDictDataDTO>> page(@RequestBody DictDataQo qo) {
        PageData<SysDictDataDTO> page = sysDictDataService.pageList(qo);
        return Result.success(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("sys:dict:info")
    public Result<SysDictDataDTO> get(@PathVariable("id") Long id) {
        SysDictDataDTO data = sysDictDataService.get(id);

        return Result.success(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("sys:dict:save")
    public Result<Void> save(@RequestBody SysDictDataDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, DefaultGroup.class);

        sysDictDataService.save(dto);

        return Result.success();
    }

    @PostMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("sys:dict:update")
    public Result<Void> update(@RequestBody SysDictDataDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        sysDictDataService.update(dto);

        return Result.success();
    }

    @PostMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("sys:dict:delete")
    public Result<Void> delete(@RequestBody DeleteQo qo) {
        sysDictDataService.delete(qo.getIds());
        return Result.success();
    }

}
