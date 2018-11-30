package com.nagi.rpc.service.impl;

import com.nagi.rpc.config.annotation.NagiService;
import com.nagi.rpc.service.OrderService;
@NagiService(interfaceName="com.nagi.rpc.service.OrderService")
public class OrderServiceImpl implements OrderService {

    public void order() {
        System.out.println("测试Order");
    }
}
