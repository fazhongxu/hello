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


 技术要点

 1.mvvm 架构 dagger2 运用 ，dagger2+Retrofit+okhttp 搭建网络框架，ARouter 实现路由

 2.aspectjx 实现AOP面向切面编程，try catch 、登录检测、网络检测等

 3.nexus搭建私服,上传本地核心库和对应模块库，实现切换本地和远程库代码

 4.实现本地aar和远程aar切换

 5.objectbox mmkv 存储封装

 6.音频录制封装，支持aac 和 mp3 格式录制

 7.FFmpeg 音频转码、添加背景音乐、调节音频音量、视频中提出音频

 8.实现图片压缩和视频压缩三方代码隔离，tbs简单封装，引入一些常用工具类，简单封装

 9.调通Jenkins自动化打包，打包失败发送消息到钉钉，打包成功把apk 发送到钉钉机器人

 10.引入AndRes 实现资源压缩和一键打包

 11.隐私政策同意后初始化三方框架









