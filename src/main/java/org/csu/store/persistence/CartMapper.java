package org.csu.store.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.store.domain.Cart;
import org.springframework.stereotype.Repository;

@Repository("cartMapper")
public interface CartMapper extends BaseMapper<Cart> {
}
