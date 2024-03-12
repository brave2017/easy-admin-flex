package com.easy.modules.sys.model.qo;


import lombok.Data;

import java.util.List;

/**
 * @author Maw
 */
@Data
public class DeleteQo {

    private Long id;
    private List<Long> ids;

}
