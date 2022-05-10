package org.csu.store.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.store.domain.User;
import org.springframework.stereotype.Repository;

@Repository("userMapper")
public interface UserMapper extends BaseMapper<User> {
}