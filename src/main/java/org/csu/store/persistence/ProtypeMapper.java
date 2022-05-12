package org.csu.store.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.store.domain.Protype;
import org.csu.store.domain.User;
import org.springframework.stereotype.Repository;

@Repository("protypeMapper")
public interface ProtypeMapper extends BaseMapper<Protype> {
}
