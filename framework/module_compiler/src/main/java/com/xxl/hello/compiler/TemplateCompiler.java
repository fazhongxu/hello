package com.xxl.hello.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.xxl.hello.annotation.Template;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * mvvm 模板代码自动创建注解处理器
 *
 * @author xxl.
 * @date 2022/8/29.
 */
@AutoService(Processor.class)
public class TemplateCompiler extends BaseCompiler {

    private static final String TAG = "TemplateCompiler ";

    private static final String CURRENT_DATE = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println(TAG + "process");
        if (!checkParams(roundEnvironment)) {
            return false;
        }
        processNavigatorCodeGeneration(roundEnvironment);
        processViewModelCodeGeneration(roundEnvironment);
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final Set<String> set = new HashSet<>();
        set.add(Template.class.getCanonicalName());
        return set;
    }

    /**
     * 处理Navigator 代码生成
     *
     * @param roundEnvironment
     */
    private void processNavigatorCodeGeneration(RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Template.class);
        for (Element element : elements) {
            if (element instanceof TypeElement) {
                final Template template = element.getAnnotation(Template.class);
                final String navigator = String.format("%sNavigator", template.name());
                final ClassName navigatorClassName = ClassName.get(template.packageName(), navigator);

                TypeSpec typeSpec = TypeSpec.interfaceBuilder(navigatorClassName)
                        .addModifiers(Modifier.PUBLIC)
                        .addJavadoc(buildTypeJavadoc(template.author(), template.description()))
                        .build();
                try {
                    JavaFile.builder(template.packageName(), typeSpec)
                            .build()
                            .writeTo(mFiler);
                    System.out.println(String.format(TAG + "process navigator %s success", navigator));
                } catch (Exception e) {
                    error(null, "generate template navigator failure %s" + e.getMessage());
                }
            }
        }
    }

    /**
     * 处理ViewModel 代码生成
     *
     * @param roundEnvironment
     */
    private void processViewModelCodeGeneration(RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Template.class);
        for (Element element : elements) {
            if (element instanceof TypeElement) {
                final Template template = element.getAnnotation(Template.class);

                final String viewModelClassName = String.format("%sViewModel", template.name());
                final String navigator = String.format("%sNavigator", template.name());

                final ClassName navigatorClassName = ClassName.get(template.packageName(), navigator);
                final ClassName baseViewModelClassName = ClassName.get("com.xxl.core.ui", "BaseViewModel");

                final ParameterizedTypeName baseViewModelTypeName = ParameterizedTypeName.get(baseViewModelClassName, navigatorClassName);

                final ClassName applicationClassName = ClassName.get("android.app", "Application");
                final String applicationParameterName = "application";

                MethodSpec methodSpec = MethodSpec.constructorBuilder()
                        .addParameter(applicationClassName, applicationParameterName)
                        .addModifiers(Modifier.PUBLIC)
                        .addStatement("super($N)", applicationParameterName)
                        .build();

                TypeSpec viewModelTypeSpec = TypeSpec.classBuilder(viewModelClassName)
                        .addModifiers(Modifier.PUBLIC)
                        .superclass(baseViewModelTypeName)
                        .addMethod(methodSpec)
                        .addJavadoc(buildTypeJavadoc(template.author(), template.description() + "数据模型"))
                        .build();

                try {
                    JavaFile.builder(template.packageName(), viewModelTypeSpec)
                            .build()
                            .writeTo(mFiler);
                    System.out.println(String.format(TAG + "process %s success", viewModelClassName));
                } catch (Exception e) {
                    error(null, "generate template viewModel failure %s" + e.getMessage());
                }
            }
        }
    }

    /**
     * 构建类的JavaDoc文档
     *
     * @param author
     * @param description
     * @return
     */
    private String buildTypeJavadoc(String author,
                                    String description) {
        return String.format("%s\n\n@author %s\n@date %s", description, author, CURRENT_DATE);
    }

    /**
     * 检查参数
     *
     * @param roundEnvironment
     * @return
     */
    private boolean checkParams(RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Template.class);
        for (Element element : elements) {
            if (element instanceof TypeElement) {
                final Template template = element.getAnnotation(Template.class);
                if (template == null) {
                    return false;
                }
                if (template.packageName() == null || "".equals(template.packageName())) {
                    error(element, "Template packageName cannot be empty!!!");
                    return false;
                }
                if (template.name() == null || "".equals(template.name())) {
                    error(element, "Template name cannot be empty!!!");
                    return false;
                }
            }
        }
        return true;
    }


}