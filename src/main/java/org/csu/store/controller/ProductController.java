package org.csu.store.controller;

import org.csu.store.BO.ProductBO;
import org.csu.store.common.CommonResponse;
import org.csu.store.domain.Buyproduct;
import org.csu.store.domain.Morder;
import org.csu.store.domain.Product;
import org.csu.store.service.MorderService;
import org.csu.store.service.ProductService;
import org.csu.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product/")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private MorderService morderService;

    @Autowired
    private UserService userService;

    @PostMapping("put_Product")
    public CommonResponse<String> putPro(@RequestBody ProductBO productbo) {
        return productService.addProduct(productbo);
    }

    @PostMapping("/delete_Product")
    public CommonResponse<String> deletePro(@RequestParam @Validated @NotBlank(message = "商品id不能为空") String proId) {
        return productService.delectProByProId(Integer.valueOf(proId));
    }

    @PostMapping("/update_Product")
    public CommonResponse<String> updatePro(@RequestBody Product product) {
        product.setUpTime(LocalDateTime.now());
        return productService.updatePro(product);
    }

    @PostMapping("/get_Product_Info_By_Id")
    public CommonResponse<Product> searchById(@RequestParam @Validated @NotBlank(message = "商品id不能为空") String proId) {
        return productService.searchProByProId(Integer.valueOf(proId));
    }

    @PostMapping("/get_Product_By_KeyWord")
    public CommonResponse<List<Product>> searchProByWord(@RequestParam @Validated @NotBlank(message = "关键字不能为空") String keyWord) {
        return productService.searchProByKeyWord(keyWord);
    }

    @PostMapping("/get_Product_By_Type")
    public CommonResponse<List<Product>> searchProByType(@RequestParam @Validated @NotBlank(message = "商品类型不能为空") String type) {
        return productService.searchProByType(type);
    }

    @PostMapping("/get_My_Sell")//我的在售，用户作为卖家
    public CommonResponse<List<Product>> searchProBySellerId(@RequestParam @Validated @NotBlank(message = "卖家id不能为空") String sellerId) {
        return productService.searchProByUserId(Integer.valueOf(sellerId));
    }

    @PostMapping("/get_My_Buyed")
    public CommonResponse<List<Buyproduct>> searchBuyedPro(@RequestParam @Validated @NotBlank(message = "卖家id不能为空") String userId) {
        if (morderService.searchBuyProById(Integer.valueOf(userId)).isSuccess()) {
            List<Morder> mordersList = morderService.searchBuyProById(Integer.valueOf(userId)).getData();
            String username = userService.getUserDetail(Integer.valueOf(userId)).getData().getUsername();
            List<Buyproduct> Buyproductlist = new ArrayList<>();
            for (Morder m : mordersList) {//循环商品编号
                Product product = productService.searchProByProId(m.getProId()).getData();
                Buyproduct bp = new Buyproduct();
                bp.setProId(product.getId());
                bp.setUserName(username);
                bp.setTypeName(product.getType());
                bp.setProTitle(product.getTitle());
                bp.setProDetail(product.getDetail());
                bp.setImageUrl(product.getImgUrl());
                bp.setProTime(product.getUpTime());
                bp.setProPrice(product.getPrice());
                bp.setOrdTime(m.getTime());
                bp.setNum(product.getNum());
                Buyproductlist.add(bp);
            }
            return CommonResponse.createForSuccess(Buyproductlist);
        }
        return CommonResponse.createForError("用户购买信息查询失败");

    }

    @GetMapping("/index_Pro_List")
    public CommonResponse<List<Product>> random() {
        return productService.selectLastRecord(5);
    }


}
