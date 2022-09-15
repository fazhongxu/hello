package com.xxl.hello.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.xxl.hello.annotation.Template;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println(TAG + "process");
        if (!checkParams(roundEnvironment)) {
            return false;
        }
        processCodeGeneration(roundEnvironment);
        return false;
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final Set<String> set = new HashSet<>();
        set.add(Template.class.getCanonicalName());
        return set;
    }

    /**
     * 处理代码生成
     *
     * @param roundEnvironment
     */
    private void processCodeGeneration(RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Template.class);
        for (Element element : elements) {
            if (element instanceof TypeElement) {
                final Template template = element.getAnnotation(Template.class);
                final ClassName fragment = processFragmentCodeGeneration(roundEnvironment, template);
                processActivityCodeGeneration(roundEnvironment, template, fragment);
                processProviderCodeGeneration(roundEnvironment, template);
                processModuleCodeGeneration(roundEnvironment, template);
                processNavigatorCodeGeneration(roundEnvironment, template);
                processViewModelCodeGeneration(roundEnvironment, template);
            }
        }
    }

    /**
     * 处理Fragment 代码生成
     *
     * @param roundEnvironment
     * @return
     */
    private ClassName processFragmentCodeGeneration(RoundEnvironment roundEnvironment,
                                                    Template template) {
        final String fragment = String.format("%sFragment", template.name());
        final ClassName fragmentClassName = ClassName.get(template.packageName(), fragment);

        MethodSpec createFragmentMethodSpec = MethodSpec.methodBuilder("newInstance")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .returns(fragmentClassName)
                .addStatement("return new $T()", fragmentClassName)
                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder(fragmentClassName)
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc(buildTypeJavadoc(template.author(), template.description()))
                .addMethod(createFragmentMethodSpec)
                .superclass(ClassName.get("androidx.fragment.app", "Fragment"))
                .build();
        try {
            JavaFile.builder(template.packageName(), typeSpec)
                    .build()
                    .writeTo(mFiler);
            System.out.println(String.format(TAG + "process fragment %s success", fragment));
        } catch (Exception e) {
            error(null, "generate template fragment failure %s" + e.getMessage());
        }
        return fragmentClassName;
    }

    /**
     * 处理Activity 代码生成
     *
     * @param roundEnvironment
     * @param template
     * @param fragment
     */
    private void processActivityCodeGeneration(RoundEnvironment roundEnvironment,
                                               Template template,
                                               ClassName fragment) {
        final String activity = String.format("%sActivity", template.name());
        final ClassName activityClassName = ClassName.get(template.packageName(), activity);
        final ClassName parentActivityClassName = ClassName.get("com.xxl.core.ui.activity", "SingleFragmentBarActivity");

        MethodSpec createFragmentMethodSpec = MethodSpec.methodBuilder("createFragment")
                .addAnnotation(ClassName.get(Override.class))
                .addModifiers(Modifier.PUBLIC)
                .returns(fragment)
                .addStatement("return $T.newInstance()", fragment)
                .build();

        MethodSpec titleMethodSpec = MethodSpec.methodBuilder("getToolbarTitle")
                .addAnnotation(ClassName.get(Override.class))
                .addModifiers(Modifier.PUBLIC)
                .returns(Integer.TYPE)
                .addStatement("return 0")
                .build();

        final List<MethodSpec> methodSpecs = new ArrayList<>();
        methodSpecs.add(createFragmentMethodSpec);
        methodSpecs.add(titleMethodSpec);

        TypeSpec typeSpec = TypeSpec.classBuilder(activityClassName)
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc(buildTypeJavadoc(template.author(), template.description()))
                .superclass(parentActivityClassName)
                .addMethods(methodSpecs)
                .build();
        try {
            JavaFile.builder(template.packageName(), typeSpec)
                    .build()
                    .writeTo(mFiler);
            System.out.println(String.format(TAG + "process activity %s success", activity));
        } catch (Exception e) {
            error(null, "generate template activity failure %s" + e.getMessage());
        }
    }

    /**
     * 处理Provider代码生成
     *
     * @param roundEnvironment
     */
    private void processProviderCodeGeneration(RoundEnvironment roundEnvironment,
                                               Template template) {
        final String module = String.format("%sFragmentModule", template.name());

        final String provider = String.format("%sFragmentProvider", template.name());
        final String fragmentName = String.format("%sFragment", template.name());
        final ClassName fragment = ClassName.get(template.packageName(), fragmentName);
        final ClassName providerClassName = ClassName.get(template.packageName(), provider);

        final AnnotationSpec fragmentAnnotationSpec = AnnotationSpec.builder(ClassName.get("dagger.android", "ContributesAndroidInjector"))
                .addMember("modules", String.format("%s.class", module))
                .build();

        MethodSpec fragmentMethodSpec = MethodSpec.methodBuilder(String.format("bind%sFactory", fragmentName))
                .addModifiers(Modifier.ABSTRACT)
                .addAnnotation(fragmentAnnotationSpec)
                .returns(fragment)
                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder(providerClassName)
                .addAnnotation(buildDaggerModuleAnnotation())
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addJavadoc(buildTypeJavadoc(template.author(), template.description()))
                .addMethod(fragmentMethodSpec)
                .build();

        try {
            JavaFile.builder(template.packageName(), typeSpec)
                    .build()
                    .writeTo(mFiler);
            System.out.println(String.format(TAG + "process provider %s success", provider));
        } catch (Exception e) {
            error(null, "generate template provider failure %s" + e.getMessage());
        }
    }


    /**
     * 处理Module代码生成
     *
     * @param roundEnvironment
     */
    private void processModuleCodeGeneration(RoundEnvironment roundEnvironment,
                                             Template template) {
        final String module = String.format("%sFragmentModule", template.name());
        final ClassName moduleClassName = ClassName.get(template.packageName(), module);

        final ClassName modelClassName = ClassName.get(template.packageName(), String.format("%sViewModel", template.name()));
        final String viewModel = "viewModel";

        final ClassName applicationClassName = ClassName.get("android.app", "Application");
        final String applicationParameterName = "application";

        final ClassName dataRepositoryKitClassName = buildDataRepositoryKit();
        final String dataRepositoryKitParameterName = "dataRepositoryKit";

        MethodSpec provideViewModelMethodSpec = MethodSpec.methodBuilder(String.format("provide%sViewModel", template.name()))
                .addAnnotation(buildDaggerProvidesAnnotation())
                .addParameter(ParameterSpec.builder(applicationClassName, applicationParameterName).addAnnotation(buildNonNullAnnotation()).addModifiers(Modifier.FINAL).build())
                .addParameter(ParameterSpec.builder(dataRepositoryKitClassName, dataRepositoryKitParameterName).addAnnotation(buildNonNullAnnotation()).addModifiers(Modifier.FINAL).build())
                .returns(modelClassName)
                .addStatement("return new $T($L,$L)", modelClassName, applicationParameterName, dataRepositoryKitParameterName)
                .build();

        MethodSpec provideViewModelMethodFactorySpec = MethodSpec.methodBuilder(String.format("provide%sViewModelFactory", template.name()))
                .addAnnotation(buildDaggerProvidesAnnotation())
                .addParameter(ParameterSpec.builder(modelClassName, viewModel).addAnnotation(buildNonNullAnnotation()).addModifiers(Modifier.FINAL).build())
                .returns(ClassName.get("androidx.lifecycle.ViewModelProvider", "Factory"))
                .addStatement("return new $T<>($L)", ClassName.get("com.xxl.core.ui", "ViewModelProviderFactory"), viewModel)
                .build();

        final List<MethodSpec> methodSpecs = new ArrayList<>();
        methodSpecs.add(provideViewModelMethodSpec);
        methodSpecs.add(provideViewModelMethodFactorySpec);

        TypeSpec typeSpec = TypeSpec.classBuilder(moduleClassName)
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc(buildTypeJavadoc(template.author(), template.description()))
                .addAnnotation(buildDaggerModuleAnnotation())
                .addMethods(methodSpecs)
                .build();
        try {
            JavaFile.builder(template.packageName(), typeSpec)
                    .build()
                    .writeTo(mFiler);
            System.out.println(String.format(TAG + "process module %s success", module));
        } catch (Exception e) {
            error(null, "generate template module failure %s" + e.getMessage());
        }
    }

    /**
     * 处理Navigator 代码生成
     *
     * @param roundEnvironment
     */
    private void processNavigatorCodeGeneration(RoundEnvironment roundEnvironment,
                                                Template template) {
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

    /**
     * 处理ViewModel 代码生成
     *
     * @param roundEnvironment
     */
    private void processViewModelCodeGeneration(RoundEnvironment roundEnvironment,
                                                Template template) {
        final ClassName dataRepositoryKitClassName = buildDataRepositoryKit();
        final String dataRepositoryKitParameterName = "dataRepositoryKit";
        final String dataRepositoryKitFiledName = String.format("m%s", dataRepositoryKitClassName.simpleName());
        FieldSpec dataRepositoryKitFieldSpec = FieldSpec.builder(dataRepositoryKitClassName, dataRepositoryKitFiledName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .build();

        final ClassName applicationClassName = ClassName.get("android.app", "Application");
        final String applicationParameterName = "application";

        MethodSpec methodSpec = MethodSpec.constructorBuilder()
                .addParameter(ParameterSpec.builder(applicationClassName, applicationParameterName).addAnnotation(buildNonNullAnnotation()).addModifiers(Modifier.FINAL).build())
                .addParameter(ParameterSpec.builder(dataRepositoryKitClassName, dataRepositoryKitParameterName).addAnnotation(buildNonNullAnnotation()).addModifiers(Modifier.FINAL).build())
                .addModifiers(Modifier.PUBLIC)
                .addStatement("super($N)", applicationParameterName)
                .addStatement("this.$N = $N", dataRepositoryKitFiledName, dataRepositoryKitParameterName)
                .build();

        final String navigator = String.format("%sNavigator", template.name());
        final String viewModelClassName = String.format("%sViewModel", template.name());
        final ClassName navigatorClassName = ClassName.get(template.packageName(), navigator);
        final ClassName baseViewModelClassName = ClassName.get("com.xxl.core.ui", "BaseViewModel");
        final ParameterizedTypeName baseViewModelTypeName = ParameterizedTypeName.get(baseViewModelClassName, navigatorClassName);

        TypeSpec viewModelTypeSpec = TypeSpec.classBuilder(viewModelClassName)
                .addJavadoc(buildTypeJavadoc(template.author(), template.description() + "数据模型"))
                .addModifiers(Modifier.PUBLIC)
                .superclass(baseViewModelTypeName)
                .addMethod(methodSpec)
                .addField(dataRepositoryKitFieldSpec)
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


    /**
     * 获取当前日期
     *
     * @return
     */
    private String getCurrentDate() {
        return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
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
        return String.format("%s\n\n@author %s\n@date %s", description, author, getCurrentDate());
    }

    /**
     * 构建@NonNull注解
     *
     * @return
     */
    private ClassName buildNonNullAnnotation() {
        return ClassName.get("androidx.annotation", "NonNull");
    }

    /**
     * 构建 dagger @Module
     *
     * @return
     */
    private ClassName buildDaggerModuleAnnotation() {
        return ClassName.get("dagger", "Module");
    }

    /**
     * 构建 dagger @Provides
     *
     * @return
     */
    private ClassName buildDaggerProvidesAnnotation() {
        return ClassName.get("dagger", "Provides");
    }

    /**
     * 构建DataRepositoryKit
     *
     * @return
     */
    private ClassName buildDataRepositoryKit() {
        return ClassName.get("com.xxl.hello.service.data.repository", "DataRepositoryKit");
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