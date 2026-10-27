# 多渠道打包工具使用说明

本目录包含一套基于 [VasDolly](https://github.com/Tencent/VasDolly) 的多渠道打包工具。VasDolly 的原理是**把渠道信息写入 APK 的签名区块（APK Signature Block）**，而不是修改 APK 内部文件，因此**不需要重新签名**，也不会破坏原有签名，打包速度极快。

## 一、目录结构

```
tools/
├── 多渠道打包工具.app        # macOS 图形应用（双击直接打开图形界面）
├── 运行渠道工具.command       # 终端启动脚本（双击编译并启动图形界面）
├── ChannelTool.java          # 图形界面源码（Swing）
└── VasDolly.jar              # 腾讯 VasDolly 多渠道打包核心 jar
```

## 二、环境要求

- **Java / JDK**：运行图形界面需要 JDK 8 及以上（首次运行 `.command` 脚本时如果本目录没有编译产物，需要 `javac` 编译；`.app` 已自带编译好的 class，只运行不编译）。
- 仅 macOS 使用（`.app` 与 `.command` 均为 macOS 专用）。Windows 下可直接用命令行方式（见下文）。

## 三、启动方式（三选一）

### 方式一：双击 `.app`（推荐，纯图形界面）

直接双击 `tools/多渠道打包工具.app`，即可打开图形界面，无需终端。

### 方式二：双击 `.command` 脚本

双击 `tools/运行渠道工具.command`，会打开一个终端窗口，自动完成「编译 → 启动图形界面」。

> 首次双击如果提示没有权限，右键选择「打开」，或在终端执行：
> ```bash
> chmod u+x "运行渠道工具.command"
> ```

### 方式三：命令行手动运行

```bash
cd tools
javac -encoding UTF-8 ChannelTool.java   # 首次需要编译
java ChannelTool
```

也可以通过 `-Dvasdolly.jar` 手动指定 VasDolly.jar 路径：

```bash
java -Dvasdolly.jar=/绝对路径/VasDolly.jar ChannelTool
```

## 四、图形界面使用步骤

1. **选择加固 APK**：点击「浏览」选择**已经加固并签名完成**的 APK，或手动在输入框粘贴路径。
2. **（可选）检测 APK**：点击「检测」，工具会自动识别加固厂商、包名、版本号、签名方案、签名证书等信息，显示在日志区。**该结果仅供参考，不会拦截打包。**
3. **选择渠道**：在渠道列表中勾选需要打包的渠道；可通过「添加渠道」「删除勾选」「全选 / 取消全选」「恢复默认」管理渠道。
4. **设置输出目录**（可选）：默认输出到加固 APK 所在目录；勾选「自定义输出目录」后可手动指定。
5. **生成多渠道包**：点击「生成多渠道包」，等待进度条结束。每个渠道会生成一个独立的 APK。

生成的文件命名规则为 `<原文件名>_<渠道名>.apk`，输出到指定目录。

## 五、渠道列表

- 渠道列表默认：`common`、`huawei`、`honor`、`vivo`、`oppo`、`tencent`、`baidu`、`xiaomi`、`user_test`。
- 渠道配置、勾选状态、自定义输出目录会自动保存到 `~/.vasdolly_tool.properties`，下次启动自动恢复。

## 六、完整的多渠道打包流程

多渠道打包是整个发布流程的最后一步，**顺序不能颠倒**：

```
1. 打包（资源压缩）    ./gradlew resguardRelease   （或 ./release.sh）
2. 签名                apksigner 对 release APK 签名
3. 加固                上传到第三方加固平台（360 / 腾讯乐固 / 爱加密等）加固，下载加固后的 APK
4. 打渠道              用本工具对「加固 APK」写入渠道，生成多渠道包
```

> 因为 VasDolly 写入渠道依赖 APK 的 v2 签名区块，所以**必须先完成签名与加固**，最后再打渠道。

## 七、命令行方式（不依赖图形界面）

如果不想用图形界面，也可以直接调用 VasDolly.jar：

```bash
# 一次性打多个渠道（逗号分隔）
java -jar tools/VasDolly.jar put \
  -c "common,oppo,vivo,honor" \
  加固后的.apk \
  输出目录
```

项目原有的 `channel.sh` 脚本就是这种用法：它自动查找 `app/build/` 下文件名包含 `jiagu` 的 APK，读取 `app/channel.txt` 中的渠道列表，输出到 `app/build/channel/`：

```bash
./channel.sh
```

## 八、验证渠道是否写入成功

```bash
# 读取 APK 写入的渠道名
java -jar tools/VasDolly.jar get -c 渠道包.apk

# 查看签名方案（V1 / V2 / V3）
java -jar tools/VasDolly.jar get -s 渠道包.apk
```

## 九、常见问题

| 问题 | 说明 |
|------|------|
| 提示「未找到 VasDolly.jar」 | 请确认 `VasDolly.jar` 位于 APK 同级、上级目录的 `tools/` 下，或通过 `-Dvasdolly.jar` 指定。 |
| 提示「请选择有效的加固 APK」 | 所选文件不是有效的 APK，或路径有误。 |
| `.app` 双击打不开 | 需要安装 Java；若提示「未找到 Java 环境」，请先安装 JDK。 |
| 加固检测显示「检测失败」 | 仅影响展示，不影响打包。可能是 APK 无法被 ZipFile 读取。 |
| 打包后无法安装 | 请确认加固 APK 本身已正确签名，且打包流程顺序正确（先签名加固、后打渠道）。 |
