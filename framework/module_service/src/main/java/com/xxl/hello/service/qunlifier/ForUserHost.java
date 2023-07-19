package com.xxl.hello.service.qunlifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * 用户网络请求地址标识
 * 作用：每个模块都有单独的服务器主机地址，用户模块的主机地址标识，如果整个项目只有一个主机地址，则使用 {@link ForHost}即可
 * 如果还有上传多媒体文件信息主机地址不一样，如使用七牛上传图片，视频则需要写一个标识标记返回骑牛主机地址
 *
 * @author xxl.
 * @date 2021/7/23.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ForUserHost {

}