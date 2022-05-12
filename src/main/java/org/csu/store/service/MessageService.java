package org.csu.store.service;

import org.csu.store.BO.MessageBO;
import org.csu.store.common.CommonResponse;
import org.csu.store.domain.Message;

import java.util.List;

public interface MessageService {

    CommonResponse<List<Message>> getMesList(Integer proId);

    CommonResponse<String> levMes(MessageBO messageBO);
}
