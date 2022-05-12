package org.csu.store.service;


import org.csu.store.BO.ProductBO;
import org.csu.store.common.CommonResponse;
import org.csu.store.domain.Product;

import java.util.List;

public interface ProductService {


    CommonResponse<String> addProduct(ProductBO productBO);

    CommonResponse<Product> selectByproId(Integer proId);


    CommonResponse<String> delectByproId(Integer proId);


    CommonResponse<String> updateProduct(Product product);

//    CommonResponse<String> addProType(Product product);
}
