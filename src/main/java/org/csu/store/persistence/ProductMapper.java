package org.csu.store.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.store.domain.Product;
import org.springframework.stereotype.Repository;

@Repository("productMapper")
public interface ProductMapper extends BaseMapper<Product> {
}
