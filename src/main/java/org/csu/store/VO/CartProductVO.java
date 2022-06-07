package org.csu.store.VO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CartProductVO {
    private Integer id;

    private String title;
    private String sellerName;
    private String type;
    private String price;
    private String imgUrl;
    private String detail;
    private LocalDateTime upTime;

}
