# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

Android 应用（包名：`com.xxl.hello`），采用 MVVM 架构、Dagger 2 依赖注入、多模块结构。主要语言为 Java，部分使用 Kotlin。UI 层使用 DataBinding（非 Compose）。

## 构建命令

```bash
# Debug 构建
./gradlew assembleDebug

# Release 构建（带 AndResGuard 资源压缩）
./gradlew resguardRelease
# 或者
./release.sh

# 清理（删除 .idea、.iml 文件和 build 目录）
./clean.sh

# 杀死卡死的 Gradle 进程
./kill_gradle.sh

# 查看依赖树
./gradlew :app:dependencies | grep project > app/build/deps.txt

# 上传模块到本地 Nexus（Android Studio：Gradle 面板 -> 模块 -> Tasks -> upload -> uploadArchives）
```

项目中没有实际的测试套件，仅有框架生成的单元测试/Instrumented 测试文件。

## 模块架构

三层多模块项目：

```
app/                    # 空壳模块 - 仅有 AndroidManifest，无源代码
framework/              # 可复用的基础设施层
  module_kit            # 纯工具类（LogUtils、AppUtils 等），无 Android 依赖
  module_core           # 基础类、AOP、控件、DataBinding 适配器（依赖 module_kit）
  module_common         # 应用级配置（NetworkConfig、工具类）
  module_service        # 数据层：仓库、ObjectBox 数据库、MMKV 偏好、API 接口、上传/下载服务
  module_resources      # 仅共享 XML 资源
  module_widget         # 自定义跨模块控件
  module_router         # ARouter 路径常量和跨模块实体类型
  module_annotation     # 编译时注解（@Bind、@Template、@WXEntry、@TestCompiler）
  module_compiler       # 注解处理器（基于 AbstractProcessor）
modules/                # 业务功能模块
  module_main           # 应用入口：HelloApplication、SplashActivity、MainActivity、Dagger 根组件
  module_user           # 用户功能：登录、设置
module_libs/            # 第三方库封装
  lib_ffmpeg, lib_picture_selector, lib_pinyin, lib_watermark（AAR）
```

### 模块依赖关系
`module_kit` <- `module_core` <- `module_service` <- 业务模块（`module_main`、`module_user`）<- `app`

业务模块通过 `common.gradle`（共享库模块配置）依赖 framework 模块。`module_main` 和 `module_user` 之间通过 ARouter 通信，不直接引用。

### 本地/远程模块切换
`config.gradle` 中有布尔开关，如 `versions.module_core_remote = false`。设为 `true` 可使用 Nexus 私服的 AAR 包替代本地源码。

## 核心架构模式

### MVVM + Dagger 2
- **ViewModel** 继承 `BaseViewModel<Navigator>`（位于 `module_core`）。使用 RxJava 3 的 Disposable、`ObservableField` 用于 DataBinding、`MutableLiveData` 用于加载状态。
- **Navigator 接口** 定义从 ViewModel 到 Fragment 的 UI 回调（如 `MainNavigator`、`LoginNavigator`）。
- **Fragment** 继承 `BaseStateViewModelFragment<VM, Binding>`，内置状态管理（Loading/Empty/Error/Content）。
- **Activity** 承载单个 Fragment（如 `MainActivity extends SingleFragmentBarActivity<MainFragment>`）。

### Dagger 依赖注入结构
- 根组件：`AppComponent`（@Singleton），位于 `module_main/di/component/AppComponent.java`
- 每个功能模块有 `ActivityBuilder` 类，注册 Activity/Fragment 以供注入
- 每个功能模块有 `DataStoreModule`，聚合 local + remote + repository 的 Dagger Module
- 自定义限定符注解在 `module_service/qunlifier/`（如 `@ForHost`、`@ForRetrofit`、`@ForUserRetrofit`）

### 数据层
- 仓库模式：接口 + 实现（如 `UserRepository` / `UserRepositoryIml`）
- `DataRepositoryKit` 聚合所有仓库 API，通过 Dagger 注入
- 远程数据：Retrofit + OkHttp + RxJava 3，`BaseRemoteDataStoreSource` 持有 Retrofit 实例和 API 请求头
- 本地数据：ObjectBox（数据库）、MMKV（键值存储）、SharedPreferences 抽象
- 每个功能模块包含：`data/local/`、`data/remote/`、`data/repository/`，各自有独立的 Dagger Module

### 导航
- **ARouter** 用于所有跨模块导航。Router API 类定义路径（如 `UserRouterApi.Login.newBuilder().navigation()`）
- Activity 使用 `@Route(path = "...")` 注解
- `LoginInterceptor` 用于需要登录验证的路由
- `SchemeJumpActivity` 处理 Deep Link / Scheme URL

### AOP（AspectJX）
自定义注解在 `module_core/aop/annotation/`：`@Safe`（try-catch 防崩溃）、`@CheckLogin`、`@CheckNetwork`、`@Async`、`@Delay`、`@SingleClick`、`@LogTag`。对应的切面实现在 `module_core/aop/aspect/`。

## 配置文件

| 文件 | 用途 |
|------|------|
| `config.gradle` | 中央依赖目录 - 所有版本号、依赖坐标、Maven 仓库配置 |
| `common.gradle` | 共享库模块模板（android-library + 通用依赖） |
| `gradle.properties` | 构建性能调优（4GB 堆内存、并行构建、缓存） |
| `and_res_guard.gradle` | AndResGuard 资源压缩/混淆配置 |
| `aspectjx_excludes.gradle` | AspectJX 包名排除规则 |

## 关键技术细节

- **ObjectBox** 插件应用于 `app/build.gradle` 和 `module_service/build.gradle`
- **构建变体**：仅 Debug + Release，无 Product Flavor。多渠道通过 VasDolly 在构建后处理。
- **签名**：Debug/Release 使用同一密钥库（根目录下的 `hello` 文件）
- **compileSdkVersion**：30，**minSdkVersion**：19，**Java 8** 源码兼容性
- **网络配置**：`module_common` 中的 `NetworkConfig.kt` 控制 Debug/Release 环境切换
- **自定义注解处理器** 在 `module_compiler` 中为 `@Bind`、`@WXEntry` 等生成代码，处理微信支付回调而无需在 app 模块创建 wxapi 目录
- **上传服务** 抽象支持多个后端：Hello 服务器、七牛云、腾讯云（使用限定符注解进行 DI）
- **下载服务** 抽象支持：Aria 库和自定义 Hello 下载实现
- **第三方 SDK**：友盟（分析/分享/推送）、微信/QQ/微博（社交）、支付宝（支付）、Bugly（崩溃）、TBS X5（WebView）、DoKit（调试）

## 新建页面规范

### 页面文件结构（6 文件 + 布局）

在 `widget/ui/<feature>/` 或业务模块的 `ui/<feature>/` 目录下创建：

| 文件 | 说明 | 继承/注解 |
|------|------|-----------|
| `XxxNavigator.java` | ViewModel → Fragment 回调接口 | 普通接口 |
| `XxxViewModel.java` | 视图模型 | `BaseViewModel<XxxNavigator>` |
| `XxxFragment.java` | Fragment 页面 | `BaseViewModelFragment<XxxViewModel, XxxBinding>` 或 `BaseStateViewModelFragment` |
| `XxxActivity.java` | Activity 宿主 | `SingleFragmentBarActivity<XxxFragment>`（带 Toolbar）或 `SingleFragmentActivity`（不带） |
| `XxxFragmentModule.java` | Dagger Module，提供 ViewModel 和 Factory | `@Module` + `@Provides` |
| `XxxFragmentProvider.java` | Dagger Module，注册 Fragment 注入 | `@Module` + `@ContributesAndroidInjector(modules=XxxFragmentModule.class)` |
| `widget_fragment_xxx.xml` | DataBinding 布局文件 | 放在 `res/layout/` 下 |

### Navigator 接口

定义 ViewModel 调用 Fragment 的回调方法。可以为空接口（无回调时），方法签名示例：

```java
public interface XxxNavigator {
    void onSomethingHappened(String data);
}
```

### ViewModel 模板

```java
public class XxxViewModel extends BaseViewModel<XxxNavigator> {
    private final DataRepositoryKit mDataRepositoryKit;
    public ObservableField<Type> fieldName = new ObservableField<>(defaultValue);

    public XxxViewModel(@NonNull Application application,
                        @NonNull DataRepositoryKit dataRepositoryKit) {
        super(application);
        mDataRepositoryKit = dataRepositoryKit;
    }
}
```

- 构造参数：`Application` + `DataRepositoryKit`（+ 其他依赖如 `DownloadService`）
- 使用 `ObservableField` 暴露给 DataBinding 的数据
- 使用 `CompositeDisposable`（继承自 BaseViewModel）管理 RxJava 订阅
- 通过 `getNavigator()` 调用 Navigator 回调

### Fragment 模板

```java
public class XxxFragment extends BaseViewModelFragment<XxxViewModel, WidgetFragmentXxxBinding>
        implements XxxNavigator {

    @Autowired(name = WidgetRouterApi.Xxx.PARAMS_KEY)
    String mParam;

    public static XxxFragment newInstance(@NonNull Bundle args) {
        XxxFragment fragment = new XxxFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override protected int getLayoutRes() { return R.layout.widget_fragment_xxx; }
    @Override protected XxxViewModel createViewModel() {
        XxxViewModel vm = createViewModel(XxxViewModel.class);
        vm.setNavigator(this);
        return vm;
    }
    @Override protected boolean enableRouterInject() { return true; }  // 使用 ARouter @Autowired 时返回 true
    @Override public int getViewModelVariable() { return BR.viewModel; }
    @Override public int getViewNavigatorVariable() { return BR.navigator; }
    @Override protected void setupData() { /* 读取参数、验证状态 */ }
    @Override public void setupLayout(@NonNull View view) { /* 初始化视图、适配器、监听器 */ }
}
```

必须重写的方法（7 个）：`getLayoutRes`、`createViewModel`、`enableRouterInject`、`getViewModelVariable`、`getViewNavigatorVariable`、`setupData`、`setupLayout`。

### Activity 模板

```java
@Route(path = WidgetRouterApi.Xxx.PATH)
public class XxxActivity extends SingleFragmentBarActivity<XxxFragment> {
    @Override public XxxFragment createFragment() { return XxxFragment.newInstance(getExtras()); }
    @Override protected int getToolbarTitle() { return R.string.xxx_title; }
}
```

- 使用 `@Route` 注解注册 ARouter 路径
- `createFragment()` 用 `getExtras()` 传递 Intent 参数
- 可选重写：`isDisplayRightCustom()`、`getRightCustomLayout()`（自定义 Toolbar 右侧视图）

### Dagger Module 模板

```java
@Module
public class XxxFragmentModule {
    @Provides
    XxxViewModel provideXxxViewModel(@ForApplication Application application,
                                      @NonNull DataRepositoryKit dataRepositoryKit) {
        return new XxxViewModel(application, dataRepositoryKit);
    }
    @Provides
    ViewModelProvider.Factory provideXxxViewModelFactory(@NonNull XxxViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }
}
```

### Dagger Provider 模板

```java
@Module
public abstract class XxxFragmentProvider {
    @ContributesAndroidInjector(modules = XxxFragmentModule.class)
    abstract XxxFragment bindXxxFragmentFactory();
}
```

### DataBinding 布局模板

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <data>
        <variable name="viewModel" type="com.xxl.hello.widget.ui.xxx.XxxViewModel" />
        <variable name="navigator" type="com.xxl.hello.widget.ui.xxx.XxxNavigator" />
    </data>
    <!-- 根布局，使用实际的 UI 内容 -->
    <LinearLayout ... />
</layout>
```

- 根标签必须是 `<layout>`
- `<data>` 中声明 `viewModel` 和 `navigator` 两个变量
- 生成 Binding 类名规则：文件名去掉下划线转 PascalCase + Binding（如 `widget_fragment_video_download.xml` → `WidgetFragmentVideoDownloadBinding`）

### 路由注册

1. **路由 API**：在对应模块的 `RouterApi` 类中添加静态内部类，包含 `PATH`、参数常量、`Builder` 类
2. **ActivityBuilder**：在模块的 `ActivityBuilder` 类中添加 `@ContributesAndroidInjector` 方法绑定 Activity

### 字符串资源

统一放在 `framework/module_resources/src/main/res/values/resources_strings.xml`，命名规则：（如 `resources_video_download_title`）。

### Drawable 资源

放在 `framework/module_widget/src/main/res/drawable/` 下，命名规则：`resources_` 前缀（如 `resources_btn_download_bg.xml`）。
