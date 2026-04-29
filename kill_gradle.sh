#!/usr/bin/env bash

### 查看当前 Gradle 运行进程
echo "正在查找 Gradle 相关进程..."
ps aux | grep -E '[g]radle|[G]radle' | grep -v grep

# 检查是否有 Gradle 进程在运行
if pgrep -f "gradle" > /dev/null; then
    echo ""
    echo "发现 Gradle 进程，是否要杀掉所有 Gradle 进程？(y/n)"
    read -r answer
    if [[ "$answer" == "y" || "$answer" == "Y" ]]; then
        echo "正在杀掉所有 Gradle 进程..."
        # 方法1：使用 pkill（推荐，更简洁）
        pkill -f "gradle"

        # 方法2：使用 killall（如果系统支持）
        # killall gradle 2>/dev/null

        # 方法3：手动查找并杀掉（备选方案）
        # ps aux | grep -E '[g]radle|[G]radle' | awk '{print $2}' | xargs kill -9 2>/dev/null

        echo "所有 Gradle 进程已被终止"

        # 验证是否已清理干净
        sleep 1
        if pgrep -f "gradle" > /dev/null; then
            echo "警告：仍有 Gradle 进程残留，尝试强制杀掉..."
            pkill -9 -f "gradle"
        else
            echo "确认：所有 Gradle 进程已清理完毕"
        fi
    else
        echo "已取消操作"
    fi
else
    echo "未发现任何运行中的 Gradle 进程"
fi