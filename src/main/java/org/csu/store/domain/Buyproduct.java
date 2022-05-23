package org.csu.store.domain;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class Buyproduct {
    private Integer proId;

    private String proTitle;
    private String userName;
    private String typeName;
    private String proPrice;
    private String proDetail;
    private String imageUrl;
    private LocalDateTime proTime;
    private LocalDateTime ordTime;
    private Integer num;
}
