package com.xxl.hello.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.xxl.hello.annotation.WXEntry;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * 微信回调页注解处理器
 *
 * @author xxl.
 * @date 2022/8/16.
 */
@AutoService(Processor.class)
public class WXEntryCompiler extends BaseCompiler {

    private static final String TAG = "WXEntryCompiler ";

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println(TAG + "process");
        // 处理注解解析以及生成Java文件
        processWXCodeGeneration(roundEnvironment);
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> hashSet = new LinkedHashSet<>();
        hashSet.add(WXEntry.class.getCanonicalName());
        return hashSet;
    }

    /**
     * 生成微信相关代码
     *
     * @param roundEnvironment
     */
    private void processWXCodeGeneration(RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(WXEntry.class);
        for (Element element : elements) {
            if (element instanceof TypeElement) {
                WXEntry annotation = element.getAnnotation(WXEntry.class);
                System.out.println(TAG + "processWXCodeGeneration " + element.toString() + "  " + annotation);
                if (annotation != null) {
                    ClassName supperClass = null;
                    try {
                        String parentClassName = annotation.parentClassName();
                        int index = parentClassName.lastIndexOf(".");
                        supperClass = ClassName.get(parentClassName.substring(0, index), parentClassName.substring(index + 1));
                    } catch (Exception e) {
                        error(element, "找不到类%s" + e.getMessage());
                        return;
                    }

                    TypeSpec classBuilder = TypeSpec.classBuilder("WXEntryActivity")
                            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                            .superclass(supperClass)
                            .build();

                    try {
                        String packageName = String.format("%s%s", annotation.packageName(), ".wxapi");
                        JavaFile.builder(packageName, classBuilder)
                                .build()
                                .writeTo(mFiler);
                        System.out.println(TAG + "processWXCodeGeneration success");
                    } catch (Exception e) {
                        error(null, "生成微信回调文件报错 %s" + e.getMessage());
                    }
                }
            }

        }
    }
}