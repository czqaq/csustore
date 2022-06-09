package org.csu.store.controller;

import org.csu.store.VO.CartProductVO;
import org.csu.store.common.CommonResponse;
import org.csu.store.domain.Cart;
import org.csu.store.domain.Product;
import org.csu.store.domain.User;
import org.csu.store.service.CartService;
import org.csu.store.service.ProductService;
import org.csu.store.service.UserService;
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
    @Autowired
    private UserService userService;

    @PostMapping("get_cart")
    public CommonResponse<List<CartProductVO>> getCart(@RequestParam @Validated @NotBlank(message = "用户id不能为空") String userId) {
        List<CartProductVO> cartProduct = new ArrayList<>();
        Cart cart = cartService.getCart(Integer.valueOf(userId)).getData();
        String items = "";
        try {
            items = cart.getItems();
        } catch (NullPointerException e) {
//            e.printStackTrace();
        }
        if (!items.equals("")) {
            System.out.println("购物车列表：" + cart);
            String[] proIds = cart.getItems().split(",");
            for (String proId : proIds) {
                CartProductVO item = new CartProductVO();
                Product product = productService.searchProByProId(Integer.parseInt(proId)).getData();
                item.setId(product.getId());
                item.setDetail(product.getDetail());
                item.setImgUrl(product.getImgUrl());
                item.setPrice(product.getPrice());
                item.setTitle(product.getTitle());
                item.setType(product.getType());
                item.setUpTime(product.getUpTime());
                String sellerName = userService.getUserDetail(product.getSellerId()).getData().getUsername();
                item.setSellerName(sellerName);
                cartProduct.add(item);
            }
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
        String[] deleteList = proId.split(",");
        for (int i = 0; i < deleteList.length - 1; i++) {
            cartService.deleteFromCart(Integer.valueOf(userId), Integer.valueOf(deleteList[i]));
        }
        return cartService.deleteFromCart(Integer.valueOf(userId), Integer.valueOf(deleteList[deleteList.length - 1]));
    }

}
