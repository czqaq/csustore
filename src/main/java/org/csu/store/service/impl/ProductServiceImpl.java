package org.csu.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;


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
        product.setNum(productBO.getNum());
        int row=productMapper.insert(product);
        if (row ==1) {
            return CommonResponse.createForSuccessMessage("添加商品信息成功");
        }
        return CommonResponse.createForError("添加商品信息失败");
    }

    @Override
    public CommonResponse<Product> searchProByProId(Integer proId) {
        Product product = productMapper.selectById(proId);
        if (product== null){
            return CommonResponse.createForError("查询商品失败");
        }
        return CommonResponse.createForSuccess(product);
    }

    @Override
    public CommonResponse<List<Product>> searchProByKeyWord(String KeyWord) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", KeyWord);
        List<Product> list = productMapper.selectList(queryWrapper);
        if (!list.isEmpty()){
            return CommonResponse.createForSuccess(list);
        }
            return CommonResponse.createForError("查询出错");
    }

    @Override
    public CommonResponse<List<Product>> searchProByType(String type) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        //查询名字为 Tom 的一条记录
        queryWrapper.eq("type",type);
        List<Product> list= productMapper.selectList(queryWrapper);
        if (!list.isEmpty()){
            return CommonResponse.createForSuccess(list);
        }
        return CommonResponse.createForError("查询出错");
    }

    @Override
    public CommonResponse<List<Product>> searchProByUserId(Integer sellerId) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        //查询名字为 Tom 的一条记录
        queryWrapper.eq("seller_id",sellerId);
        List<Product> list= productMapper.selectList(queryWrapper);
        if (!list.isEmpty()){
            return CommonResponse.createForSuccess(list);
        }
        return CommonResponse.createForError("用户无在售商品信息");

    }

    @Override
    public CommonResponse<String> delectProByProId(Integer proId) {
       int row= productMapper.deleteById(proId);
       if(row==1){
           return CommonResponse.createForSuccessMessage("删除商品成功");
       }
        return CommonResponse.createForError("删除商品失败");
    }

    @Override
    public CommonResponse<String> updatePro(Product product) {
        int row = productMapper.updateById(product);
        if(row==1){
            return CommonResponse.createForSuccessMessage("修改成功");
        }
        return CommonResponse.createForError("修改商品失败");
    }

    @Override
    public CommonResponse<List<Product>> selectLastRecord(Integer num) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        //查询名字为 Tom 的一条记录
        queryWrapper.gt("num",num);
        List<Product> list= productMapper.selectList(queryWrapper);
        if (!list.isEmpty()){
            return CommonResponse.createForSuccess(list);
        }
        return CommonResponse.createForError("无商品");
    }


}
