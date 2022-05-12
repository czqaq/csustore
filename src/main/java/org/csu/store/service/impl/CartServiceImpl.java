package org.csu.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.csu.store.common.CommonResponse;
import org.csu.store.domain.Cart;
import org.csu.store.domain.User;
import org.csu.store.persistence.CartMapper;
import org.csu.store.persistence.MessageMapper;
import org.csu.store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service("cartService")
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;


    @Override
    public CommonResponse<Cart> getCart(Integer userId) {
        Cart cart = cartMapper.selectOne(
                Wrappers.<Cart>query().eq("user_id", userId));
        if (cart == null) {
            return CommonResponse.createForError("用户没有创建购物车");
        }
        return CommonResponse.createForSuccess(cart);
    }

    @Override
    public CommonResponse<String> addToCart(Integer userId, Integer proId) {
        Cart cart = cartMapper.selectOne(
                Wrappers.<Cart>query().eq("user_id", userId));
        String items = "";
        // 用户没有购物车，新建购物车
        if (cart == null) {
            Cart initCart = new Cart();
            initCart.setUserId(userId);
            initCart.setItems(String.valueOf(proId));
            initCart.setUpdateTime(LocalDateTime.now());
            int rows = cartMapper.insert(initCart);
            if (rows == 0) {
                return CommonResponse.createForError("用户新建购物车失败");
            }
            return CommonResponse.createForSuccessMessage("商品添加成功");
        }else{
            // 用户之前新建的购物车内的商品都清空
            if (cart.getItems().equals(items)) {
                items = String.valueOf(proId);
            } else {
                items = cart.getItems() + ',' + String.valueOf(proId);
            }
            cart.setItems(items);
            UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("user_id", userId);
            int rows = cartMapper.update(cart, updateWrapper);
            if (rows > 0) {
                return CommonResponse.createForSuccessMessage("商品添加成功");
            }
        }
        return CommonResponse.createForError("商品添加失败");
    }

    @Override
    public CommonResponse<String> deleteFromCart(Integer userId, Integer proId) {
        Cart cart = cartMapper.selectOne(
                Wrappers.<Cart>query().eq("user_id", userId));
        String items = "";
        StringBuilder updateItems = new StringBuilder();
        boolean hasDelete = false;
        if (cart.getItems().equals("")) {
            return CommonResponse.createForError("用户购物车为空，删除失败");
        } else {
            items = cart.getItems();
        }
        String[] splitStr = items.split(",");

        for (String s : splitStr) {
            // 找到第一个匹配的proId
            if (!hasDelete && s.equals(String.valueOf(proId))) {
                System.out.println("匹配第一个proId: " + s);
                hasDelete = true;
                continue;
            } else if (!hasDelete && !s.equals(String.valueOf(proId))) {
                if (updateItems.toString().equals("")) {
                    System.out.println("添加第一个item");
                    updateItems = new StringBuilder(s);
                } else {
                    System.out.println("修改items: " + proId);
                    updateItems.append(",").append(s);
                }
            }

            if (hasDelete) {
                if (updateItems.toString().equals("")) {
                    System.out.println("添加第一个item");
                    updateItems = new StringBuilder(s);
                } else {
                    System.out.println("修改items: " + proId);
                    updateItems.append(",").append(s);
                }
            }
        }
        System.out.println("拼接结束");

        cart.setItems(updateItems.toString());
        UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId);
        int rows = cartMapper.update(cart, updateWrapper);
        if (rows > 0) {
            return CommonResponse.createForSuccessMessage("从购物车中删除商品成功");
        }
        return CommonResponse.createForError("从购物车中删除商品失败");
    }
}
