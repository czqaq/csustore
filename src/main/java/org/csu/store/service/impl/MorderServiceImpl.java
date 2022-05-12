package org.csu.store.service.impl;

import org.csu.store.BO.OrderBO;
import org.csu.store.common.CommonResponse;
import org.csu.store.domain.Morder;
import org.csu.store.persistence.MorderMapper;
import org.csu.store.service.MorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service("morderService")
public class MorderServiceImpl implements MorderService {
    @Autowired
    private MorderMapper morderMapper;

    @Override
    public CommonResponse<String> insertMorder(OrderBO orderBO) {
        Morder morder = new Morder();
        morder.setBuyerId(orderBO.getBuyerId());
        morder.setProId(orderBO.getProId());
        morder.setState(orderBO.getState());
        morder.setTime(LocalDateTime.now());
        int row = morderMapper.insert(morder);
        if (row == 0){
            return CommonResponse.createForError("订单失败");
        }
        return CommonResponse.createForSuccessMessage("订单成功");
    }
}
