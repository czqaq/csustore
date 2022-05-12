package org.csu.store.controller;

import org.csu.store.BO.ProductBO;
import org.csu.store.common.CommonResponse;
import org.csu.store.domain.Product;
import org.csu.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/product/")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("putPro")
    public CommonResponse<String> putPro(@RequestBody ProductBO productbo ) {
        return productService.addProduct(productbo);
    }
    @PostMapping("/deletePro")
    public CommonResponse<String> deletePro(@RequestParam @Validated @NotBlank(message = "商品id不能为空") String proId){
        return productService.delectByproId(Integer.valueOf(proId));
    }
    @PostMapping("/updatePro")
    public CommonResponse<String> updatePro(@RequestBody Product product){
        product.setUpTime(LocalDateTime.now());
        return productService.updateProduct(product);
    }


}
