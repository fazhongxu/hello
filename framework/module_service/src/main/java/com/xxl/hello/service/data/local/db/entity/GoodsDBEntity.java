package com.xxl.hello.service.data.local.db.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.NameInDb;
import io.objectbox.annotation.Unique;

/**
 * 产品表
 *
 * @author xxl.
 * @date 2023/07/10.
 */
@Entity
@NameInDb("h_t_goods")
public class GoodsDBEntity extends BaseDBEntity<GoodsDBEntity> {

    //region: 成员变量

    /**
     * 产品ID
     */
    @Unique
    @Index
    @NameInDb("goods_id")
    private String goodsId;

    /**
     * 产品名称
     */
    @NameInDb("good_name")
    private String goodName;

    //endregion

    //region: 构造函数

    public GoodsDBEntity() {

    }

    public static GoodsDBEntity obtain() {
        return new GoodsDBEntity();
    }

    //endregion

    //region: 提供方法

    /**
     * 获取产品ID
     *
     * @return
     */
    public String getGoodsId() {
        return goodsId;
    }

    /**
     * 获取产品名称
     *
     * @return
     */
    public String getGoodName() {
        return goodName;
    }

    /**
     * 设置产品ID
     *
     * @param goodsId
     * @return
     */
    public GoodsDBEntity setGoodsId(String goodsId) {
        this.goodsId = goodsId;
        return this;
    }


    //endregion
}
