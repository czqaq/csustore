package org.csu.store.controller;

import org.csu.store.BO.MessageBO;
import org.csu.store.BO.OrderBO;
import org.csu.store.common.CommonResponse;
import org.csu.store.domain.Morder;
import org.csu.store.service.CartService;
import org.csu.store.service.MorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/order/")
public class MorderController {
    @Autowired
    private MorderService morderService;
    @Autowired
    private CartService cartService;

    @PostMapping("pay_Done")//购买完成，建立订单
    public CommonResponse<String> payDone(@RequestBody OrderBO orderBO) {
        System.out.println(orderBO.getProId());
        CommonResponse<String> CommonResponse=cartService.deleteFromCart(orderBO.getBuyerId(),orderBO.getProId());
        if(CommonResponse.isSuccess()){
            return (morderService.insertMorder(orderBO));
        }
        return CommonResponse;
    }

}
