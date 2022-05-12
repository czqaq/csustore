package org.csu.store.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@TableName("morder")
public class Morder {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer buyerId;
    private Integer proId;
    private LocalDateTime Time;
    private Integer state;
}
