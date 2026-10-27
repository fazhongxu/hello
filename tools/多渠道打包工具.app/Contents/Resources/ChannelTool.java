import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * VasDolly 多渠道打包图形工具（精简版）。
 *
 * 用法：选择加固 APK + 填写渠道列表，点「生成多渠道包」即可。
 *   - 输出默认放在加固 APK 所在目录，也可勾选「自定义输出目录」。
 *   - 选择 APK 后自动显示「加固 / 签名」状态（仅供参考，不拦截打包）。
 *   - VasDolly.jar 自动定位，渠道列表自动保存到 ~/.vasdolly_tool.properties。
 *
 * 编译：javac -encoding UTF-8 ChannelTool.java
 * 运行：java ChannelTool
 */
public class ChannelTool extends JFrame {

    private static final File PREFS_FILE =
            new File(System.getProperty("user.home"), ".vasdolly_tool.properties");

    // ---- 输入 ----
    private final JTextField apkField = new JTextField(30);
    private final JCheckBox customOutputCheck = new JCheckBox("自定义输出目录");
    private final JTextField outputField = new JTextField(30);

    // ---- APK 状态显示 ----
    private final JLabel apkStatusLabel = new JLabel(" ");

    // ---- 渠道 ----
    private final DefaultListModel<ChannelItem> channelModel = new DefaultListModel<>();
    private final CheckboxList channelList = new CheckboxList(channelModel);

    // ---- 日志 ----
    private final JTextArea logArea = new JTextArea();

    private final JButton generateBtn = new JButton("生成多渠道包");
    private final JProgressBar progressBar = new JProgressBar();

    private Process runningProcess;

    public ChannelTool() {
        super("多渠道打包工具");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLookAndFeel();
        buildUi();
        loadPrefs();
        pack();
        setLocationRelativeTo(null);
        setMinimumSize(getSize());
    }

    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
    }

    // ==================== 界面构建 ====================

    private void buildUi() {
        setLayout(new BorderLayout(8, 8));

        // 顶部：APK + 输出目录 + 状态
        JPanel pathPanel = new JPanel(new GridBagLayout());
        pathPanel.setBorder(BorderFactory.createTitledBorder("输入"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 8, 4, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        gbc.gridx = 0; gbc.weightx = 0; pathPanel.add(new JLabel("加固 APK:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; pathPanel.add(apkField, gbc);
        JPanel apkBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        apkBtnPanel.add(button("浏览", this::browseApk));
        apkBtnPanel.add(button("检测", this::detectApk));
        gbc.gridx = 2; gbc.weightx = 0; pathPanel.add(apkBtnPanel, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0; gbc.weightx = 0; pathPanel.add(customOutputCheck, gbc);
        gbc.gridx = 1; gbc.weightx = 1; pathPanel.add(outputField, gbc);
        gbc.gridx = 2; gbc.weightx = 0; pathPanel.add(button("浏览", this::browseOutput), gbc);

        gbc.gridy = 2;
        gbc.gridx = 0; gbc.weightx = 1; gbc.gridwidth = 3; pathPanel.add(apkStatusLabel, gbc);
        gbc.gridwidth = 1;

        // 渠道列表（勾选要打包的渠道）
        JPanel channelPanel = new JPanel(new BorderLayout(4, 4));
        channelPanel.setBorder(BorderFactory.createTitledBorder("渠道列表（勾选要打包的渠道）"));
        channelList.setCellRenderer(new ChannelRenderer());
        channelList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane channelScroll = new JScrollPane(channelList);
        channelScroll.setPreferredSize(new Dimension(520, 170));
        channelPanel.add(channelScroll, BorderLayout.CENTER);
        JPanel channelBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        channelBtnPanel.add(button("全选", this::selectAllChannels));
        channelBtnPanel.add(button("取消全选", this::deselectAllChannels));
        channelBtnPanel.add(button("添加渠道", this::addChannel));
        channelBtnPanel.add(button("删除勾选", this::removeCheckedChannels));
        channelBtnPanel.add(button("恢复默认", this::resetChannels));
        channelPanel.add(channelBtnPanel, BorderLayout.SOUTH);

        // 操作按钮
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        generateBtn.setFont(generateBtn.getFont().deriveFont(Font.BOLD));
        actionPanel.add(generateBtn);
        progressBar.setVisible(false);
        actionPanel.add(progressBar);

        // 日志
        JPanel logPanel = new JPanel(new BorderLayout(4, 4));
        logPanel.setBorder(BorderFactory.createTitledBorder("日志"));
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setPreferredSize(new Dimension(520, 180));
        logPanel.add(logScroll, BorderLayout.CENTER);

        // 组装
        JPanel north = new JPanel();
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
        pathPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        channelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        actionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        north.add(pathPanel);
        north.add(channelPanel);
        north.add(actionPanel);

        add(north, BorderLayout.NORTH);
        add(logPanel, BorderLayout.CENTER);

        // 事件
        generateBtn.addActionListener(e -> doGenerate());
        apkField.addActionListener(e -> {
            updateOutputHint();
            clearLog();
            detectApk();
        });
        customOutputCheck.addItemListener(e -> {
            boolean custom = customOutputCheck.isSelected();
            outputField.setEnabled(custom);
            if (custom) outputField.setText("");
            else updateOutputHint();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                savePrefs();
                if (runningProcess != null) runningProcess.destroy();
            }
        });

        // 初始状态：默认与 APK 同目录
        outputField.setEnabled(customOutputCheck.isSelected());
    }

    private JButton button(String text, Runnable action) {
        JButton b = new JButton(text);
        b.addActionListener(e -> action.run());
        return b;
    }

    // ==================== 配置持久化 ====================

    private void loadPrefs() {
        Properties p = new Properties();
        if (PREFS_FILE.isFile()) {
            try (Reader r = new InputStreamReader(new FileInputStream(PREFS_FILE), StandardCharsets.UTF_8)) {
                p.load(r);
            } catch (IOException ignored) {
            }
            customOutputCheck.setSelected(Boolean.parseBoolean(p.getProperty("custom", "false")));
            outputField.setText(p.getProperty("output", ""));
            outputField.setEnabled(customOutputCheck.isSelected());
        }

        String channelsStr = p.getProperty("channels", null);
        if (channelsStr != null && !channelsStr.trim().isEmpty()) {
            Set<String> selected = new HashSet<>();
            String selectedStr = p.getProperty("selected", "");
            if (selectedStr != null) {
                for (String s : selectedStr.split(",")) if (!s.trim().isEmpty()) selected.add(s.trim());
            }
            channelModel.clear();
            for (String name : channelsStr.split(",")) {
                String n = name.trim();
                if (n.isEmpty()) continue;
                ChannelItem item = new ChannelItem(n);
                item.selected = selected.contains(n);
                channelModel.addElement(item);
            }
        } else {
            addDefaultChannels();
        }
    }

    private void savePrefs() {
        Properties p = new Properties();
        List<String> names = new ArrayList<>();
        List<String> selected = new ArrayList<>();
        for (int i = 0; i < channelModel.size(); i++) {
            ChannelItem it = channelModel.get(i);
            names.add(it.name);
            if (it.selected) selected.add(it.name);
        }
        p.setProperty("channels", String.join(",", names));
        p.setProperty("selected", String.join(",", selected));
        p.setProperty("custom", String.valueOf(customOutputCheck.isSelected()));
        p.setProperty("output", outputField.getText().trim());
        try (Writer w = new OutputStreamWriter(new FileOutputStream(PREFS_FILE), StandardCharsets.UTF_8)) {
            p.store(w, "VasDolly ChannelTool preferences");
        } catch (IOException ignored) {
        }
    }

    // ==================== 核心逻辑 ====================

    private void doGenerate() {
        File apk = new File(apkField.getText().trim());
        if (!apk.isFile()) {
            warn("请选择有效的加固 APK");
            return;
        }

        File jar = resolveJar(apk);
        if (jar == null) {
            warn("未找到 VasDolly.jar。请确认它位于 APK 同级或上级目录的 tools/ 下。");
            return;
        }

        String channels = joinChannels();
        if (channels.isEmpty()) {
            warn("渠道列表为空，请至少填写一个渠道。");
            return;
        }

        File outDir = resolveOutputDir(apk);
        if (outDir == null || outDir.getAbsolutePath().isEmpty()) {
            warn("请填写有效的输出目录");
            return;
        }
        if (!outDir.isDirectory() && !outDir.mkdirs()) {
            warn("无法创建输出目录：" + outDir.getAbsolutePath());
            return;
        }

        List<String> cmd = new ArrayList<>();
        cmd.add("java");
        cmd.add("-jar");
        cmd.add(jar.getAbsolutePath());
        cmd.add("put");
        cmd.add("-c");
        cmd.add(channels);
        cmd.add(apk.getAbsolutePath());
        cmd.add(outDir.getAbsolutePath());

        appendLog("VasDolly.jar: " + jar.getAbsolutePath());
        appendLog("输出目录: " + outDir.getAbsolutePath());
        savePrefs();
        runCommand(cmd, "生成渠道包");
    }

    private File resolveOutputDir(File apk) {
        if (customOutputCheck.isSelected()) {
            String s = outputField.getText().trim();
            return s.isEmpty() ? null : new File(s);
        }
        return apk.getParentFile();
    }

    /** 定位 VasDolly.jar：启动参数 > 自身所在位置 > APK 向上查找 > 工作目录向上查找。 */
    private File resolveJar(File apk) {
        File f = fromSystemProperty();
        if (f != null) return f;
        f = findJarNearSelf();
        if (f != null) return f;
        f = searchUp(apk.isFile() ? apk.getParentFile() : null);
        if (f != null) return f;
        return searchUp(new File(System.getProperty("user.dir")).getAbsoluteFile());
    }

    private File fromSystemProperty() {
        String prop = System.getProperty("vasdolly.jar");
        if (prop != null && !prop.trim().isEmpty()) {
            File f = new File(prop);
            if (f.isFile()) return f;
        }
        return null;
    }

    /** 根据自身 .class 文件的位置，找捆绑在同一目录（或其上一级 / tools/）的 VasDolly.jar。 */
    private File findJarNearSelf() {
        try {
            File base = new File(ChannelTool.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            File parent = base.isDirectory() ? base : base.getParentFile();
            File[] candidates = {
                    new File(base, "VasDolly.jar"),
                    new File(base, "tools/VasDolly.jar"),
                    parent != null ? new File(parent, "VasDolly.jar") : null,
                    parent != null ? new File(parent, "tools/VasDolly.jar") : null,
            };
            for (File c : candidates) {
                if (c != null && c.isFile()) return c;
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private File searchUp(File dir) {
        while (dir != null) {
            File tools = new File(dir, "tools/VasDolly.jar");
            if (tools.isFile()) return tools;
            File direct = new File(dir, "VasDolly.jar");
            if (direct.isFile()) return direct;
            dir = dir.getParentFile();
        }
        return null;
    }

    private String joinChannels() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < channelModel.size(); i++) {
            ChannelItem it = channelModel.get(i);
            if (!it.selected) continue;
            if (sb.length() > 0) sb.append(',');
            sb.append(it.name);
        }
        return sb.toString();
    }

    private void updateOutputHint() {
        if (customOutputCheck.isSelected()) return;
        File apk = new File(apkField.getText().trim());
        outputField.setText(apk.isFile() ? apk.getParent() : "");
    }

    // ==================== APK 加固 / 签名检测（仅显示，不拦截） ====================

    private void detectApk() {
        File apk = new File(apkField.getText().trim());
        if (!apk.isFile()) {
            apkStatusLabel.setText(" ");
            return;
        }
        String jiagu = detectHardening(apk);
        apkStatusLabel.setText("加固: " + jiagu + "　｜　签名: 检测中...");
        new Thread(() -> {
            String fileMd5 = md5OfFile(apk);
            AppInfo appInfo = detectAppInfo(apk);
            String scheme = detectSignatureScheme(apk);
            SigResult cert = detectCertDetail(apk);
            String jiaguFinal = jiagu;
            SwingUtilities.invokeLater(() -> {
                apkStatusLabel.setText("加固: " + jiaguFinal + "　｜　签名: " + scheme);
                appendLog("----- APK 信息 -----");
                if (appInfo != null) {
                    if (appInfo.label != null) appendLog("应用名称: " + appInfo.label);
                    if (appInfo.packageName != null) appendLog("包名: " + appInfo.packageName);
                    if (appInfo.versionName != null) appendLog("版本号: " + appInfo.versionName);
                    if (appInfo.versionCode != null) appendLog("版本 Code: " + appInfo.versionCode);
                }
                appendLog("文件 MD5: " + fileMd5);
                appendLog("签名方案: " + scheme);
                if (cert != null) {
                    if (cert.dn != null) appendLog("签名证书: " + cert.dn);
                    if (cert.md5 != null) appendLog("签名 MD5: " + cert.md5);
                    if (cert.sha1 != null) appendLog("签名 SHA1: " + cert.sha1);
                    if (cert.sha256 != null) appendLog("签名 SHA256: " + cert.sha256);
                }
                appendLog("--------------------");
            });
        }, "apk-detect").start();
    }

    /** 扫描 APK 内部特征文件，识别常见加固厂商。 */
    private String detectHardening(File apk) {
        String[][] vendors = {
                {"360加固", "libjiagu"},
                {"腾讯乐固", "libshella", "libshellx", "libdexhelper"},
                {"爱加密", "ijiami", "libexecmain"},
                {"梆梆加固", "libsecexe", "libsecmain", "libnesec"},
                {"阿里聚安全", "libsgmain", "libsgsecuritybody"},
                {"百度加固", "libbaiduprotect"},
        };
        try (java.util.zip.ZipFile zip = new java.util.zip.ZipFile(apk)) {
            java.util.Enumeration<? extends java.util.zip.ZipEntry> en = zip.entries();
            while (en.hasMoreElements()) {
                String name = en.nextElement().getName().toLowerCase();
                for (String[] v : vendors) {
                    for (int i = 1; i < v.length; i++) {
                        if (name.contains(v[i])) return "已加固（" + v[0] + "）";
                    }
                }
            }
        } catch (Exception e) {
            return "检测失败";
        }
        return "未加固";
    }

    /** 用 aapt dump badging 读取应用名称 / 包名 / 版本号 / versionCode。 */
    private AppInfo detectAppInfo(File apk) {
        File aapt = findBuildTool("aapt");
        if (aapt == null) return null;
        List<String> cmd = new ArrayList<>();
        cmd.add(aapt.getAbsolutePath());
        cmd.add("dump");
        cmd.add("badging");
        cmd.add(apk.getAbsolutePath());
        String out = runAndCapture(cmd);
        if (out == null) return null;
        AppInfo info = new AppInfo();
        for (String line : out.split("\\R")) {
            if (line.startsWith("package:")) {
                info.packageName = extractQuoted(line, "name=");
                info.versionCode = extractQuoted(line, "versionCode=");
                info.versionName = extractQuoted(line, "versionName=");
            } else if (line.startsWith("application-label:'")) {
                if (info.label == null) info.label = extractQuoted(line, "application-label:");
            }
        }
        if (info.label == null && info.packageName == null
                && info.versionName == null && info.versionCode == null) {
            return null;
        }
        return info;
    }

    /** 调用 VasDolly get -s 读取签名方案（V1/V2/V3 等）。 */
    private String detectSignatureScheme(File apk) {
        File jar = resolveJar(apk);
        if (jar == null) return "未知";
        List<String> cmd = new ArrayList<>();
        cmd.add("java");
        cmd.add("-jar");
        cmd.add(jar.getAbsolutePath());
        cmd.add("get");
        cmd.add("-s");
        cmd.add(apk.getAbsolutePath());
        String out = runAndCapture(cmd);
        if (out == null) return "未知";
        if (out.toLowerCase().contains("not signed")) return "未签名";
        int idx = out.indexOf("signature mode:");
        if (idx >= 0) {
            String rest = out.substring(idx + "signature mode:".length()).trim();
            String[] parts = rest.split("\\R");
            if (parts.length > 0 && !parts[0].trim().isEmpty()) return parts[0].trim();
        }
        return "未知";
    }

    /** 读取签名证书详情（DN / MD5 / SHA1 / SHA256），优先 apksigner，回退 keytool。 */
    private SigResult detectCertDetail(File apk) {
        File apksigner = findBuildTool("apksigner");
        if (apksigner != null) {
            List<String> cmd = new ArrayList<>();
            cmd.add(apksigner.getAbsolutePath());
            cmd.add("verify");
            cmd.add("--print-certs");
            cmd.add("--min-sdk-version");
            cmd.add("24");
            cmd.add(apk.getAbsolutePath());
            SigResult r = parseApksigner(runAndCapture(cmd));
            if (r != null) return r;
        }
        // 回退 keytool（仅 V1 签名）
        File keytool = findKeytool();
        if (keytool != null) {
            List<String> cmd = new ArrayList<>();
            cmd.add(keytool.getAbsolutePath());
            cmd.add("-printcert");
            cmd.add("-jarfile");
            cmd.add(apk.getAbsolutePath());
            SigResult r = parseKeytool(runAndCapture(cmd));
            if (r != null) return r;
        }
        return null;
    }

    private SigResult parseApksigner(String out) {
        if (out == null || out.isEmpty()) return null;
        SigResult r = new SigResult();
        for (String line : out.split("\\R")) {
            String v;
            if ((v = extractAfter(line, "certificate dn:")) != null) r.dn = v;
            else if ((v = extractAfter(line, "certificate md5 digest:")) != null) r.md5 = v;
            else if ((v = extractAfter(line, "certificate sha-1 digest:")) != null) r.sha1 = v;
            else if ((v = extractAfter(line, "certificate sha-256 digest:")) != null) r.sha256 = v;
        }
        return (r.dn == null && r.md5 == null && r.sha1 == null && r.sha256 == null) ? null : r;
    }

    private SigResult parseKeytool(String out) {
        if (out == null || out.isEmpty()) return null;
        SigResult r = new SigResult();
        for (String line : out.split("\\R")) {
            String v;
            if ((v = extractAfter(line, "Owner:")) != null) r.dn = v;
            else if ((v = extractAfter(line, "SHA1:")) != null) r.sha1 = v;
            else if ((v = extractAfter(line, "SHA256:")) != null) r.sha256 = v;
        }
        return (r.dn == null && r.sha1 == null && r.sha256 == null) ? null : r;
    }

    private String extractAfter(String line, String key) {
        String low = line.toLowerCase();
        int idx = low.indexOf(key.toLowerCase());
        if (idx < 0) return null;
        return line.substring(idx + key.length()).trim();
    }

    /** 提取 key 后面第一个单引号括起来的内容，例如 name='com.x' -> com.x。 */
    private String extractQuoted(String line, String key) {
        int idx = line.indexOf(key);
        if (idx < 0) return null;
        int start = line.indexOf('\'', idx + key.length());
        if (start < 0) return null;
        int end = line.indexOf('\'', start + 1);
        if (end < 0) return null;
        return line.substring(start + 1, end);
    }

    private File findBuildTool(String toolName) {
        File f = searchBuildTools(System.getenv("ANDROID_HOME"), toolName);
        if (f != null) return f;
        f = searchBuildTools(new File(System.getProperty("user.home"), "Library/Android/sdk").getAbsolutePath(), toolName);
        if (f != null) return f;
        return searchBuildTools(System.getenv("ANDROID_SDK_ROOT"), toolName);
    }

    private File searchBuildTools(String sdk, String toolName) {
        if (sdk == null || sdk.isEmpty()) return null;
        File bt = new File(sdk, "build-tools");
        if (!bt.isDirectory()) return null;
        File[] versions = bt.listFiles();
        if (versions == null) return null;
        Arrays.sort(versions, (a, b) -> b.getName().compareTo(a.getName()));
        for (File v : versions) {
            File tool = new File(v, toolName);
            if (tool.isFile()) return tool;
        }
        return null;
    }

    private File findKeytool() {
        File kt = new File(System.getProperty("java.home"), "bin/keytool");
        if (kt.isFile()) return kt;
        File kt2 = new File("/usr/bin/keytool");
        if (kt2.isFile()) return kt2;
        return null;
    }

    private String runAndCapture(List<String> cmd) {
        try {
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(true);
            Process p = pb.start();
            StringBuilder sb = new StringBuilder();
            try (BufferedReader r = new BufferedReader(
                    new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = r.readLine()) != null) sb.append(line).append('\n');
            }
            p.waitFor();
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    private String md5OfFile(File f) {
        try (InputStream in = new BufferedInputStream(new FileInputStream(f))) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buf = new byte[8192];
            int n;
            while ((n = in.read(buf)) != -1) md.update(buf, 0, n);
            return toHex(md.digest());
        } catch (Exception e) {
            return "计算失败";
        }
    }

    private String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) sb.append(String.format("%02x", b & 0xff));
        return sb.toString();
    }

    private static class SigResult {
        String dn;
        String md5;
        String sha1;
        String sha256;
    }

    private static class AppInfo {
        String label;
        String packageName;
        String versionName;
        String versionCode;
    }

    private static class ChannelItem {
        final String name;
        boolean selected = true;

        ChannelItem(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private static class ChannelRenderer extends JCheckBox implements ListCellRenderer<ChannelItem> {
        @Override
        public Component getListCellRendererComponent(JList<? extends ChannelItem> list, ChannelItem value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            setText(value.name);
            setSelected(value.selected);
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setOpaque(true);
            return this;
        }
    }

    private static class CheckboxList extends JList<ChannelItem> {
        private final DefaultListModel<ChannelItem> model;

        CheckboxList(DefaultListModel<ChannelItem> model) {
            super(model);
            this.model = model;
        }

        @Override
        protected void processMouseEvent(MouseEvent e) {
            if (e.getID() == MouseEvent.MOUSE_PRESSED) {
                int index = locationToIndex(e.getPoint());
                if (index >= 0) {
                    Rectangle r = getCellBounds(index, index);
                    if (r != null && e.getX() < r.x + 22) { // 点复选框：只切换勾选，不选中该行
                        ChannelItem item = model.get(index);
                        item.selected = !item.selected;
                        repaint();
                        return;
                    }
                }
            }
            super.processMouseEvent(e);
        }
    }

    // ==================== 命令执行 ====================

    private void runCommand(List<String> command, String desc) {
        if (runningProcess != null) {
            warn("已有任务在运行，请稍候。");
            return;
        }
        setBusy(true);
        appendLog("$ " + String.join(" ", command));

        new Thread(() -> {
            try {
                ProcessBuilder pb = new ProcessBuilder(command);
                pb.redirectErrorStream(true);
                Process p = pb.start();
                runningProcess = p;
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String l = line;
                        SwingUtilities.invokeLater(() -> appendLog(l));
                    }
                }
                int code = p.waitFor();
                runningProcess = null;
                SwingUtilities.invokeLater(() -> {
                    appendLog("");
                    appendLog(code == 0 ? ">>> " + desc + " 完成" : ">>> " + desc + " 失败，退出码 " + code);
                    setBusy(false);
                });
            } catch (Exception e) {
                runningProcess = null;
                SwingUtilities.invokeLater(() -> {
                    appendLog(">>> 执行出错：" + e.getMessage());
                    setBusy(false);
                });
            }
        }, "vasdolly-worker").start();
    }

    private void setBusy(boolean busy) {
        generateBtn.setEnabled(!busy);
        progressBar.setVisible(busy);
        progressBar.setIndeterminate(busy);
    }

    private void appendLog(String line) {
        logArea.append(line + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    private void clearLog() {
        logArea.setText("");
    }

    // ==================== 渠道管理 ====================

    private static final String[] DEFAULT_CHANNELS = {
            "common", "huawei", "honor", "vivo", "oppo",
            "tencent", "baidu", "xiaomi", "user_test",
    };

    private void addDefaultChannels() {
        for (String name : DEFAULT_CHANNELS) {
            channelModel.addElement(new ChannelItem(name));
        }
    }

    private void addChannel() {
        String input = JOptionPane.showInputDialog(this, "请输入渠道名称，多个用逗号或空格分隔：", "添加渠道", JOptionPane.PLAIN_MESSAGE);
        if (input == null) return; // 取消
        input = input.trim();
        if (input.isEmpty()) return;
        String[] parts = input.split("[,，\\s]+");
        int added = 0;
        for (String p : parts) {
            String name = p.trim();
            if (name.isEmpty() || contains(name)) continue;
            channelModel.addElement(new ChannelItem(name));
            added++;
        }
        if (added == 0) {
            warn("没有新增渠道（可能都已存在）。");
        }
        savePrefs();
    }

    private void removeCheckedChannels() {
        boolean removed = false;
        for (int i = channelModel.size() - 1; i >= 0; i--) {
            if (channelModel.get(i).selected) {
                channelModel.remove(i);
                removed = true;
            }
        }
        if (!removed) {
            warn("没有勾选的渠道可删除，请先勾选要删除的渠道。");
        }
        savePrefs();
    }

    private void selectAllChannels() {
        for (int i = 0; i < channelModel.size(); i++) {
            channelModel.get(i).selected = true;
        }
        channelList.repaint();
        savePrefs();
    }

    private void deselectAllChannels() {
        for (int i = 0; i < channelModel.size(); i++) {
            channelModel.get(i).selected = false;
        }
        channelList.repaint();
        savePrefs();
    }

    private void resetChannels() {
        channelModel.clear();
        addDefaultChannels();
        savePrefs();
    }

    private boolean contains(String name) {
        for (int i = 0; i < channelModel.size(); i++) {
            if (channelModel.get(i).name.equals(name)) return true;
        }
        return false;
    }

    // ==================== 文件选择 ====================

    private void browseApk() {
        File f = chooseFile("选择加固 APK", "apk", new File(apkField.getText().trim()));
        if (f != null) {
            apkField.setText(f.getAbsolutePath());
            updateOutputHint();
            clearLog();
            detectApk();
        }
    }

    private void browseOutput() {
        File dir = chooseDirectory(new File(outputField.getText().trim()));
        if (dir != null) outputField.setText(dir.getAbsolutePath());
    }

    private File chooseDirectory(File start) {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (start != null && start.isDirectory()) fc.setCurrentDirectory(start);
        return fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION ? fc.getSelectedFile() : null;
    }

    private File chooseFile(String title, String ext, File start) {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle(title);
        fc.setFileFilter(new FileNameExtensionFilter("*." + ext, ext));
        if (start != null && start.getParentFile() != null && start.getParentFile().isDirectory()) {
            fc.setCurrentDirectory(start.getParentFile());
        } else if (start != null && start.isDirectory()) {
            fc.setCurrentDirectory(start);
        }
        return fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION ? fc.getSelectedFile() : null;
    }

    // ==================== 杂项 ====================

    private void warn(String msg) {
        JOptionPane.showMessageDialog(this, msg, "提示", JOptionPane.WARNING_MESSAGE);
    }

    // ==================== 入口 ====================

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChannelTool().setVisible(true));
    }
}
