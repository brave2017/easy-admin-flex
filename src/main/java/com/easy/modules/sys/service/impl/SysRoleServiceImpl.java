package com.easy.modules.sys.service.impl;

import com.easy.common.page.PageData;
import com.easy.common.utils.ConvertUtils;
import com.easy.modules.sys.dao.SysRoleDao;
import com.easy.modules.sys.entity.SysRoleEntity;
import com.easy.modules.sys.entity.table.SysRoleEntityTableDef;
import com.easy.modules.sys.model.dto.SysRoleDTO;
import com.easy.modules.sys.model.qo.DeleteQo;
import com.easy.modules.sys.model.qo.RoleListQo;
import com.easy.modules.sys.service.*;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.StringUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色
 *
 * @author Maw
 */
@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {
    private final SysRoleMenuService sysRoleMenuService;
    private final SysRoleDataScopeService sysRoleDataScopeService;
    private final SysRoleUserService sysRoleUserService;
    private final SysDeptService sysDeptService;

    @Override
    public PageData<SysRoleDTO> pageList(RoleListQo qo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select()
                .where(SysRoleEntityTableDef.SYS_ROLE_ENTITY.NAME.like(qo.getName(), StringUtil::isNotBlank));
        Page<SysRoleEntity> paginate = mapper.paginate(qo.getPage(), qo.getLimit(), queryWrapper);
        List<SysRoleEntity> records = paginate.getRecords();
        if(CollectionUtils.isNotEmpty(records)){
            List<SysRoleDTO> dataList = records.stream()
                    .map(SysRoleEntity -> {
                        SysRoleDTO data = new SysRoleDTO();
                        data.setId(SysRoleEntity.getId());
                        data.setName(SysRoleEntity.getName());
                        data.setRemark(SysRoleEntity.getRemark());
                        data.setCreateDate(SysRoleEntity.getCreateDate());
                        return data;
                    })
                    .collect(Collectors.toList());
            return new PageData<>(dataList,paginate.getTotalRow());
        }
        return new PageData<>(null,paginate.getTotalRow());
    }

    @Override
    public SysRoleDTO get(Long id) {
        SysRoleEntity entity = mapper.selectOneById(id);

        return ConvertUtils.sourceToTarget(entity, SysRoleDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysRoleDTO dto) {
        SysRoleEntity entity = ConvertUtils.sourceToTarget(dto, SysRoleEntity.class);

        //保存角色
        mapper.insert(entity);

        //保存角色菜单关系
        sysRoleMenuService.saveOrUpdate(entity.getId(), dto.getMenuIdList());

        //保存角色数据权限关系
        sysRoleDataScopeService.saveOrUpdate(entity.getId(), dto.getDeptIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleDTO dto) {
        SysRoleEntity entity = ConvertUtils.sourceToTarget(dto, SysRoleEntity.class);

        //更新角色
        updateById(entity);

        //更新角色菜单关系
        sysRoleMenuService.saveOrUpdate(entity.getId(), dto.getMenuIdList());

        //更新角色数据权限关系
        sysRoleDataScopeService.saveOrUpdate(entity.getId(), dto.getDeptIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(DeleteQo qo) {
        //删除角色
        mapper.deleteBatchByIds(qo.getIds());

        //删除角色用户关系
        sysRoleUserService.deleteByRoleIds(qo.getIds());

        //删除角色菜单关系
        sysRoleMenuService.deleteByRoleIds(qo.getIds());

        //删除角色数据权限关系
        sysRoleDataScopeService.deleteByRoleIds(qo.getIds());
    }


    @Override
    public List<SysRoleDTO> roleList() {
        List<SysRoleEntity> list = mapper.selectAll();
        return ConvertUtils.sourceToTarget(list,SysRoleDTO.class);
    }
}
