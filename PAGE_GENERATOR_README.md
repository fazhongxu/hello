# MVVM 页面代码生成脚本使用说明

## 简介

`generate_page.py` 是一个用于快速生成符合项目 MVVM 架构规范的页面代码的脚本。它可以根据指定的参数自动生成 7 个文件，大大提高开发效率。

## 使用方法

### 基本用法

```bash
# 在 widget 模块中创建一个名为 TestPage 的页面，功能目录为 test
python3 generate_page.py --name TestPage --module widget --feature test

# 在 user 模块中创建一个名为 UserLogin 的页面，功能目录为 login，并添加描述
python3 generate_page.py --name UserLogin --module user --feature login --desc "用户登录页面"

# 在 main 模块中创建页面，不生成布局文件（仅生成 Java 代码）
python3 generate_page.py --name MainPage --module main --feature home --no-layout
```

### 参数说明

| 参数 | 简写 | 必填 | 说明 |
|------|------|------|------|
| `--name` | `-n` | ✅ | 页面名称（PascalCase，如：VideoDownload、UserLogin） |
| `--module` | `-m` | ✅ | 目标模块（可选值：widget、main、user） |
| `--feature` | `-f` | ✅ | 功能目录名称（如：video、login、home） |
| `--desc` | `-d` | ❌ | 页面描述（默认为页面名称） |
| `--no-router` | - | ❌ | 不生成路由配置代码片段 |
| `--no-layout` | - | ❌ | 不生成布局文件 |
| `--dry-run` | - | ❌ | 预览生成内容，不实际写入文件 |

### 模块说明

| 模块 | 说明 | UI 包名 |
|------|------|---------|
| `widget` | framework/module_widget | com.xxl.hello.widget.ui |
| `main` | modules/module_main | com.xxl.hello.main.ui |
| `user` | modules/module_user | com.xxl.hello.user.ui |

## 生成的文件

脚本会在指定模块的 `src/main/java/<package>/ui/<feature>/` 目录下生成以下 6 个 Java 文件：

1. **XxxNavigator.java** - Navigator 接口（ViewModel → Fragment 回调）
2. **XxxViewModel.java** - ViewModel 视图模型
3. **XxxFragment.java** - Fragment 页面
4. **XxxActivity.java** - Activity 宿主
5. **XxxFragmentModule.java** - Dagger Module
6. **XxxFragmentProvider.java** - Dagger Provider

如果未使用 `--no-layout` 参数，还会在模块的 `res/layout/` 目录下生成：

7. **widget_fragment_xxx.xml** - DataBinding 布局文件

## 使用示例

### 示例 1：创建视频下载页面

```bash
python3 generate_page.py \
  --name VideoDownload \
  --module widget \
  --feature video \
  --desc "视频下载页面"
```

生成后的文件结构：
```
framework/module_widget/
├── src/main/java/com/xxl/hello/widget/ui/video/
│   ├── VideoDownloadActivity.java
│   ├── VideoDownloadFragment.java
│   ├── VideoDownloadFragmentModule.java
│   ├── VideoDownloadFragmentProvider.java
│   ├── VideoDownloadNavigator.java
│   └── VideoDownloadViewModel.java
└── src/main/res/layout/
    └── widget_fragment_video_download.xml
```

### 示例 2：创建用户登录页面

```bash
python3 generate_page.py \
  --name UserLogin \
  --module user \
  --feature login \
  --desc "用户登录页面"
```

### 示例 3：Dry Run 预览

在生成文件前，可以使用 `--dry-run` 参数预览将要生成的内容：

```bash
python3 generate_page.py \
  --name TestPage \
  --module widget \
  --feature test \
  --dry-run
```

## 生成后的手动步骤

脚本无法自动完成以下步骤，需要手动处理：

### 1. 添加路由配置

脚本会生成一个路由代码片段文件（如 `widget_fragment_test_page_router_snippet.txt`），需要将其内容复制到对应的 RouterApi 文件中：

- **widget 模块**：`framework/module_widget/src/main/java/com/xxl/hello/widget/data/router/WidgetRouterApi.java`
- **main 模块**：`framework/module_router/src/main/java/com/xxl/hello/router/api/MainRouterApi.java`
- **user 模块**：`framework/module_router/src/main/java/com/xxl/hello/router/api/UserRouterApi.java`

### 2. 注册 Activity

在对应模块的 ActivityBuilder 中添加绑定方法：

```java
@ContributesAndroidInjector(modules = TestPageFragmentProvider.class)
abstract TestPageActivity bindTestPageActivityBuilder();
```

- **widget 模块**：`framework/module_widget/src/main/java/com/xxl/hello/widget/di/builder/WidgetActivityBuilder.java`
- **main 模块**：`modules/module_main/src/main/java/com/xxl/hello/main/di/builder/MainAppActivityBuilder.java`
- **user 模块**：`modules/module_user/src/main/java/com/xxl/hello/user/di/builder/UserActivityBuilder.java`

### 3. 添加字符串资源

在 `framework/module_resources/src/main/res/values/resources_strings.xml` 中添加：

```xml
<string name="resources_{feature}_title">{描述}</string>
```

例如：
```xml
<string name="resources_test_title">测试页面</string>
```

### 4. 实现 TODO 项

生成的文件中有以下 TODO 需要完成：

- **Navigator.java**：定义 ViewModel 调用 Fragment 的回调方法
- **ViewModel.java**：添加 ObservableField 数据字段和业务逻辑方法
- **Fragment.java**：实现 Navigator 接口方法、初始化视图和数据
- **Layout.xml**：添加实际的 UI 组件

## 常见问题

### Q1：脚本执行权限被拒绝？

```bash
chmod +x generate_page.py
```

### Q2：Python 版本要求？

需要 Python 3.6 或更高版本。

### Q3：如何修改模板？

编辑 `generate_page.py` 文件中的模板函数（`get_xxx_template`）即可。

### Q4：生成的文件与现有文件冲突怎么办？

脚本会提示是否覆盖，输入 `n` 取消，手动备份现有文件后再运行。

## 高级用法

### 1. 批量生成页面

可以创建一个 shell 脚本来批量生成多个页面：

```bash
#!/bin/bash
python3 generate_page.py --name Page1 --module widget --feature test1
python3 generate_page.py --name Page2 --module widget --feature test2
python3 generate_page.py --name Page3 --module widget --feature test3
```

### 2. 自定义模板

如果项目的代码风格有变化，可以修改脚本中的模板函数：
- `get_navigator_template` - Navigator 接口模板
- `get_viewmodel_template` - ViewModel 模板
- `get_fragment_template` - Fragment 模板
- `get_activity_template` - Activity 模板
- `get_fragment_module_template` - FragmentModule 模板
- `get_fragment_provider_template` - FragmentProvider 模板
- `get_layout_template` - 布局文件模板
