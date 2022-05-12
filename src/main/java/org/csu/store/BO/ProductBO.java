package org.csu.store.BO;

import lombok.Data;

@Data
public class ProductBO {
    private Integer title;
    private Integer sellerId;
    private String type;
    private String price;
    private String imgUrl;
    private String detail;
}
