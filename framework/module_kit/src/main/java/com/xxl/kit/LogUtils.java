package com.xxl.kit;


import static com.orhanobut.logger.Logger.ASSERT;
import static com.orhanobut.logger.Logger.DEBUG;
import static com.orhanobut.logger.Logger.ERROR;
import static com.orhanobut.logger.Logger.INFO;
import static com.orhanobut.logger.Logger.VERBOSE;
import static com.orhanobut.logger.Logger.WARN;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.LogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Log 打印工具
 *
 * @author xxl.
 * @date 2021/7/23.
 */
public final class LogUtils {

    //region: 成员变量

    /**
     * 是否是debug模式
     */
    private static boolean sIsDebug;

    //endregion

    //region: 构造函数

    private LogUtils() {

    }

    //endregion

    //region: 提供方法

    /**
     * 初始化
     *
     * @param isDebug
     */
    public static void init(final boolean isDebug,
                            @NonNull final String logTag) {
        sIsDebug = isDebug;

        final FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag(logTag)
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority,
                                      @Nullable String tag) {
                return isDebug;
            }
        });

        CustomFormatStrategy customFormatStrategy = CustomFormatStrategy.newBuilder()
                .tag(logTag)
                .diskPath(PathUtils.getAppIntFilesPath())
                .build();
        Logger.addLogAdapter(new DiskLogAdapter(customFormatStrategy));
    }

    public static void i(@NonNull final String message, Object... objects) {
        Logger.i(message, objects);
    }

    public static void d(@NonNull final String message, Object... objects) {
        Logger.d(message, objects);
    }

    public static void d(@NonNull final String message, boolean printStack) {
        if (printStack) {
            Logger.d(String.format("%s %s", getStackTrace(new Exception()), message));
            return;
        }
        Logger.d(message);
    }

    public static void w(@NonNull final String message, Object... objects) {
        Logger.w(message, objects);
    }

    public static void e(@NonNull final String message, Object... objects) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Logger.e(message, objects);
    }

    public static void e(@NonNull final Object object, Object... objects) {
        if (object instanceof String) {
            Logger.e((String) object);
        } else {
            final String json = GsonUtils.toJson(object);
            if (!TextUtils.isEmpty(json)) {
                Logger.e(json);
            }
        }
    }

    /**
     * 获取任务堆栈信息
     *
     * @return
     */
    public static String getStackTrace(Exception exception) {
        try {
            StackTraceElement stackTraceElement = exception.getStackTrace()[1];
            String methodName = stackTraceElement.getMethodName();
            String fileName = stackTraceElement.getFileName();
            int lineNumber = stackTraceElement.getLineNumber();
            return String.format("class %s method %s lineNumber %s", fileName.substring(0, fileName.indexOf(".")), methodName, lineNumber);
        } catch (Exception e) {

        }
        return "";
    }


    public static class DiskLogAdapter implements LogAdapter {

        @NonNull
        private final FormatStrategy formatStrategy;

        public DiskLogAdapter() {
            formatStrategy = CustomFormatStrategy.newBuilder().build();
        }

        public DiskLogAdapter(@NonNull FormatStrategy formatStrategy) {
            this.formatStrategy = formatStrategy;
        }

        @Override
        public boolean isLoggable(int priority, @Nullable String tag) {
            return true;
        }

        @Override
        public void log(int priority, @Nullable String tag, @NonNull String message) {
            formatStrategy.log(priority, tag, message);
        }
    }

    /**
     * Log formatted file logging for Android.
     * Writes to CSV the following data:
     * epoch timestamp, ISO8601 timestamp (human-readable), log level, tag, log message.
     */
    public static class CustomFormatStrategy implements FormatStrategy {

        private static final String NEW_LINE = System.getProperty("line.separator");
        private static final String NEW_LINE_REPLACEMENT = " <br> ";
        private static final String SEPARATOR = ",";

        @NonNull
        private final Date date;
        @NonNull
        private final SimpleDateFormat dateFormat;
        @NonNull
        private final LogStrategy logStrategy;
        @Nullable
        private final String tag;

        private CustomFormatStrategy(@NonNull CustomFormatStrategy.Builder builder) {
            checkNotNull(builder);

            date = builder.date;
            dateFormat = builder.dateFormat;
            logStrategy = builder.logStrategy;
            tag = builder.tag;
        }

        @NonNull
        public static CustomFormatStrategy.Builder newBuilder() {
            return new CustomFormatStrategy.Builder();
        }

        @Override
        public void log(int priority, @Nullable String onceOnlyTag, @NonNull String message) {
            checkNotNull(message);

            String tag = formatTag(onceOnlyTag);

            date.setTime(System.currentTimeMillis());

            StringBuilder builder = new StringBuilder();

            // machine-readable date/time
            builder.append(Long.toString(date.getTime()));

            // human-readable date/time
            builder.append(SEPARATOR);
            builder.append(dateFormat.format(date));

            // level
            builder.append(SEPARATOR);
            builder.append(logLevel(priority));

            // tag
            builder.append(SEPARATOR);
            builder.append(tag);

            // message
            if (message.contains(NEW_LINE)) {
                // a new line would break the CSV format, so we replace it here
                //message = message.replaceAll(NEW_LINE, NEW_LINE_REPLACEMENT);
            }
            builder.append(SEPARATOR);
            builder.append(message);

            // new line
            builder.append(NEW_LINE);

            logStrategy.log(priority, tag, builder.toString());
        }

        @Nullable
        private String formatTag(@Nullable String tag) {
            if (!TextUtils.isEmpty(tag) && !TextUtils.equals(this.tag, tag)) {
                return this.tag + "-" + tag;
            }
            return this.tag;
        }

        public static final class Builder {
            private static final int MAX_BYTES = 500 * 1024; // 500K averages to a 4000 lines per file

            Date date;
            SimpleDateFormat dateFormat;
            LogStrategy logStrategy;
            String tag = "PRETTY_LOGGER";
            String diskPath;

            private Builder() {
            }

            @NonNull
            public CustomFormatStrategy.Builder date(@Nullable Date val) {
                date = val;
                return this;
            }

            @NonNull
            public CustomFormatStrategy.Builder dateFormat(@Nullable SimpleDateFormat val) {
                dateFormat = val;
                return this;
            }

            @NonNull
            public CustomFormatStrategy.Builder logStrategy(@Nullable LogStrategy val) {
                logStrategy = val;
                return this;
            }

            @NonNull
            public CustomFormatStrategy.Builder tag(@Nullable String tag) {
                this.tag = tag;
                return this;
            }

            public CustomFormatStrategy.Builder diskPath(@Nullable String diskPath) {
                this.diskPath = diskPath;
                return this;
            }

            @NonNull
            public CustomFormatStrategy build() {
                if (date == null) {
                    date = new Date();
                }
                if (dateFormat == null) {
                    dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.UK);
                }
                if (logStrategy == null) {

                    String diskPath = this.diskPath != null ? this.diskPath : Environment.getExternalStorageDirectory().getAbsolutePath();
                    String folder = diskPath + File.separatorChar + "log";

                    HandlerThread ht = new HandlerThread("AndroidFileLogger." + folder);
                    ht.start();
                    Handler handler = new CustomDiskLogStrategy.WriteHandler(ht.getLooper(), folder, MAX_BYTES);
                    logStrategy = new CustomDiskLogStrategy(handler);
                }
                return new CustomFormatStrategy(this);
            }
        }

    }

    /**
     * Abstract class that takes care of background threading the file log operation on Android.
     * implementing classes are free to directly perform I/O operations there.
     * <p>
     * Writes all logs to the disk with log format.
     */
    public static class CustomDiskLogStrategy implements LogStrategy {

        @NonNull
        private final Handler handler;

        public CustomDiskLogStrategy(@NonNull Handler handler) {
            this.handler = checkNotNull(handler);
        }

        @Override
        public void log(int level, @Nullable String tag, @NonNull String message) {
            checkNotNull(message);

            // do nothing on the calling thread, simply pass the tag/msg to the background thread
            handler.sendMessage(handler.obtainMessage(level, message));
        }

        static class WriteHandler extends Handler {

            @NonNull
            private final String folder;
            private final int maxFileSize;

            WriteHandler(@NonNull Looper looper, @NonNull String folder, int maxFileSize) {
                super(checkNotNull(looper));
                this.folder = checkNotNull(folder);
                this.maxFileSize = maxFileSize;
            }

            @SuppressWarnings("checkstyle:emptyblock")
            @Override
            public void handleMessage(@NonNull Message msg) {
                String content = (String) msg.obj;

                FileWriter fileWriter = null;
                File logFile = getLogFile(folder, "logs");

                try {
                    fileWriter = new FileWriter(logFile, true);

                    writeLog(fileWriter, content);

                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                    if (fileWriter != null) {
                        try {
                            fileWriter.flush();
                            fileWriter.close();
                        } catch (IOException e1) { /* fail silently */ }
                    }
                }
            }

            /**
             * This is always called on a single background thread.
             * Implementing classes must ONLY write to the fileWriter and nothing more.
             * The abstract class takes care of everything else including close the stream and catching IOException
             *
             * @param fileWriter an instance of FileWriter already initialised to the correct file
             */
            private void writeLog(@NonNull FileWriter fileWriter, @NonNull String content) throws IOException {
                checkNotNull(fileWriter);
                checkNotNull(content);

                fileWriter.append(content);
            }

            private File getLogFile(@NonNull String folderName, @NonNull String fileName) {
                checkNotNull(folderName);
                checkNotNull(fileName);

                File folder = new File(folderName);
                if (!folder.exists()) {
                    //TODO: What if folder is not created, what happens then?
                    folder.mkdirs();
                }

                int newFileCount = 0;
                File newFile;
                File existingFile = null;

                newFile = new File(folder, String.format("%s_%s.log", fileName, newFileCount));
                while (newFile.exists()) {
                    existingFile = newFile;
                    newFileCount++;
                    newFile = new File(folder, String.format("%s_%s.log", fileName, newFileCount));
                }

                if (existingFile != null) {
                    if (existingFile.length() >= maxFileSize) {
                        return newFile;
                    }
                    return existingFile;
                }

                return newFile;
            }
        }

    }

    public static <T> T checkNotNull(T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        return obj;
    }

    static String logLevel(int value) {
        switch (value) {
            case VERBOSE:
                return "VERBOSE";
            case DEBUG:
                return "DEBUG";
            case INFO:
                return "INFO";
            case WARN:
                return "WARN";
            case ERROR:
                return "ERROR";
            case ASSERT:
                return "ASSERT";
            default:
                return "UNKNOWN";
        }
    }

    //endregion


}