package org.csu.store.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("protype")
public class Protype {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String typename;
}
