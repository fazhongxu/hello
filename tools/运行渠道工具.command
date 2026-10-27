#!/bin/bash
# 双击运行：编译并启动多渠道打包工具（会打开一个终端窗口）
# 建议和 ChannelTool.java 放在同一个目录（比如 Android 工程根目录）。

cd "$(dirname "$0")" || exit 1

JAVA="/usr/bin/java";   [ -x "$JAVA" ]  || JAVA="java"
JAVAC="/usr/bin/javac"; [ -x "$JAVAC" ] || JAVAC="javac"

if [ ! -f ChannelTool.class ] || [ ChannelTool.java -nt ChannelTool.class ]; then
    echo "正在编译 ChannelTool.java ..."
    "$JAVAC" -encoding UTF-8 ChannelTool.java || {
        echo "编译失败，请检查上方错误信息。"
        read -r -p "按回车退出..." _
        exit 1
    }
fi

"$JAVA" ChannelTool
