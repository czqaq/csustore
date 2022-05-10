package org.csu.store.service.impl;

import org.csu.store.persistence.MessageMapper;
import org.csu.store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("cartService")
public class CartServiceImpl implements CartService {
    @Autowired
    private MessageMapper messageMapper;


}
