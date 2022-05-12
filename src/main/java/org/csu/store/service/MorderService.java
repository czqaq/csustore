package org.csu.store.service;

import org.csu.store.BO.OrderBO;
import org.csu.store.common.CommonResponse;
import org.csu.store.domain.Morder;

public interface MorderService {


    CommonResponse<String> insertMorder(OrderBO orderBO);
}
