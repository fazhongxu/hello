package com.xxl.hello.compiler;

import com.google.auto.service.AutoService;
import com.xxl.hello.annotation.Bind;
import com.xxl.hello.annotation.WXEntry;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * 自己实现ButterKnife注解处理器
 *
 * @author xxl.
 * @date 2022/8/16.
 */
@AutoService(Processor.class)
public class ButterKnifeCompiler extends BaseCompiler {

    private static final String TAG = "ButterKnifeCompiler ";

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println(TAG + "process");
        // 处理注解解析以及生成Java文件
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> hashSet = new LinkedHashSet<>();
        hashSet.add(Bind.class.getCanonicalName());
        return hashSet;
    }
}