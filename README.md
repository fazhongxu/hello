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

5.

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