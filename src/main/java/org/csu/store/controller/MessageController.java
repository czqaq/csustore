package org.csu.store.controller;

import org.csu.store.BO.MessageBO;
import org.csu.store.common.CommonResponse;
import org.csu.store.domain.Message;
import org.csu.store.domain.User;
import org.csu.store.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/message/")
@Validated
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("get_mes_list")
    public CommonResponse<List<Message>> getMesList(@RequestParam @Validated @NotBlank(message = "商品id不能为空") String proId){
        return messageService.getMesList(Integer.valueOf(proId));
    }

    @PostMapping("leave_mes")
    public CommonResponse<String> levMes(@RequestBody MessageBO messageBO){
        return messageService.levMes(messageBO);
    }
}
