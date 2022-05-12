package org.csu.store.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("product")
public class Product {

    private Integer id;
    private Integer title;
    private Integer sellerId;
    private String type;
    private String price;
    private String imgUrl;
    private String detail;
    private LocalDateTime upTime;
}
