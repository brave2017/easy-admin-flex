package com.easy.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 *  字典数据
 *
 * @author Maw
 */
@Data
public class DictData {
    @JsonIgnore
    private Long dictTypeId;
    private String dictLabel;
    private String dictValue;
}
