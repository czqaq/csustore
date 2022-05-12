package org.csu.store.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.store.domain.Morder;
import org.springframework.stereotype.Repository;

@Repository("morderMapper")
public interface MorderMapper extends BaseMapper<Morder> {

}
