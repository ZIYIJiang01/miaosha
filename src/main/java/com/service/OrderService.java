package com.service;

import com.error.BusinessException;
import com.service.model.OrderModel;

public interface OrderService {

    OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException;
}
