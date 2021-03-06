package org.csu.store.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("product")
public class Product {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;
    private Integer sellerId;
    private String type;
    private String price;
    private String imgUrl;
    private String detail;
    private LocalDateTime upTime;
    private Integer num;
}
