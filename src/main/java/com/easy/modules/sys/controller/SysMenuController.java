package com.easy.modules.sys.controller;

import com.easy.common.annotation.LogOperation;
import com.easy.common.exception.ErrorCode;
import com.easy.common.utils.Result;
import com.easy.common.validator.AssertUtils;
import com.easy.common.validator.ValidatorUtils;
import com.easy.common.validator.group.DefaultGroup;
import com.easy.modules.security.service.ShiroService;
import com.easy.modules.security.user.SecurityUser;
import com.easy.modules.security.user.UserDetail;
import com.easy.modules.sys.model.dto.SysMenuDTO;
import com.easy.modules.sys.enums.MenuTypeEnum;
import com.easy.modules.sys.model.qo.DeleteQo;
import com.easy.modules.sys.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 菜单管理
 *
 * @author Maw
 */
@RestController
@RequestMapping("/sys/menu")
@Api(tags = "菜单管理")
@AllArgsConstructor
public class SysMenuController {
    private final SysMenuService sysMenuService;
    private final ShiroService shiroService;

    @GetMapping("nav")
    @ApiOperation("导航")
    public Result<List<SysMenuDTO>> nav() {
        UserDetail user = SecurityUser.getUser();
        List<SysMenuDTO> list = sysMenuService.getUserMenuList(user, MenuTypeEnum.MENU.value());
        return Result.success(list);
    }

    @GetMapping("permissions")
    @ApiOperation("权限标识")
    public Result<Set<String>> permissions() {
        UserDetail user = SecurityUser.getUser();
        Set<String> set = shiroService.getUserPermissions(user);

        return Result.success(set);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    @ApiImplicitParam(name = "type", value = "菜单类型 0：菜单 1：按钮  null：全部", paramType = "query", dataType = "int")
    @RequiresPermissions("sys:menu:list")
    public Result<List<SysMenuDTO>> list(@RequestParam Integer type) {
        List<SysMenuDTO> list = sysMenuService.getAllMenuList(type);
        return Result.success(list);
    }

    @PostMapping("pageList")
    @ApiOperation("列表")
    @ApiImplicitParam(name = "type", value = "菜单类型 0：菜单 1：按钮  null：全部", paramType = "query", dataType = "int")
    @RequiresPermissions("sys:menu:list")
    public Result<List<SysMenuDTO>> pageList() {
        List<SysMenuDTO> list = sysMenuService.getAllMenuList(0);
        return Result.success(list);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("sys:menu:info")
    public Result<SysMenuDTO> get(@PathVariable("id") Long id) {
        SysMenuDTO data = sysMenuService.get(id);

        return Result.success(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("sys:menu:save")
    public Result<Void> save(@RequestBody SysMenuDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, DefaultGroup.class);

        sysMenuService.save(dto);

        return Result.success();
    }

    @PostMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("sys:menu:update")
    public Result<Void> update(@RequestBody SysMenuDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, DefaultGroup.class);

        sysMenuService.update(dto);

        return Result.success();
    }

    @PostMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("sys:menu:delete")
    public Result<Void> delete(@RequestBody DeleteQo qo) {
        //判断是否有子菜单或按钮
        List<SysMenuDTO> list = sysMenuService.getListPid(qo.getId());
        if (list.size() > 0) {
            return Result.error(ErrorCode.SUB_MENU_EXIST);
        }

        sysMenuService.delete(qo.getId());
        return Result.success();
    }

    @GetMapping("select")
    @ApiOperation("角色菜单权限")
    @RequiresPermissions("sys:menu:select")
    public Result<List<SysMenuDTO>> select() {
        UserDetail user = SecurityUser.getUser();
        List<SysMenuDTO> list = sysMenuService.getUserMenuList(user, null);
        return Result.success(list);
    }
}
