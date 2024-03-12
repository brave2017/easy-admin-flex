package com.easy.modules.sys.service.impl;

import com.easy.common.constant.Constant;
import com.easy.common.exception.ErrorCode;
import com.easy.common.exception.ServiceException;
import com.easy.common.utils.ConvertUtils;
import com.easy.common.utils.TreeUtils;
import com.easy.modules.security.user.UserDetail;
import com.easy.modules.sys.dao.SysMenuDao;
import com.easy.modules.sys.dao.SysRoleMenuDao;
import com.easy.modules.sys.dao.SysRoleUserDao;
import com.easy.modules.sys.entity.table.SysMenuEntityTableDef;
import com.easy.modules.sys.entity.table.SysRoleMenuEntityTableDef;
import com.easy.modules.sys.entity.table.SysRoleUserEntityTableDef;
import com.easy.modules.sys.model.dto.SysMenuDTO;
import com.easy.modules.sys.entity.SysMenuEntity;
import com.easy.modules.sys.enums.SuperAdminEnum;
import com.easy.modules.sys.service.SysMenuService;
import com.easy.modules.sys.service.SysRoleMenuService;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import com.mybatisflex.core.row.RowUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {
    private final SysRoleMenuService sysRoleMenuService;
    private final SysRoleUserDao sysRoleUserDao;
    private final SysRoleMenuDao sysRoleMenuDao;

    @Override
    public SysMenuDTO get(Long id) {
        SysMenuEntity entity = QueryChain.of(mapper)
                .select(SysMenuEntityTableDef.SYS_MENU_ENTITY.ALL_COLUMNS)
                .select("(select name from sys_menu t2 where t2.id = t1.pid)").as("parentName")
                .from(SysMenuEntityTableDef.SYS_MENU_ENTITY.as("t1"))
                .where(SysMenuEntityTableDef.SYS_MENU_ENTITY.ID.eq(id))
                .one();
        return ConvertUtils.sourceToTarget(entity, SysMenuDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysMenuDTO dto) {
        SysMenuEntity entity = ConvertUtils.sourceToTarget(dto, SysMenuEntity.class);

        //保存菜单
        mapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysMenuDTO dto) {
        SysMenuEntity entity = ConvertUtils.sourceToTarget(dto, SysMenuEntity.class);

        //上级菜单不能为自身
        if (entity.getId().equals(entity.getPid())) {
            throw new ServiceException(ErrorCode.SUPERIOR_MENU_ERROR);
        }

        //更新菜单
        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        //删除菜单
        mapper.deleteById(id);

        //删除角色菜单关系
        sysRoleMenuService.deleteByMenuId(id);
    }

    @Override
    public List<SysMenuDTO> getAllMenuList(Integer menuType) {
        List<SysMenuEntity> menuList = QueryChain.of(mapper).select(SysMenuEntityTableDef.SYS_MENU_ENTITY.ALL_COLUMNS)
                .where(SysMenuEntityTableDef.SYS_MENU_ENTITY.MENU_TYPE.eq(menuType, Objects.nonNull(menuType)))
                .orderBy(SysMenuEntityTableDef.SYS_MENU_ENTITY.SORT.asc())
                .list();
        List<SysMenuDTO> dtoList = ConvertUtils.sourceToTarget(menuList, SysMenuDTO.class);
        return TreeUtils.build(dtoList, Constant.MENU_ROOT);
    }

    @Override
    public List<SysMenuDTO> getUserMenuList(UserDetail user, Integer menuType) {
        List<SysMenuEntity> menuList;

        //系统管理员，拥有最高权限
        if (Objects.equals(user.getSuperAdmin(), SuperAdminEnum.YES.getValue())) {
            menuList = QueryChain.of(mapper).select(SysMenuEntityTableDef.SYS_MENU_ENTITY.ALL_COLUMNS)
                    .where(SysMenuEntityTableDef.SYS_MENU_ENTITY.MENU_TYPE.eq(menuType, Objects.nonNull(menuType)))
                    .orderBy(SysMenuEntityTableDef.SYS_MENU_ENTITY.SORT.asc())
                    .list();
        } else {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.select(SysRoleUserEntityTableDef.SYS_ROLE_USER_ENTITY.ALL_COLUMNS)
                    .leftJoin(SysRoleMenuEntityTableDef.SYS_ROLE_MENU_ENTITY).on(SysRoleUserEntityTableDef.SYS_ROLE_USER_ENTITY.ROLE_ID.eq(SysRoleMenuEntityTableDef.SYS_ROLE_MENU_ENTITY))
                    .leftJoin(SysMenuEntityTableDef.SYS_MENU_ENTITY).on(SysRoleMenuEntityTableDef.SYS_ROLE_MENU_ENTITY.MENU_ID.eq(SysMenuEntityTableDef.SYS_MENU_ENTITY.ID))
                    .where(SysRoleUserEntityTableDef.SYS_ROLE_USER_ENTITY.USER_ID.eq(user.getId()))
                    .and(SysMenuEntityTableDef.SYS_MENU_ENTITY.MENU_TYPE.eq(menuType, Objects.nonNull(menuType)))
                    .orderBy(SysMenuEntityTableDef.SYS_MENU_ENTITY.SORT.asc());
            List<Row> rows = Db.selectListByQuery(queryWrapper);
            menuList = RowUtil.toEntityList(rows, SysMenuEntity.class);
        }

        List<SysMenuDTO> dtoList = ConvertUtils.sourceToTarget(menuList, SysMenuDTO.class);
        return TreeUtils.build(dtoList);
    }

    @Override
    public List<SysMenuDTO> getListPid(Long pid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(SysMenuEntity::getPid,pid);
        List<SysMenuEntity> menuList =  mapper.selectListByQuery(queryWrapper);
        return ConvertUtils.sourceToTarget(menuList, SysMenuDTO.class);
    }

}
