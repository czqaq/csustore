package org.csu.store.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("cart")
public class Cart {
    private Integer id;
    private Integer userId;
    private String items;
    private LocalDateTime updateTime;
}
