package com.xxl.hello.service.data.local.db.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.NameInDb;
import io.objectbox.annotation.Unique;
import io.objectbox.relation.ToOne;

/**
 * 订单表
 *
 * @author xxl.
 * @date 2023/07/10.
 */
@Entity
@NameInDb("h_t_order")
public class OrderDBEntity extends BaseDBEntity<OrderDBEntity> {

    //region: 成员变量

    /**
     * 订单编号
     */
    @Unique
    @Index
    @NameInDb("order_no")
    private String orderNo;

    /**
     * 客户信息，一个订单信息对应一个客户，一个客户对应多个订单
     */
    private ToOne<CustomerDBEntity> customer;

    //endregion

    //region: 构造函数

    public OrderDBEntity() {

    }

    public static OrderDBEntity obtain() {
        return new OrderDBEntity();
    }

    //endregion

    //region: 提供方法

    /**
     * 获取订单编号
     *
     * @return
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 获取客户信息
     *
     * @return
     */
    public ToOne<CustomerDBEntity> getCustomer() {
        return customer;
    }

    /**
     * 设置订单编号
     *
     * @param orderNo
     * @return
     */
    public OrderDBEntity setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    /**
     * 设置客户信息
     *
     * @param customer
     * @return
     */
    public OrderDBEntity setCustomer(CustomerDBEntity customer) {
        this.customer.setTarget(customer);
        return this;
    }

    //endregion
}
