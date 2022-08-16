package com.xxl.hello.compiler;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * 注解处理器
 * reference https://www.jianshu.com/p/07ef8ba80562
 *
 * @author xxl.
 * @date 2022/8/16.
 */
public abstract class BaseCompiler extends AbstractProcessor {

    private static final String TAG = "BaseCompiler ";

    protected Types mTypes;
    protected Elements mElements;
    protected Filer mFiler;
    private Messager mMessager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        System.out.println(TAG + "init");
        mTypes = processingEnvironment.getTypeUtils();
        mElements = processingEnvironment.getElementUtils();
        mFiler = processingEnvironment.getFiler();
        mMessager = processingEnvironment.getMessager();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}