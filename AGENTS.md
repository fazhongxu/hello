# 仓库贡献指南

一款多模块 Android 应用（applicationId `com.xxl.hello`），采用 MVVM 架构：Dagger2 + Retrofit + OkHttp 构建网络层，ARouter 实现模块间路由。AspectJX 提供 AOP 能力（登录检测、网络检测、VIP 拦截等），各模块可通过 `config.gradle` 在本地源码与 Nexus 私服 AAR 之间切换。

## 项目结构与模块组织

- `app/` — 应用主壳模块，负责组装所有模块并配置 release 签名。
- `framework/` — 基础库层，供业务模块依赖：
  - `module_core`、`module_kit`、`module_common`、`module_service`、`module_resources`、`module_widget`、`module_router`
  - `module_annotation` + `module_compiler` — 编译期注解处理（如微信支付回调类生成）。
- `modules/` — 业务功能模块：`module_main`、`module_user`。
- `module_libs/` — 三方库隔离封装：`lib_ffmpeg`、`lib_picture_selector`、`lib_pinyin`、`lib_watermark`。
- `config.gradle` — 统一依赖版本配置（`deps`、`build_versions`）；`common.gradle` — 库模块通用配置。
- `schemas/`、`docs/`、`tools/` — 辅助资源与文档。
- 源码遵循标准 Android 目录结构：`src/main/`、`src/test/`（单元测试）、`src/androidTest/`（插桩测试）。


## 架构总览

应用采用 **MVVM** 架构，严格按依赖方向自上而下分为四层，无循环依赖（`gradle.properties` 中 `org.gradle.parallel=true` 生效的前提）。模块间不直接写 `project()`，而是通过 `config.gradle` 的 `deps` 映射间接引用，每个模块有 `versions.*_remote` 开关在「本地源码」与「Nexus 私服 AAR」间切换。

### 分层依赖关系

```
                     app  (应用壳: 签名/渠道/AndResGuard/AspectJX)
                      │
        ┌─────────────┴─────────────┐
   module_main                 module_user        (业务层, 用 common.gradle)
        │                           │
        └─────────────┬─────────────┘
                     ▼
   ┌─────────────────────────────────────────────┐
   │  hello.router  hello.widget  hello.common   │   (framework 业务库)
   │  hello.service  hello.annotation            │
   └────────────────────┬────────────────────────┘
                        │ kapt hello.compiler
                        ▼
                 origin.core  origin.kit           (framework 底座)
                        │
                        ▼
              module_libs (lib_ffmpeg / lib_picture_selector /
                           lib_pinyin / lib_watermark)        (三方隔离层)
```

### framework 各模块职责

- **`module_core`**（`com.xxl.core`）— 底座。`BaseActivity`/`BaseApplication`/`BaseBindingAdapter`、AOP 切面（`AsyncAspect`、`AndroidIdHookAspect`）、音频（`AudioPlayer`/`AudioCapture`）、下载（`AriaDownloadServiceImpl`）、网络 `ApiHeader`。
- **`module_kit`**（`com.xxl.kit`）— 纯工具箱，零业务依赖。`FFmpegUtils`、`EncryptUtils`、`ImageUtils`、`DeviceUtils`、`CountdownWrapper`、路由聚合 `AppRouterApi`。
- **`module_common`**（`com.xxl.hello.common`）— 业务配置中心（Kotlin 为主）：`AppConfig`/`AppOptions`、`NetworkConfig`、`ShareConfig`、`CacheDirConfig`、`IconManager`（图标切换）。
- **`module_service`**（`com.xxl.hello.service`）— 数据层与服务抽象：`BaseService`/`BaseRepositoryIml`/`BaseDataSource`、ObjectBox 实体、`*LocalDataSource` 与 `*RemoteDataSource` 双源分离、Scheme 服务。
- **`module_widget`**（`com.xxl.hello.widget`）— 复杂 UI 组件：聊天会话体系（`BaseChatSessionActivity/Fragment/ViewModel`、`ChatSessionAdapter`、`BaseMessageProvider`）、分享 `BaseSharePicker`、`BaseWebFragment`。
- **`module_router`**（`com.xxl.hello.router`）— 路由 API 与拦截器：`MainRouterApi`、`UserRouterApi`、`LoginInterceptor`。
- **`module_annotation` + `module_compiler`** — 注解 + APT 处理器：`@WXEntry`（绕过微信支付需在 app 建 wxapi 目录）、`@Bind`、`@Template`，由 `HelloCompiler` 聚合入口。

### 业务模块（modules）

- **`module_main`** — App 壳业务实现：`HelloApplication`、`MainActivity`、`SplashActivity`（启动页）、`SchemeJumpActivity`（Scheme 统一跳转）、Dagger2 图（`AppComponent`/`AppModule`）、微信回调 `WeChatCallbackActivity`、桌面小部件 `HelloAppWidgetProvider`。
- **`module_user`** — 用户体系（标准 MVVM 分层）：`LoginActivity`/`LoginViewModel`/`LoginNavigator`、Repository 模式（`UserRepository` + 本地/远程双数据源）、请求响应实体、`PrivacyPolicyPopupWindow`。

### 关键设计点

- **本地 ⇄ 远程切换**：`config.gradle` 中改 `versions.module_core_remote = true`，即可把 `module_core` 从源码依赖切到私服 AAR，便于核心库独立发版联调；`module_libs` 同理（`ffmpeg_kit_remote`、`picture_selector_remote`）。
- **数据层双源**：`module_service` 每个领域都有 `*LocalDataSource` 与 `*RemoteDataSource`，由 Repository 统一调度，符合 MVVM + Clean Architecture 数据流向。
## 构建、测试与开发命令

所有命令均通过 Gradle Wrapper 在仓库根目录执行。

- `./gradlew assembleDebug` / `assembleRelease` — 构建 debug / release APK。
- `./gradlew resguardRelease` — 集成 AndResGuard 资源压缩与混淆的 release 打包命令（主要打包方式）。
- `./channel.sh` — 基于 VasDolly 的多渠道打包。
- `./signer.sh` — 对 APK 进行签名（使用 `hello` 密钥库）。
- `./gradlew :modules:module_user:assemble` — 构建单个模块 AAR（支持 fat-aar 内嵌打包）。
- `./gradlew test` / `connectedAndroidTest` — 运行单元测试 / 插桩测试。
- `./clean.sh` — 清理 `.idea`、`*.iml` 及所有 `build/` 目录。
- `./kill_gradle.sh` — 查找并杀死卡死的 Gradle 进程。
- `./gradlew :app:dependencies | grep framework > app/framework_deps.txt` — 查看模块依赖关系图。

## 编码规范与命名约定

- 语言：以 Java 为主，Kotlin 互操作；源码/目标兼容性为 Java 8（见 `config.gradle`）。
- 模块统一应用 `kotlin-android`、`kotlin-kapt`、`com.alibaba.arouter`；kapt 传入 `AROUTER_MODULE_NAME`。
- 启用 DataBinding（`buildFeatures.dataBinding = true`），使用生成的 Binding 类替代 `findViewById`。
- 依赖版本必须在 `config.gradle` 中集中声明并通过 `deps` 映射引用，禁止在模块 `build.gradle` 中硬编码版本号。
- 通过 `config.gradle` 中的 `versions.*_remote` 开关在本地源码与远程 AAR 间切换。

## 测试规范

- 单元测试使用 JUnit 4（`testImplementation 'junit:junit:4.12'`）；插桩测试使用 AndroidX Test 与 Espresso。
- 单元测试置于 `src/test/`，插桩测试置于 `src/androidTest/`，包路径与被测类保持一致。
- 测试类遵循 `*Test` / `*AndroidTest` 命名约定。
- 运行单个模块测试：`./gradlew :modules:module_user:testDebugUnitTest`。

## 提交与 Pull Request 规范

- 提交信息为简洁的中文描述，使用祈使或陈述语气（如 `增加图片编辑页面`、`修复编辑图片后显示空白问题`）。多变更提交可使用 `1.…；2.…` 编号列表。
- 禁止提交生成产物：`build/`、`*.iml`、`.idea/`、`bin/` 均在 `.gitignore` 中——必要时先执行 `./clean.sh`。
- 功能开发在 `feature/*` 分支进行（如 `feature/audio`、`feature/dagger2`）；PR 应限定在单一功能或修复范围内，并关联对应 issue。
- 提交前移除测试/调试代码；历史提交中频繁可见对临时测试逻辑的清理。
