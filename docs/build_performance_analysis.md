# 项目构建性能分析报告

## 问题概述

Android 项目（包名：`com.xxl.hello`）在修改代码后运行速度较慢，影响开发效率。

---

## 性能瓶颈分析

### 🔴 主要瓶颈

**1. kapt 注解处理器过多 (最大瓶颈)**

使用了 5 个 kapt 处理器：
- `dagger-compiler` + `dagger-android-processor` (Dagger 2)
- `arouter-compiler` (ARouter)
- `glide-compiler` (Glide)
- `module_compiler` (自定义处理器)
- `aira_processor` (Aria 下载库)

**问题**：kapt 不支持增量编译，每次修改代码都会全量重新处理，这是最大的性能杀手。

---

**2. AspectJX AOP 字节码织入**

- 编译时需要扫描和修改所有 class 字节码
- 即使配置了排除规则，初始化和解析仍耗时
- 位置：`android-aspectjx` 插件 + `aspectjx_excludes.gradle`

---

**3. DataBinding**

- 每个模块都启用，编译时生成大量绑定类
- 与 kapt 结合时性能影响叠加

---

**4. ObjectBox 数据库代码生成**

- 需要生成模型类的数据库映射代码
- 应用于 `app` 和 `module_service` 模块

---

**5. 多模块依赖链**

```
module_kit → module_core → module_service → 业务模块
```

虽然并行构建已开启 (`org.gradle.parallel=true`)，但依赖链限制了并行度。

---

## 优化方案

### 方案 1：KAPT 性能调优 (立即可用)

在 `gradle.properties` 中添加：

```properties
# KAPT 性能优化
kapt.use.worker.api=true
kapt.incremental.apt=true
kapt.use.k2=false

# 增加 kapt 缓存
kapt.kotlin.generated.services.dir.is.cacheable=true
```

---

### 方案 2：用 KSP 替代 kapt (推荐，中期方案)

**KSP (Kotlin Symbol Processing)** 比 kapt 快 2-3 倍，且支持增量编译。

在 `config.gradle` 中添加版本：

```gradle
versions.ksp = "1.6.21-1.0.6"
```

在项目根 `build.gradle` 中添加插件：

```gradle
dependencies {
    classpath "com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:$versions.ksp"
}
```

在 `app/build.gradle` 中替换部分 kapt：

```gradle
apply plugin: 'com.google.devtools.ksp'

// 替换前
// kapt deps.glide.glide_processor
// kapt deps.arouter.arouter_processor

// 替换后
ksp deps.glide.glide_processor
ksp deps.arouter.arouter_processor
```

> 注意：Dagger 和 Aria 目前不完全支持 KSP，仍需使用 kapt

---

### 方案 3：Debug 构建优化 (立即可用)

在 `app/build.gradle` 的 `debug` 构建类型中禁用不必要的处理：

```gradle
buildTypes {
    debug {
        minifyEnabled false
        shrinkResources false

        // 如果 AOP 注解使用不多，可以禁用
        // 需配合 aspectjx_excludes.gradle 使用
    }
}
```

修改 `aspectjx_excludes.gradle`，支持按需启用：

```gradle
aspectjx {
    // Debug 模式禁用，Release 模式启用
    enabled project.hasProperty('enableAspectJ') ? project.enableAspectJ : false

    exclude 'androidx', 'android.support', 'kotlin', ...
}
```

构建时显式启用（仅 Release 需要）：
```bash
./gradlew assembleRelease -PenableAspectJ=true
```

---

### 方案 4：模块化隔离 (长期方案)

把不常变的 framework 模块发布为 AAR 到本地 Nexus。

在 `config.gradle` 中切换：

```gradle
# 开发时使用本地源码
versions.module_core_remote = false
versions.module_kit_remote = false
versions.module_service_remote = false

# 稳定后切换为 AAR
versions.module_core_remote = true
versions.module_kit_remote = true
versions.module_service_remote = true
```

这样每次构建只需编译业务模块，framework 模块直接使用已编译的 AAR。

---

### 方案 5：依赖精简 (可选)

| 技术 | 替代方案 | 影响 |
|------|---------|------|
| Aria 下载库 | 使用 `module_service` 中的下载服务 | 减少 1 个 kapt |
| ObjectBox | 如果用得少，改用 MMKV/Room | 减少代码生成 |

---

## 立即可做的快速优化

在 `gradle.properties` 末尾添加：

```properties
# KAPT 性能优化
kapt.use.worker.api=true
kapt.incremental.apt=true

# Kotlin 编译优化
kotlin.daemon.jvm.args=-Xmx2048m
kotlin.incremental=true
kotlin.incremental.js=true
kotlin.caching.enabled=true

# DataBinding 增量编译
android.databinding.incremental=true
```

然后执行：
```bash
./gradlew clean assembleDebug
```

---

## 预期收益

| 优化项 | 预计提升 | 难度 |
|--------|---------|------|
| KAPT 优化 + KSP | 30-50% | 中 |
| 禁用 Debug 模式 AspectJX | 15-25% | 低 |
| 模块 AAR 化 | 40-60% | 中 |
| 依赖精简 | 10-20% | 高 |
| **全部优化后** | **50-70%** | - |

---

## 当前有效配置

以下配置已在 `gradle.properties` 中启用，保持不变：

```properties
org.gradle.jvmargs=-Xmx4096m -XX:+UseParallelGC
org.gradle.parallel=true
org.gradle.caching=true
org.gradle.daemon=true
org.gradle.configureondemand=true
android.enableBuildCache=true
```

这些是基础优化，对多模块项目效果显著。

---

## 执行建议

1. **立即执行**：添加 gradle.properties 快速优化配置
2. **短期计划**：评估 KSP 迁移可行性（先替换 Glide 和 ARouter）
3. **中期计划**：Debug 模式禁用 AspectJX
4. **长期规划**：Framework 模块 AAR 化

---

## 参考链接

- [KSP 官方文档](https://kotlinlang.org/docs/ksp-overview.html)
- [Gradle 性能优化指南](https://developer.android.com/build/optimize-your-build)
- [Android 构建剖析](https://developer.android.com/build/build-profile)
