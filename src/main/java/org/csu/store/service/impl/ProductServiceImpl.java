package org.csu.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.csu.store.BO.ProductBO;
import org.csu.store.common.CommonResponse;
import org.csu.store.domain.Product;
import org.csu.store.domain.User;
import org.csu.store.persistence.ProductMapper;
import org.csu.store.persistence.ProtypeMapper;
import org.csu.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

//    @Autowired
//    private ProtypeMapper protypeMapper;

    @Override
    public CommonResponse<String> addProduct(ProductBO productBO) {
        Product product = new Product();
        product.setDetail(productBO.getDetail());
        product.setTitle(productBO.getTitle());
        product.setSellerId(productBO.getSellerId());
        product.setType(productBO.getType());
        product.setPrice(productBO.getPrice());
        product.setImgUrl(productBO.getImgUrl());
        product.setUpTime(LocalDateTime.now());
        int row=productMapper.insert(product);
        if (row ==1) {
            return CommonResponse.createForSuccessMessage("添加商品信息成功");
        }
        return CommonResponse.createForError("添加商品信息失败");
    }

    @Override
    public CommonResponse<Product> selectByproId(Integer proId) {
        Product product = productMapper.selectById(proId);
        if (product== null){
            return CommonResponse.createForError("查询商品失败");
        }
        return CommonResponse.createForSuccess(product);
    }

    @Override
    public CommonResponse<String> delectByproId(Integer proId) {
       int row= productMapper.deleteById(proId);
       if(row==1){
           return CommonResponse.createForSuccessMessage("删除商品成功");
       }
        return CommonResponse.createForError("删除商品失败");
    }

    @Override
    public CommonResponse<String> updateProduct(Product product) {

//        UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.eq("id", product.getId());
//        updateWrapper.set("title", product.getTitle());
//        updateWrapper.set("sellerId", product.getSellerId());
//        updateWrapper.set("type", product.getType());
//        updateWrapper.set("imgUrl", product.getImgUrl());
//        updateWrapper.set("detail", product.getDetail());
//        updateWrapper.set("upTime", product.getUpTime());
//        int rows = productMapper.update(user, updateWrapper);
          int row = productMapper.updateById(product);
        if(row==1){
            return CommonResponse.createForSuccessMessage("修改成功");
        }
        return CommonResponse.createForError("修改商品失败");





    }


}
