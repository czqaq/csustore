package org.csu.store.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@TableName("message")
public class Message {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer replyId;
    private Integer userId;
    private Integer proId;
    private String content;
    private LocalDateTime time;
}
