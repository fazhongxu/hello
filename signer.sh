#!/usr/bin/env bash
#apk签名命令
#-----------------------------------------------------------------------------------------------------------
#使用说明：方式一：打开Terminal窗口，输入 ./signer.sh 执行命令即可签名
#：       方式二：直接点击第一行左边显示的绿色运行图标即可签名
#如果提示permission denied，使用命令 chmod u+x signer.sh先获取release.sh文件访问权限，再执行./signer.sh 命令签名
#-----------------------------------------------------------------------------------------------------------

#build-tools/lib 下 apksigner.jar
# 安装报错 待解决 https://blog.csdn.net/xys616/article/details/123892541

#Failure [-124: Failed parse during installPackageLI: Targeting R+ (version 30 and above) requires the resources.arsc of installed APKs to be stored uncompressed and aligned on a 4-byte boundary]

java -jar apksigner.jar sign --ks hello --ks-key-alias hello app/build/outputs/apk/release/app-release_100_jiagu.apk


