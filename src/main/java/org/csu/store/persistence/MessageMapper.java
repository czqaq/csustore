package org.csu.store.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.store.domain.Message;
import org.springframework.stereotype.Repository;

@Repository("messageMapper")
public interface MessageMapper extends BaseMapper<Message> {
}
