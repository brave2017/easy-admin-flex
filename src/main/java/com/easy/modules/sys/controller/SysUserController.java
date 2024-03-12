package com.easy.modules.sys.controller;

import com.easy.common.annotation.LogOperation;
import com.easy.common.exception.ErrorCode;
import com.easy.common.page.PageData;
import com.easy.common.utils.ConvertUtils;
import com.easy.common.utils.Result;
import com.easy.common.validator.AssertUtils;
import com.easy.common.validator.ValidatorUtils;
import com.easy.common.validator.group.AddGroup;
import com.easy.common.validator.group.DefaultGroup;
import com.easy.common.validator.group.UpdateGroup;
import com.easy.modules.security.password.PasswordUtils;
import com.easy.modules.security.user.SecurityUser;
import com.easy.modules.security.user.UserDetail;
import com.easy.modules.sys.model.dto.PasswordDTO;
import com.easy.modules.sys.model.dto.SysUserDTO;
import com.easy.modules.sys.model.qo.DeleteQo;
import com.easy.modules.sys.model.qo.UserListQo;
import com.easy.modules.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 用户管理
 *
 * @author Maw
 */
@RestController
@RequestMapping("/sys/user")
@Api(tags = "用户管理")
@AllArgsConstructor
public class SysUserController {
    private final SysUserService sysUserService;

    @PostMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("sys:user:page")
    public Result<PageData<SysUserDTO>> page(@RequestBody UserListQo params) {
        PageData<SysUserDTO> page = sysUserService.pageList(params);
        return Result.success(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("sys:user:info")
    public Result<SysUserDTO> getById(@PathVariable("id") Long id) {
        SysUserDTO data = sysUserService.getById(id);
        return Result.success(data);
    }

    @GetMapping("info")
    @ApiOperation("登录用户信息")
    public Result<SysUserDTO> info() {
        SysUserDTO data = ConvertUtils.sourceToTarget(SecurityUser.getUser(), SysUserDTO.class);
        return Result.success(data);
    }

    @PostMapping("password")
    @ApiOperation("修改密码")
    @LogOperation("修改密码")
    public Result<Void> password(@RequestBody PasswordDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto);
        UserDetail user = SecurityUser.getUser();
        //原密码不正确
        if (!PasswordUtils.matches(dto.getPassword(), user.getPassword())) {
            return Result.error(ErrorCode.PASSWORD_ERROR);
        }
        sysUserService.updatePassword(user.getId(), dto.getNewPassword());
        return Result.success();
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("sys:user:save")
    public Result<Void> save(@RequestBody SysUserDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        sysUserService.save(dto);

        return Result.success();
    }

    @PostMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("sys:user:update")
    public Result<Void> update(@RequestBody SysUserDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        sysUserService.update(dto);

        return Result.success();
    }

    @PostMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("sys:user:delete")
    public Result<Void> delete(@RequestBody DeleteQo qo) {
        sysUserService.deleteBatchIds(qo);
        return Result.success();
    }
}
