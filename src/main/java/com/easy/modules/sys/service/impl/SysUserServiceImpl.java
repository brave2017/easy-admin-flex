package com.easy.modules.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.easy.common.page.PageData;
import com.easy.common.utils.ConvertUtils;
import com.easy.modules.security.password.PasswordUtils;
import com.easy.modules.security.user.SecurityUser;
import com.easy.modules.security.user.UserDetail;
import com.easy.modules.sys.dao.SysUserDao;
import com.easy.modules.sys.entity.SysUserEntity;
import com.easy.modules.sys.entity.table.SysDeptEntityTableDef;
import com.easy.modules.sys.entity.table.SysUserEntityTableDef;
import com.easy.modules.sys.enums.SuperAdminEnum;
import com.easy.modules.sys.model.dto.SysUserDTO;
import com.easy.modules.sys.model.qo.DeleteQo;
import com.easy.modules.sys.model.qo.UserListQo;
import com.easy.modules.sys.service.SysDeptService;
import com.easy.modules.sys.service.SysRoleUserService;
import com.easy.modules.sys.service.SysUserService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.StringUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 系统用户
 *
 * @author Maw
 */
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
    private final SysRoleUserService sysRoleUserService;
    private final SysDeptService sysDeptService;

    @Override
    public PageData<SysUserDTO> pageList(UserListQo qo) {
        //普通管理员，只能查询所属部门及子部门的数据
        UserDetail user = SecurityUser.getUser();
        List<Long> deptIdList = new ArrayList<>();
        if (SuperAdminEnum.NO.getValue().equals(user.getSuperAdmin())) {
            deptIdList = sysDeptService.getSubDeptIdList(user.getDeptId());
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(SysUserEntityTableDef.SYS_USER_ENTITY.ALL_COLUMNS,SysDeptEntityTableDef.SYS_DEPT_ENTITY.NAME.as("deptName"))
                .from(SysUserEntityTableDef.SYS_USER_ENTITY)
                .leftJoin(SysDeptEntityTableDef.SYS_DEPT_ENTITY)
                .on(SysUserEntityTableDef.SYS_USER_ENTITY.DEPT_ID.eq(SysDeptEntityTableDef.SYS_DEPT_ENTITY.ID))
                .where(SysUserEntityTableDef.SYS_USER_ENTITY.USERNAME.like(qo.getUsername(), StringUtil::isNotBlank))
                .and(SysUserEntityTableDef.SYS_USER_ENTITY.GENDER.eq(qo.getGender(),StringUtil::isNotBlank))
                .and(SysUserEntityTableDef.SYS_USER_ENTITY.GENDER.eq(qo.getGender(),StringUtil::isNotBlank))
                .and(SysUserEntityTableDef.SYS_USER_ENTITY.DEPT_ID.in(deptIdList));

        Page<SysUserEntity> paginate = mapper.paginate(qo.getPage(), qo.getLimit(), queryWrapper);
        List<SysUserEntity> records = paginate.getRecords();
        List<SysUserDTO> data = ConvertUtils.sourceToTarget(records, SysUserDTO.class);
        return new PageData<>(data,paginate.getTotalRow());
    }

    @Override
    public SysUserDTO getById(Long id) {
        SysUserEntity userEntity = QueryChain.of(mapper)
                .select(SysUserEntityTableDef.SYS_USER_ENTITY.ALL_COLUMNS, SysDeptEntityTableDef.SYS_DEPT_ENTITY.NAME.as("deptName"))
                .from(SysUserEntityTableDef.SYS_USER_ENTITY)
                .leftJoin(SysDeptEntityTableDef.SYS_DEPT_ENTITY).on(SysUserEntityTableDef.SYS_USER_ENTITY.DEPT_ID.eq(SysDeptEntityTableDef.SYS_DEPT_ENTITY.ID))
                .where(SysUserEntityTableDef.SYS_USER_ENTITY.ID.eq(id))
                .one();

        List<Long> roleIdList = sysRoleUserService.getRoleIdList(id);
        SysUserDTO data = ConvertUtils.sourceToTarget(userEntity, SysUserDTO.class);
        data.setRoleIdList(roleIdList);
        return data;
    }

    @Override
    public SysUserDTO getByUsername(String username) {
        SysUserEntity entity = QueryChain.of(mapper)
                .where(SysUserEntityTableDef.SYS_USER_ENTITY.USERNAME.eq(username))
                .one();
        return ConvertUtils.sourceToTarget(entity, SysUserDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysUserDTO dto) {
        SysUserEntity entity = ConvertUtils.sourceToTarget(dto, SysUserEntity.class);

        //密码加密
        String password = PasswordUtils.encode(entity.getPassword());
        entity.setPassword(password);

        //保存用户
        entity.setSuperAdmin(SuperAdminEnum.NO.getValue());
        mapper.insert(entity);

        //保存角色用户关系
        sysRoleUserService.saveOrUpdate(entity.getId(), dto.getRoleIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUserDTO dto) {
        SysUserEntity entity = ConvertUtils.sourceToTarget(dto, SysUserEntity.class);

        //密码加密
        if (StrUtil.isBlank(dto.getPassword())) {
            entity.setPassword(null);
        } else {
            String password = PasswordUtils.encode(entity.getPassword());
            entity.setPassword(password);
        }

        //更新用户
        updateById(entity);

        //更新角色用户关系
        sysRoleUserService.saveOrUpdate(entity.getId(), dto.getRoleIdList());
    }

    @Override
    public void delete(List<Long> userIds) {
        //删除用户
        mapper.deleteBatchByIds(userIds);

        //删除角色用户关系
        sysRoleUserService.deleteByUserIds(userIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(Long id, String newPassword) {
        newPassword = PasswordUtils.encode(newPassword);
        SysUserEntity sysUserEntity = mapper.selectOneById(id);
        sysUserEntity.setPassword(newPassword);
        mapper.update(sysUserEntity);
    }


    @Override
    public void deleteBatchIds(DeleteQo qo) {
        mapper.deleteBatchByIds(qo.getIds());
    }
}
