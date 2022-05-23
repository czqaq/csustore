package org.csu.store.service;


import org.csu.store.BO.ProductBO;
import org.csu.store.common.CommonResponse;
import org.csu.store.domain.Product;

import java.util.List;

public interface ProductService {


    CommonResponse<String> addProduct(ProductBO productBO);

    CommonResponse<Product> searchProByProId(Integer proId);

    CommonResponse<List<Product>>  searchProByKeyWord(String KeyWord);

    CommonResponse<List<Product>>  searchProByType(String type);

    CommonResponse<List<Product>>  searchProByUserId(Integer sellerId);

    CommonResponse<String> delectProByProId(Integer proId);

    CommonResponse<String> updatePro(Product product);

    CommonResponse<List<Product>> selectLastRecord(Integer num);




}
