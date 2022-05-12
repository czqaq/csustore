package org.csu.store.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("cart")
public class Cart {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;
    private String items;
    private LocalDateTime updateTime;
}
