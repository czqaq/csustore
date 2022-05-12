package org.csu.store.BO;

import lombok.Data;

@Data
public class MessageBO {
    private Integer userId;
    private Integer proId;
    private String content;
}
