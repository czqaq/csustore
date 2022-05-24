package org.csu.store.controller;

import org.csu.store.common.CommonResponse;
import org.csu.store.domain.Cart;
import org.csu.store.domain.Product;
import org.csu.store.domain.User;
import org.csu.store.service.CartService;
import org.csu.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart/")
@Validated
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @PostMapping("get_cart")
    public CommonResponse<List<Product>> getCart(@RequestParam @Validated @NotBlank(message = "用户id不能为空") String userId) {
        List<Product> cartProduct = new ArrayList<>();
        Cart cart = cartService.getCart(Integer.valueOf(userId)).getData();

        System.out.println("购物车列表：" + cart);
        String[] proIds  = cart.getItems().split(",");
        for (String proId : proIds) {
            cartProduct.add(productService.searchProByProId(Integer.parseInt(proId)).getData());
        }

        return CommonResponse.createForSuccess(cartProduct);
    }

    @PostMapping("add_to_cart")
    public CommonResponse<String> addToCart(@RequestParam @Validated @NotBlank(message = "用户id不能为空") String userId,
                                            @RequestParam @Validated @NotBlank(message = "商品id不能为空") String proId) {
        return cartService.addToCart(Integer.valueOf(userId), Integer.valueOf(proId));
    }

    @PostMapping("delete_from_cart")
    public CommonResponse<String> deleteFromCart(@RequestParam @Validated @NotBlank(message = "用户id不能为空") String userId,
                                                 @RequestParam @Validated @NotBlank(message = "商品id不能为空") String proId) {
        return cartService.deleteFromCart(Integer.valueOf(userId), Integer.valueOf(proId));
    }

}
