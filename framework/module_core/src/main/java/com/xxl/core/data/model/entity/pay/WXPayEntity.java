package com.xxl.core.data.model.entity.pay;

import androidx.annotation.Keep;

/**
 * 微信支付的参数信息
 *
 * @author xxl.
 * @date 2023/3/1.
 */
@Keep
public class WXPayEntity {

    //region: 成员变量

    private String mPartnerId;

    private String mPrepayId;
    
    private String mPackageValue;
    
    private String mNonceStr;
    
    private String mTimeStamp;
    
    private String mSign;

    //endregion

    //region: 构造函数

    private WXPayEntity() {

    }

    public final static WXPayEntity obtain() {
        return new WXPayEntity();
    }

    //endregion

    //region: 提供方法

    public String getPartnerId() {
        return mPartnerId;
    }

    public void setPartnerId(String partnerId) {
        this.mPartnerId = partnerId;
    }

    public String getPrepayId() {
        return mPrepayId;
    }

    public void setPrepayId(String prepayId) {
        this.mPrepayId = prepayId;
    }

    public String getPackageValue() {
        return mPackageValue;
    }

    public void setPackageValue(String packageValue) {
        this.mPackageValue = packageValue;
    }

    public String getNonceStr() {
        return mNonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.mNonceStr = nonceStr;
    }

    public String getTimestamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.mTimeStamp = timeStamp;
    }

    public String getSign() {
        return mSign;
    }

    public void setSign(String sign) {
        this.mSign = sign;
    }
    
    //endregion

    //region: 内部辅助方法

    //endregion

}