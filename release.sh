#!/usr/bin/env bash
#apk打包命令
#-----------------------------------------------------------------------------------------------------------
#使用说明：方式一：打开Terminal窗口，输入 ./release.sh 执行命令即可打包
#：       方式二：直接点击第一行左边显示的绿色运行图标即可打包
#如果提示permission denied，使用命令 chmod u+x release.sh先获取release.sh文件访问权限，再执行./release.sh 命令打包
#-----------------------------------------------------------------------------------------------------------
./gradlew resguardRelease
#./gradlew resguardHuaweiRelease 如果只是需要华为渠道，可以把这个放开，把上面的注释掉，否则就会打多渠道的包
#./gradlew app:resguardRelease 如果一个项目有多个app，加上app的项目名称即可