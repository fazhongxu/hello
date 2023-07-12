package com.xxl.hello.service.data.local.db.api;

import com.xxl.hello.service.data.local.db.entity.OrderDBEntity;

import java.util.List;

/**
 * 订单数据库服务
 *
 * @Author: xxl
 * @Date: 2023/07/12 11:36 PM
 **/
public interface OrderDBDataService {

    //region: 与订单信息相关

    /**
     * 获取订单信息
     *
     * @param orderNo
     * @return
     */
    OrderDBEntity getOrderDBEntity(final String orderNo);

    /**
     * 获取订单数据
     *
     * @param page
     * @param pageSize
     * @return
     */
    List<OrderDBEntity> getOrderDBEntities(final int page,
                                           final int pageSize);

    //endregion
}
