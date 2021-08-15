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

