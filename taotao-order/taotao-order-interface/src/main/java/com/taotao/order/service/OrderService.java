package com.taotao.order.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.order.pojo.OrderInfo;

/**
 * Created by 李壮壮 on 2018/9/13.
 */
public interface OrderService {
    TaotaoResult createOrder(OrderInfo orderInfo);
}
