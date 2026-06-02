#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Android MVVM 页面代码生成脚本

用于快速生成符合项目规范的 MVVM 页面文件，包括：
- Navigator.java
- ViewModel.java
- Fragment.java
- Activity.java
- FragmentModule.java
- FragmentProvider.java
- XML 布局文件

使用方法:
    python generate_page.py --name TestPage --module widget --feature test
    python generate_page.py --help

作者: Claude Code
日期: 2026/06/02
"""

import os
import sys
import argparse
from datetime import datetime
from pathlib import Path


# ============================================================================
# 模板配置
# ============================================================================

# 项目根目录（脚本所在目录）
PROJECT_ROOT = Path(__file__).parent.absolute()

# 模块路径映射
MODULE_PATHS = {
    'widget': {
        'base_dir': 'framework/module_widget',
        'package': 'com.xxl.hello.widget',
        'ui_package': 'com.xxl.hello.widget.ui',
        'router_api': 'framework/module_widget/src/main/java/com/xxl/hello/widget/data/router/WidgetRouterApi.java',
        'activity_builder': 'framework/module_widget/src/main/java/com/xxl/hello/widget/di/builder/WidgetActivityBuilder.java',
        'strings_resource': 'framework/module_resources/src/main/res/values/resources_strings.xml',
        'drawable_dir': 'framework/module_widget/src/main/res/drawable',
        'layout_dir': 'framework/module_widget/src/main/res/layout',
    },
    'main': {
        'base_dir': 'modules/module_main',
        'package': 'com.xxl.hello.main',
        'ui_package': 'com.xxl.hello.main.ui',
        'router_api': 'framework/module_router/src/main/java/com/xxl/hello/router/api/MainRouterApi.java',
        'activity_builder': 'modules/module_main/src/main/java/com/xxl/hello/main/di/builder/MainAppActivityBuilder.java',
        'strings_resource': 'framework/module_resources/src/main/res/values/resources_strings.xml',
        'drawable_dir': 'framework/module_widget/src/main/res/drawable',
        'layout_dir': 'modules/module_main/src/main/res/layout',
    },
    'user': {
        'base_dir': 'modules/module_user',
        'package': 'com.xxl.hello.user',
        'ui_package': 'com.xxl.hello.user.ui',
        'router_api': 'framework/module_router/src/main/java/com/xxl/hello/router/api/UserRouterApi.java',
        'activity_builder': 'modules/module_user/src/main/java/com/xxl/hello/user/di/builder/UserActivityBuilder.java',
        'strings_resource': 'framework/module_resources/src/main/res/values/resources_strings.xml',
        'drawable_dir': 'framework/module_widget/src/main/res/drawable',
        'layout_dir': 'modules/module_user/src/main/res/layout',
    },
}

# 当前日期
CURRENT_DATE = datetime.now().strftime('%Y/%m/%d')


# ============================================================================
# 模板定义
# ============================================================================

def get_navigator_template(class_name, feature_name, description, package, ui_package):
    """生成 Navigator 接口模板"""
    return f'''package {ui_package}.{feature_name};

/**
 * {description}
 *
 * @author xxl.
 * @date {CURRENT_DATE}.
 */
public interface {class_name}Navigator {{

    // TODO: 定义 ViewModel 调用 Fragment 的回调方法

}}
'''


def get_viewmodel_template(class_name, feature_name, description, package, ui_package, has_download_service=False):
    """生成 ViewModel 模板"""
    extra_imports = ""
    extra_fields = ""
    constructor_params = "DataRepositoryKit"

    if has_download_service:
        extra_imports = '''
import com.xxl.core.service.download.DownloadServiceWrapper;'''
        extra_fields = '''
    private DownloadServiceWrapper mDownloadServiceWrapper;

    /**
     * 设置下载服务包装类
     *
     * @param downloadServiceWrapper
     */
    public void setDownloadServiceWrapper(@NonNull final DownloadServiceWrapper downloadServiceWrapper) {{
        mDownloadServiceWrapper = downloadServiceWrapper;
    }}
'''

    return f'''package {ui_package}.{feature_name};

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.xxl.core.ui.BaseViewModel;
import com.xxl.hello.service.data.repository.DataRepositoryKit;{extra_imports}

/**
 * {description}
 *
 * @author xxl.
 * @date {CURRENT_DATE}.
 */
public class {class_name}ViewModel extends BaseViewModel<{class_name}Navigator> {{

    //region: 成员变量

    private final DataRepositoryKit mDataRepositoryKit;{extra_fields}

    /**
     * TODO: 添加 ObservableField 数据字段
     */
    public ObservableField<String> sampleData = new ObservableField<>("");

    //endregion

    //region: 构造函数

    public {class_name}ViewModel(@NonNull final Application application,
                                  @NonNull final DataRepositoryKit dataRepositoryKit) {{
        super(application);
        mDataRepositoryKit = dataRepositoryKit;
    }}

    //endregion

    //region: 提供方法

    /**
     * TODO: 添加业务逻辑方法
     */
    public void onButtonClick() {{
        // 处理按钮点击事件
    }}

    //endregion

}}
'''


def get_fragment_template(class_name, feature_name, description, package, ui_package, router_class_name, module_name):
    """生成 Fragment 模板"""
    return f'''package {ui_package}.{feature_name};

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.hello.{module_name}.BR;
import com.xxl.hello.{module_name}.R;
import com.xxl.hello.{module_name}.data.router.{router_class_name};
import com.xxl.hello.{module_name}.databinding.{get_binding_name(class_name)};

/**
 * {description}
 *
 * @author xxl.
 * @date {CURRENT_DATE}.
 */
public class {class_name}Fragment extends BaseViewModelFragment<{class_name}ViewModel, {get_binding_name(class_name)}>
        implements {class_name}Navigator {{

    //region: 成员变量

    private {get_binding_name(class_name)} mBinding;

    private {class_name}ViewModel mViewModel;

    // TODO: 如果有路由参数，添加 @Autowired 注解
    // @Autowired(name = {router_class_name}.{class_name}.PARAMS_KEY_XXX)
    // String mParam;

    //endregion

    //region: 构造函数

    public static {class_name}Fragment newInstance(@NonNull final Bundle args) {{
        final {class_name}Fragment fragment = new {class_name}Fragment();
        fragment.setArguments(args);
        return fragment;
    }}

    //endregion

    //region: 页面生命周期

    @Override
    protected int getLayoutRes() {{
        return R.layout.{get_layout_name(class_name)};
    }}

    @Override
    protected {class_name}ViewModel createViewModel() {{
        mViewModel = createViewModel({class_name}ViewModel.class);
        mViewModel.setNavigator(this);
        return mViewModel;
    }}

    @Override
    protected boolean enableRouterInject() {{
        return true;
    }}

    @Override
    public int getViewModelVariable() {{
        return BR.viewModel;
    }}

    @Override
    public int getViewNavigatorVariable() {{
        return BR.navigator;
    }}

    @Override
    protected void setupData() {{
        // TODO: 初始化数据，读取路由参数
    }}

    @Override
    public void setupLayout(@NonNull View view) {{
        mBinding = getViewDataBinding();
        setupLayoutView();
    }}

    //endregion

    //region: 页面视图渲染

    private void setupLayoutView() {{
        // TODO: 初始化视图，设置监听器
    }}

    //endregion

    //region: {class_name}Navigator

    // TODO: 实现 Navigator 接口方法

    //endregion

}}
'''


def get_activity_template(class_name, feature_name, description, package, ui_package, router_class_name, module_name):
    """生成 Activity 模板"""
    return f'''package {ui_package}.{feature_name};

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.core.ui.activity.SingleFragmentBarActivity;
import com.xxl.hello.{module_name}.R;
import com.xxl.hello.{module_name}.data.router.{router_class_name}.{class_name};

/**
 * {description}
 *
 * @author xxl.
 * @date {CURRENT_DATE}.
 */
@Route(path = {class_name}.PATH)
public class {class_name}Activity extends SingleFragmentBarActivity<{class_name}Fragment> {{

    //region: 页面生命周期

    @Override
    public {class_name}Fragment createFragment() {{
        return {class_name}Fragment.newInstance(getExtras());
    }}

    @Override
    protected int getToolbarTitle() {{
        return R.string.resources_{get_feature_name_snake(feature_name)}_title;
    }}

    //endregion

}}
'''


def get_fragment_module_template(class_name, feature_name, description, package, ui_package):
    """生成 FragmentModule 模板"""
    return f'''package {ui_package}.{feature_name};

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.xxl.core.ui.ViewModelProviderFactory;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.qunlifier.ForApplication;

import dagger.Module;
import dagger.Provides;

/**
 * {description}
 *
 * @author xxl.
 * @date {CURRENT_DATE}.
 */
@Module
public class {class_name}FragmentModule {{

    @Provides
    {class_name}ViewModel provide{class_name}ViewModel(@ForApplication final Application application,
                                                          @NonNull final DataRepositoryKit dataRepositoryKit) {{
        return new {class_name}ViewModel(application, dataRepositoryKit);
    }}

    @Provides
    ViewModelProvider.Factory provide{class_name}ViewModelFactory(@NonNull final {class_name}ViewModel viewModel) {{
        return new ViewModelProviderFactory<>(viewModel);
    }}

}}
'''


def get_fragment_provider_template(class_name, feature_name, description, package, ui_package):
    """生成 FragmentProvider 模板"""
    return f'''package {ui_package}.{feature_name};

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * {description}
 *
 * @author xxl.
 * @date {CURRENT_DATE}.
 */
@Module
public abstract class {class_name}FragmentProvider {{

    /**
     * 绑定{description}
     *
     * @return
     */
    @ContributesAndroidInjector(modules = {class_name}FragmentModule.class)
    abstract {class_name}Fragment bind{class_name}FragmentFactory();

}}
'''


def get_layout_template(class_name, feature_name, package, ui_package, module_name):
    """生成 XML 布局模板"""
    return f'''<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <data>

        <variable
            name="viewModel"
            type="{ui_package}.{feature_name}.{class_name}ViewModel" />

        <variable
            name="navigator"
            type="{ui_package}.{feature_name}.{class_name}Navigator" />

    </data>

    <LinearLayout
        android:id="@+id/ll_root_container"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:padding="16dp">

        <!-- TODO: 添加 UI 组件 -->

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{{viewModel.sampleData}}"
            android:textSize="16sp" />

    </LinearLayout>

</layout>
'''


def get_router_api_template(class_name, feature_name, description, module_name):
    """生成路由 API 模板（用于插入到 RouterApi 文件）"""
    feature_snake = get_feature_name_snake(feature_name)
    return f'''

    //region: {description}路由相关

    public static class {class_name} {{

        /**
         * {description}页面路径地址
         */
        public static final String PATH = {get_module_name_constant(module_name)} + "/{feature_snake}";

        // TODO: 添加路由参数常量
        // public static final String PARAMS_KEY_XXX = "params_key_xxx";

        public static Builder newBuilder() {{
            return new Builder();
        }}

        public static class Builder {{

            private Bundle mParams = new Bundle();

            public Builder() {{

            }}

            /**
             * TODO: 添加参数设置方法
             *
             * @param param
             * @return
             */
            // public Builder setXxx(@NonNull final String param) {{
            //     mParams.putString(PARAMS_KEY_XXX, param);
            //     return this;
            // }}

            /**
             * 跳转到{description}
             */
            public void navigation() {{
                RouterUtils.navigation(PATH, mParams);
            }}
        }}
    }}

    //endregion
'''


# ============================================================================
# 工具函数
# ============================================================================

def get_class_name(name):
    """将名称转换为 PascalCase（保持用户输入的大小写，只做规范化）"""
    # 如果用户输入的是 VideoDownload，保持为 VideoDownload
    # 如果用户输入的是 video-download，转换为 VideoDownload
    if not name:
        return ''
    # 首字母大写
    return name[0].upper() + name[1:]


def get_feature_name_snake(feature_name):
    """将功能名称转换为 snake_case"""
    import re
    s1 = re.sub('(.)([A-Z][a-z]+)', r'\1_\2', feature_name)
    return re.sub('([a-z0-9])([A-Z])', r'\1_\2', s1).lower()


def get_binding_name(class_name):
    """生成 DataBinding 类名"""
    # 例如：VideoDownload -> WidgetFragmentVideoDownloadBinding
    return f'WidgetFragment{class_name}Binding'


def get_layout_name(class_name):
    """生成布局文件名"""
    # 例如：VideoDownload -> widget_fragment_video_download
    camel_case = class_name[0].lower() + class_name[1:] if class_name else ''
    import re
    snake_case = re.sub('([a-z0-9])([A-Z])', r'\1_\2', camel_case).lower()
    return f'widget_fragment_{snake_case}'


def get_module_name_constant(module_name):
    """获取模块名称常量"""
    module_constants = {
        'widget': 'WIDGET_MODULE_NAME',
        'main': 'MAIN_MODULE_NAME',
        'user': 'USER_MODULE_NAME',
    }
    return module_constants.get(module_name, 'WIDGET_MODULE_NAME')


def ensure_dir(directory):
    """确保目录存在"""
    Path(directory).mkdir(parents=True, exist_ok=True)


def write_file(file_path, content):
    """写入文件"""
    with open(file_path, 'w', encoding='utf-8') as f:
        f.write(content)


# ============================================================================
# 主逻辑
# ============================================================================

def parse_args():
    """解析命令行参数"""
    parser = argparse.ArgumentParser(
        description='Android MVVM 页面代码生成脚本',
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog='''
示例:
  python generate_page.py --name VideoDownload --module widget --feature video
  python generate_page.py --name UserLogin --module user --feature login --desc "用户登录页面"
  python generate_page.py --name TestPage --module widget --feature test --no-router
        '''
    )

    parser.add_argument('--name', '-n', required=True, help='页面名称（PascalCase，如：VideoDownload）')
    parser.add_argument('--module', '-m', required=True, choices=['widget', 'main', 'user'], help='目标模块')
    parser.add_argument('--feature', '-f', required=True, help='功能目录名称（如：video、login）')
    parser.add_argument('--desc', '-d', default='', help='页面描述（默认为页面名称）')
    parser.add_argument('--no-router', action='store_true', help='不生成路由配置和 ActivityBuilder 注册')
    parser.add_argument('--no-layout', action='store_true', help='不生成布局文件')
    parser.add_argument('--dry-run', action='store_true', help='预览生成内容，不实际写入文件')

    return parser.parse_args()


def generate_files(args):
    """生成所有文件"""
    # 参数处理
    class_name = get_class_name(args.name)
    feature_name = args.feature.lower()
    description = args.desc if args.desc else class_name
    module_config = MODULE_PATHS[args.module]

    base_dir = PROJECT_ROOT / module_config['base_dir']
    package = module_config['package']
    ui_package = module_config['ui_package']
    module_name = args.module
    router_class_name = f'{module_config["package"].split(".")[-1].capitalize()}RouterApi'

    if args.module == 'widget':
        router_class_name = 'WidgetRouterApi'

    # UI 目录
    ui_dir = base_dir / f'src/main/java/{ui_package.replace(".", "/")}/{feature_name}'

    # 布局目录
    layout_dir = PROJECT_ROOT / module_config['layout_dir']

    print(f'\n========================================')
    print(f'开始生成 MVVM 页面')
    print(f'========================================')
    print(f'页面名称: {class_name}')
    print(f'功能目录: {feature_name}')
    print(f'目标模块: {args.module}')
    print(f'包名: {ui_package}.{feature_name}')
    print(f'描述: {description}')
    print(f'========================================\n')

    files_to_create = []

    # 1. Navigator
    navigator_path = ui_dir / f'{class_name}Navigator.java'
    navigator_content = get_navigator_template(class_name, feature_name, description, package, ui_package)
    files_to_create.append((navigator_path, navigator_content, 'Navigator'))

    # 2. ViewModel
    viewmodel_path = ui_dir / f'{class_name}ViewModel.java'
    viewmodel_content = get_viewmodel_template(class_name, feature_name, description, package, ui_package)
    files_to_create.append((viewmodel_path, viewmodel_content, 'ViewModel'))

    # 3. Fragment
    fragment_path = ui_dir / f'{class_name}Fragment.java'
    fragment_content = get_fragment_template(class_name, feature_name, description, package, ui_package, router_class_name, args.module)
    files_to_create.append((fragment_path, fragment_content, 'Fragment'))

    # 4. Activity
    activity_path = ui_dir / f'{class_name}Activity.java'
    activity_content = get_activity_template(class_name, feature_name, description, package, ui_package, router_class_name, args.module)
    files_to_create.append((activity_path, activity_content, 'Activity'))

    # 5. FragmentModule
    module_path = ui_dir / f'{class_name}FragmentModule.java'
    module_content = get_fragment_module_template(class_name, feature_name, description, package, ui_package)
    files_to_create.append((module_path, module_content, 'FragmentModule'))

    # 6. FragmentProvider
    provider_path = ui_dir / f'{class_name}FragmentProvider.java'
    provider_content = get_fragment_provider_template(class_name, feature_name, description, package, ui_package)
    files_to_create.append((provider_path, provider_content, 'FragmentProvider'))

    # 7. 布局文件
    if not args.no_layout:
        layout_path = layout_dir / f'{get_layout_name(class_name)}.xml'
        layout_content = get_layout_template(class_name, feature_name, package, ui_package, args.module)
        files_to_create.append((layout_path, layout_content, 'Layout'))

    # 输出文件列表
    print(f'将生成以下文件：\n')
    for i, (path, _, type_name) in enumerate(files_to_create, 1):
        print(f'{i}. [{type_name}] {path.relative_to(PROJECT_ROOT)}')

    # Dry run 模式：只显示内容
    if args.dry_run:
        print(f'\n========================================')
        print(f'Dry Run 模式 - 文件内容预览')
        print(f'========================================\n')
        for path, content, type_name in files_to_create:
            print(f'--- {type_name}: {path.name} ---')
            print(content[:500] + '...\n' if len(content) > 500 else content)
            print()
        return

    # 确认写入
    if not args.dry_run:
        print(f'\n是否继续生成？[y/N]: ', end='')
        if input().lower() != 'y':
            print('已取消')
            return

    # 创建目录并写入文件
    print(f'\n开始生成文件...')
    for path, content, type_name in files_to_create:
        ensure_dir(path.parent)
        write_file(path, content)
        print(f'  ✓ {type_name}: {path.name}')

    # 路由配置提示
    if not args.no_router:
        router_api_path = PROJECT_ROOT / module_config['router_api']
        activity_builder_path = PROJECT_ROOT / module_config['activity_builder']

        print(f'\n需要手动添加路由配置：')
        print(f'  1. 在 {router_api_path.relative_to(PROJECT_ROOT)} 中添加路由定义')
        print(f'  2. 在 {activity_builder_path.relative_to(PROJECT_ROOT)} 中注册 Activity')

        # 生成路由代码片段
        router_snippet_path = PROJECT_ROOT / f'{get_layout_name(class_name)}_router_snippet.txt'
        write_file(router_snippet_path, get_router_api_template(class_name, feature_name, description, args.module))
        print(f'\n  路由代码片段已生成到: {router_snippet_path.relative_to(PROJECT_ROOT)}')

    # 字符串资源提示
    if not args.no_layout:
        strings_path = PROJECT_ROOT / module_config['strings_resource']
        print(f'\n需要在 {strings_path.relative_to(PROJECT_ROOT)} 中添加字符串资源：')
        print(f'  <string name="resources_{get_feature_name_snake(feature_name)}_title">{description}</string>')

    print(f'\n========================================')
    print(f'✓ 页面生成完成！')
    print(f'========================================\n')


def main():
    """主函数"""
    try:
        args = parse_args()
        generate_files(args)
    except KeyboardInterrupt:
        print(f'\n\n操作已取消')
        sys.exit(1)
    except Exception as e:
        print(f'\n错误: {e}', file=sys.stderr)
        import traceback
        traceback.print_exc()
        sys.exit(1)


if __name__ == '__main__':
    main()
