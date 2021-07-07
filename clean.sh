#!/usr/bin/env bash
### 使用时，Terminal窗口 输入 ./clean.sh 或 ./gradlew clean 即可执行命令，执行后清空 .iml和build文件夹数据
rm -rf .idea
find . -name "*.iml" -type f -exec rm -rf {} \;
find . -name "build" -type d -exec rm -rf {} \;

