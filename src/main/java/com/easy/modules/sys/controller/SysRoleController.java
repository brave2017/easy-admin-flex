package com.easy.modules.sys.controller;

import com.easy.common.annotation.LogOperation;
import com.easy.common.page.PageData;
import com.easy.common.utils.Result;
import com.easy.common.validator.AssertUtils;
import com.easy.common.validator.ValidatorUtils;
import com.easy.common.validator.group.AddGroup;
import com.easy.common.validator.group.DefaultGroup;
import com.easy.common.validator.group.UpdateGroup;
import com.easy.modules.sys.model.dto.SysRoleDTO;
import com.easy.modules.sys.model.qo.DeleteQo;
import com.easy.modules.sys.model.qo.RoleListQo;
import com.easy.modules.sys.service.SysRoleDataScopeService;
import com.easy.modules.sys.service.SysRoleMenuService;
import com.easy.modules.sys.service.SysRoleService;
import com.mybatisflex.core.query.QueryChain;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 角色管理
 *
 * @author Maw
 */
@RestController
@RequestMapping("/sys/role")
@Api(tags = "角色管理")
@AllArgsConstructor
public class SysRoleController {
    private final SysRoleService sysRoleService;
    private final SysRoleMenuService sysRoleMenuService;
    private final SysRoleDataScopeService sysRoleDataScopeService;

    @PostMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("sys:role:page")
    public Result<PageData<SysRoleDTO>> page(@RequestBody RoleListQo qo) {
        PageData<SysRoleDTO> page = sysRoleService.pageList(qo);
        return Result.success(page);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    @RequiresPermissions("sys:role:list")
    public Result<List<SysRoleDTO>> list() {
        List<SysRoleDTO> data = sysRoleService.roleList();
        return Result.success(data);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("sys:role:info")
    public Result<SysRoleDTO> get(@PathVariable("id") Long id) {
        SysRoleDTO data = sysRoleService.get(id);

        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.getMenuIdList(id);
        data.setMenuIdList(menuIdList);

        //查询角色对应的数据权限
        List<Long> deptIdList = sysRoleDataScopeService.getDeptIdList(id);
        data.setDeptIdList(deptIdList);

        return Result.success(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("sys:role:save")
    public Result<Void> save(@RequestBody SysRoleDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);
        sysRoleService.save(dto);
        return Result.success();
    }

    @PostMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("sys:role:update")
    public Result<Void> update(@RequestBody SysRoleDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);
        sysRoleService.update(dto);
        return Result.success();
    }

    @PostMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("sys:role:delete")
    public Result<Void> delete(@RequestBody DeleteQo qo) {
        sysRoleService.delete(qo);
        return Result.success();
    }
}
