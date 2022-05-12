package org.csu.store.service;

import org.csu.store.common.CommonResponse;
import org.csu.store.domain.Cart;

public interface CartService {

    public CommonResponse<Cart> getCart(Integer userId);

    public CommonResponse<String> addToCart(Integer userId, Integer proId);

    public CommonResponse<String> deleteFromCart(Integer userId, Integer proId);
}
