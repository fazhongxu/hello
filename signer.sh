#!/usr/bin/env bash
#apk签名命令
#-----------------------------------------------------------------------------------------------------------
#使用说明：方式一：打开Terminal窗口，输入 ./signer.sh 执行命令即可签名
#：       方式二：直接点击第一行左边显示的绿色运行图标即可签名
#如果提示permission denied，使用命令 chmod u+x signer.sh先获取release.sh文件访问权限，再执行./signer.sh 命令签名
#-----------------------------------------------------------------------------------------------------------

#/Users/xxl/Library/Android/sdk/build-tools/30.0.3/lib/apksigner.jar
# apksigner

#build-tools/lib 下 apksigner.jar
# 安装报错 待解决 https://blog.csdn.net/xys616/article/details/123892541
#Failure [-124: Failed parse during installPackageLI: Targeting R+ (version 30 and above) requires the resources.arsc of installed APKs to be stored uncompressed and aligned on a 4-byte boundary]
#cd /Users/xxl/Library/Android/sdk/build-tools/30.0.3/
#./zipalign -p -f -v 4 /Users/xxl/AndroidStudioProjects/hello/app/build/outputs/apk/release/app-release_100_jiagu.apk /Users/xxl/AndroidStudioProjects/hello/app/build/outputs/apk/release/app-release_100_1jiagu.apk

#java -jar apksigner.jar sign --ks hello --ks-key-alias hello app/build/outputs/apk/release/app-release_100_1jiagu.apk

# 从 local.properties 文件中读取 SDK 路径
SDK_PATH=$(grep "^sdk.dir=" local.properties | sed 's/^sdk.dir=//')
echo "$SDK_PATH"

# 定义 build.gradle 文件路径
BUILD_GRADLE_PATH="app/build.gradle"

# 检查文件是否存在
if [ ! -f "$BUILD_GRADLE_PATH" ]; then
    echo "文件 $BUILD_GRADLE_PATH 不存在"
    exit 1
fi

# 提取 targetSdkVersion 值
TARGET_SDK_VERSION=$(grep -A 10 "defaultConfig {" "$BUILD_GRADLE_PATH" | grep "targetSdkVersion" | awk '{print $2}' | tr -d ' \t')

# 检查是否找到 targetSdkVersion
if [ -z "$TARGET_SDK_VERSION" ]; then
    echo "未找到 targetSdkVersion"
else
    echo "targetSdkVersion 的值是: $TARGET_SDK_VERSION"
fi

