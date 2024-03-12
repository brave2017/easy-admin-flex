package com.easy.modules.sys.model.qo;


import com.easy.common.page.PageEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Maw
 */
@ApiModel(value = "用户列表请求参数")
@EqualsAndHashCode(callSuper = true)
@Data
public class UserListQo extends PageEntity {

    private String username;
    private String gender;
    private Long deptId;

}
