package com.xxl.hello.service.data.local.db.entity;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.NameInDb;
import io.objectbox.annotation.Unique;
import io.objectbox.relation.ToMany;

/**
 * 客户表
 *
 * @author xxl.
 * @date 2023/07/10.
 */
@Entity
@NameInDb("h_t_customer")
public class CustomerDBEntity extends BaseDBEntity<CustomerDBEntity> {

    //region: 成员变量

    /**
     * 客户ID
     */
    @Unique
    @Index
    @NameInDb("customer_id")
    private String customerId;

    /**
     * 客户用户ID
     */
    @NameInDb("customer_uid")
    private String customerUserId;

    /**
     * 客户用户昵称
     */
    @NameInDb("customer_nickname")
    private String customerNickname;

    /**
     * 订单信息，一个订单信息对应一个客户，一个客户对应多个订单
     */
    @Backlink(to = "customer")
    private ToMany<OrderDBEntity> order;

    //endregion

    //region: 构造函数

    public CustomerDBEntity() {

    }

    public static CustomerDBEntity obtain() {
        return new CustomerDBEntity();
    }

    //endregion

    //region: 提供方法

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerUserId() {
        return customerUserId;
    }

    public String getCustomerNickname() {
        return customerNickname;
    }

    public ToMany<OrderDBEntity> getOrder() {
        return order;
    }

    //endregion
}
