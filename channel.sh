#!/usr/bin/env bash
#apk多渠道打包命令
#-----------------------------------------------------------------------------------------------------------
#使用说明：方式一：打开Terminal窗口，输入 ./channel.sh 执行命令即可打包
#：       方式二：直接点击第一行左边显示的绿色运行图标即可打包
#如果提示permission denied，使用命令 chmod u+x channel.sh先获取channel.sh文件访问权限，再执行./channel.sh 命令打包
#-----------------------------------------------------------------------------------------------------------

# 删除旧的渠道包
rm -rf app/build/outputs/apk/release/channel

if [  $? -eq 0 ]; then
  echo "remove channel successfully."
else
  echo "remove channel error."
fi

# 定义搜索路径和目标目录
SEARCH_DIR="app/build/outputs/apk/release/"
TARGET_DIR="app/build/outputs/apk/release/channel"

# 查找包含 'jiagu' 字符串的 APK 文件
APK_FILE=$(find "$SEARCH_DIR" -name "*jiagu*.apk")

# 检查是否找到了文件
if [ -z "$APK_FILE" ]; then
  echo "No APK file containing 'jiagu' found in $SEARCH_DIR"
  exit 1
fi

# 执行 Java JAR 文件
java -jar VasDolly.jar put -c app/channel.txt "$APK_FILE" "$TARGET_DIR"

# 检查 Java 程序是否成功
if [ $? -eq 0 ]; then
  echo "channel package successfully."
else
  echo "channel package error."
fi

# vasdolly 多渠道打包说明 执行完上面的命令后得到apk 再执行命令打渠道包

# gradle 方式有版本限制 则用jar命令方式 最新3.0.6 https://github.com/Tencent/VasDolly/blob/master/command/README.md
# java -jar VasDolly.jar help
# java -jar VasDolly.jar put -c "oppo,honor,vivo,xiaomi" app/build/outputs/apk/release/app-release.apk app/build/outputs/apk/release/channel