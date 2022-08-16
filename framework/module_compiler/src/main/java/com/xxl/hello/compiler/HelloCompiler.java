package com.xxl.hello.compiler;

import com.google.auto.service.AutoService;
import com.xxl.hello.annotation.TestCompiler;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * 注解处理器
 *
 * @author xxl.
 * @date 2022/8/16.
 */
@AutoService(Processor.class)
public class HelloCompiler extends BaseCompiler {

    private static final String TAG = "HelloCompiler ";

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println(TAG + "process");
        // 处理注解解析以及生成Java文件
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> hashSet = new LinkedHashSet<>();
        hashSet.add(TestCompiler.class.getCanonicalName());
        return hashSet;
    }
}