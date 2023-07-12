package com.xxl.hello.service.data.local.db.impl.objectbox;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.local.db.api.OrderDBDataService;
import com.xxl.hello.service.data.local.db.entity.OrderDBEntity;
import com.xxl.hello.service.data.local.db.entity.OrderDBEntity_;

import java.util.List;

import io.objectbox.Property;

/**
 * 订单数据库服务
 * 实现{@link OrderDBDataService} 针对 {@link OrderDBEntity} 数据表操作服务
 *
 * @Author: xxl
 * @Date: 2023/07/12 11:36 PM
 **/
public class OrderObjectBoxDataSource extends BaseObjectBoxDataSource<OrderDBEntity> implements OrderDBDataService {

    //region: 构造函数

    public OrderObjectBoxDataSource(@NonNull final ObjectBoxDBClientKit objectBoxDBClientKit) {
        super(objectBoxDBClientKit);
    }

    //endregion

    //region: 生命周期

    /**
     * 获取数据表主键ID
     *
     * @return
     */
    @Override
    public Property<OrderDBEntity> getPrimaryKey() {
        return OrderDBEntity_.orderNo;
    }

    //endregion

    //region: 与订单信息相关

    /**
     * 获取订单信息
     *
     * @param orderNo
     * @return
     */
    @Override
    public OrderDBEntity getOrderDBEntity(final String orderNo) {
        return get(orderNo);
    }

    /**
     * 获取订单数据
     *
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<OrderDBEntity> getOrderDBEntities(final int page,
                                                  final int pageSize) {
        final long offset = (page - 1) * pageSize;
        return getOperateBox()
                .query()
                .build()
                .find(offset, pageSize);
    }

    //region: 与订单信息相关
}
