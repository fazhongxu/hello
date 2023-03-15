# hello_nexus
hello_nexus

1.安装nexus，配置好环境变量

2.启动nexus ./nexus run / nexus start

3.进入网页 http://localhost:8081 登录nexus

4.创建仓库

hello_nexus
maven2
hosted
http://127.0.0.1:8081/repository/hello_nexus/

5.配置上传代码，需要上传的话点击AndroidStudio的右侧 Gradle ->找到对应的模块 Tasks-> upload 双击 uploadArchives

apply plugin: 'maven'

uploadArchives {
    repositories.mavenDeployer {
        // 修改为自己创建的仓库地址
        repository(url: "http://127.0.0.1:8081/repository/hello_nexus/") {
            authentication(userName: "admin", password: "admin123")
        }
        pom.version = "1.0.1"
        pom.artifactId = "hello_nexus"
        pom.groupId = "com.xxl.common"
        pom.packaging = 'aar'
    }
}

6.使用
 repositories {
        //使用的时候添加仓库
        maven {
            url "http://127.0.0.1:8081/repository/hello_nexus/"
        }
 }

 // 私服使用
 implementation 'com.xxl.common:hello_nexus:1.0.1@aar'
 // 本地
 //implementation project(path: ':module_common')

 7.观察效果
 // 需要体验是否使用本地library 可以通过修改 config.gradle versions.module_common_remote = false 来切换
 // 观察MainActivity内的文件引用到了本地代码还是远程的私服aar文件


 8.命令打包

 ./gradlew resguardRelease

 9.adb查看页面信息

 查看当前栈顶的Activity
 adb shell dumpsys activity | grep "mFocusedActivity"

 查看当前栈顶的Activity的Fragment
 adb shell dumpsys activity your.package.name
 或adb shell dumpsys activity top

 搜索执行结果中，fragment 状态
 mUserVisibleHint= true 的就是当前显示的fragment

 查看依赖关系 并输出log到指定txt文件
 gradlew app:dependencies >app/build/deps.txt

 查看本地模块依赖关系 并输出log到指定txt文件 grep 正则过滤一下
 gradlew :app:dependencies | grep project > app/build/deps.txt

 命令签名
 方式一：

 jarsigner -keystore 密钥库名 xxx.apk 密钥别名

 方式二：

 apksigner sign --ks 密钥库名(或者密钥文件完整路径）--ks-key-alias 密钥别名 xxx.apk

 上述命令，签名后没有改变文件名称。如何判断是否签名成功？可以采用如下命令。

 打印签名信息命令：

 keytool -list -printcert -jarfile appname.apk

 通过上述命令，可以打印出签名的所有者信息，发布者，序列号，有效期等信息。
 通过以上信息，即可判断出是否签名成功。

 jarsigner -verbose -keystore /Users/xxl/AndroidStudioProjects/hello/hello /Users/xxl/AndroidStudioProjects/hello/app/app-debug.apk hello

 keytool -list -printcert -jarfile /Users/xxl/AndroidStudioProjects/hello/app/app-debug.apk

 fat-aar 将多个本地aar合并到一个module的aar中

 // 如：将 ffmpeg_kit，picture_selector打包到modules:module_user模块中
 1.modules:module_user build.gradle 配置如下
 dependencies {
     embed deps.ffmpeg_kit
     embed deps.picture_selector
 }

 2.modules:module_user apply plugin: 'com.kezong.fat-aar'

 3. ./gradlew :modules:module_user:assemble 就可以在build/outpuss/aar文件内找到带有本地两个aar的用户模块的aar了

 技术要点

 1.mvvm 架构 dagger2 运用 ，dagger2+Retrofit+okhttp 搭建网络框架，ARouter 实现路由

 2.aspectjx 实现AOP面向切面编程，try catch 、登录检测、网络检测等

 3.nexus搭建私服,上传本地核心库和对应模块库，实现切换本地和远程库代码

 4.实现本地aar和远程aar切换

 5.objectbox mmkv 存储封装

 6.音频录制封装，支持aac 和 mp3 格式录，音乐播放器

 7.FFmpeg 音频转码、添加背景音乐、调节音频音量、视频中提出音频

 8.实现图片压缩和视频压缩三方代码隔离，tbs简单封装，引入一些常用工具类，简单封装

 9.调通Jenkins自动化打包，打包失败发送消息到钉钉，打包成功把apk 发送到钉钉机器人

 10.引入AndRes 实现资源压缩和一键打包

 11.隐私政策同意后初始化三方框架

 12.封装下载服务

 13.封装统一分享弹窗和统一操作

 14.封装统一分享入口，可以实现自由设置分享类型和自定义分享操作事件

 15.封装列表刷新和分页功能，封装列表基础适配器、拖拽、结合DataBinding、自带头部的适配器

 16.使用运行时注解处理器AbstractProcessor创建一些类，绕过微信支付回调必须在app模块创建wxapi目录实现支付功能

 17.封装项目常用的自定义Toolbar，通过自定义属性配置toolbar左边图标样式，标题文字颜色，处理点击事件等

 18.封装Scheme统一处理，统一处理app scheme参数解析、登录判断和跳转页面逻辑











