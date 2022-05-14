package org.csu.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.store.BO.MessageBO;
import org.csu.store.common.CommonResponse;
import org.csu.store.domain.Message;
import org.csu.store.domain.User;
import org.csu.store.persistence.MessageMapper;
import org.csu.store.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Override
    public CommonResponse<List<Message>> getMesList(Integer proId) {

        List<Message> mesList;

        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pro_id", proId);
        mesList = messageMapper.selectList(queryWrapper);

        return CommonResponse.createForSuccess(mesList);
    }

    @Override
    public CommonResponse<String> levMes(MessageBO messageBO) {
        Message message = new Message();
        message.setUserId(messageBO.getUserId());
        message.setProId(messageBO.getProId());
        message.setContent(messageBO.getContent());
        message.setTime(LocalDateTime.now());

        int row = messageMapper.insert(message);
        if (row == 0){
            return CommonResponse.createForError("留言失败");
        }
        return CommonResponse.createForSuccessMessage("留言成功");
    }
}
