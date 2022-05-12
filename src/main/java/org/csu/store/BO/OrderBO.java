package org.csu.store.BO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderBO {
    private Integer buyerId;
    private Integer proId;
    private Integer state;
}
