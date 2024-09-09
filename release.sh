#!/usr/bin/env bash
#apk打包命令
#-----------------------------------------------------------------------------------------------------------
#使用说明：方式一：打开Terminal窗口，输入 ./release.sh 执行命令即可打包
#：       方式二：直接点击第一行左边显示的绿色运行图标即可打包
#如果提示permission denied，使用命令 chmod u+x release.sh先获取release.sh文件访问权限，再执行./release.sh 命令打包
#-----------------------------------------------------------------------------------------------------------

./gradlew resguardRelease

# 检查 Gradle 任务是否成功 执行成功后继续接下来的任务
if [ $? -eq 0 ]; then
  echo "Gradle task completed successfully."

  # 删除旧的渠道包
  rm -rf app/build/outputs/apk/release/channel
  if [  $? -eq 0 ]; then
    echo "remove channel successfully."
  else
    echo "remove channel error."
  fi

  # 执行 Java JAR 文件
  java -jar VasDolly.jar put -c app/channel.txt app/build/outputs/apk/release/app-release.apk app/build/outputs/apk/release/channel

  # 检查 Java 程序是否成功
  if [ $? -eq 0 ]; then
    echo "channel package successfully."
  else
    echo "channel package error."
  fi
else
  echo "Gradle task failed. Exiting."
fi

# vasdolly 多渠道打包说明 执行完上面的命令后得到apk 再执行命令打渠道包

# gradle 方式有版本限制 则用jar命令方式 最新3.0.6 https://github.com/Tencent/VasDolly/blob/master/command/README.md
# java -jar VasDolly.jar help
# java -jar VasDolly.jar put -c "oppo,honor,vivo,xiaomi" app/build/outputs/apk/release/app-release.apk app/build/outputs/apk/release/channel