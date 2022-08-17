package com.xxl.hello.compiler;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * 注解处理器
 * <p>
 * reference
 * https://www.jianshu.com/p/07ef8ba80562
 * https://www.jianshu.com/p/18b20f82db78?utm_campaign=haruki&utm_content=note&utm_medium=reader_share&utm_source=weixin
 * https://blog.csdn.net/qq_45295475/article/details/121595963
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

    public void error(Element element, String message, Object... args) {
        printMessage(Diagnostic.Kind.ERROR, element, message, args);
    }

    public void waring(Element element, String message, Object... args) {
        printMessage(Diagnostic.Kind.WARNING, element, message, args);
    }

    public void note(Element element, String message, Object... args) {
        printMessage(Diagnostic.Kind.NOTE, element, message, args);
    }

    public void printMessage(Diagnostic.Kind kind, Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        mMessager.printMessage(kind, message, element);
    }

}