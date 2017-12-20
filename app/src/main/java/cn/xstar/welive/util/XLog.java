package cn.xstar.welive.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: xstar
 * @since: 2017-11-28.
 */

public class XLog {

    private static ExecutorService logService = Executors.newCachedThreadPool();
    private static ConfigBuilder config;

    public static ConfigBuilder initLog(Context context) {
        StringBuffer sb = new StringBuffer();
        if (Environment.MEDIA_MOUNTED.equals(
                Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            sb.append(Environment.getExternalStorageDirectory().getPath());
        }
        sb.append("/").append(context.getPackageName()).append("/xlog/");
        File dirFile = new File(sb.toString());
        if (!dirFile.exists()) dirFile.mkdirs();
        sb.append("xlogs_").append(DateUtils.currentStr("yyyy_MM_dd")).append(".txt");
        File tempFile = new File(sb.toString());
        try {
            if (!tempFile.exists())
                tempFile.createNewFile();
        } catch (IOException e) {
        }
        boolean haveFile = tempFile.exists();
        config = new ConfigBuilder().useConsole(true).useFile(haveFile).logFile(tempFile);
        return config;
    }

    public static void logV(String msg) {
        logLv(msg, "V");
    }

    public static void logD(String msg) {
        logLv(msg, "D");
    }

    public static void logW(String msg) {
        logLv(msg, "W");
    }

    public static void logI(String msg) {
        logLv(msg, "I");
    }

    public static void logE(String msg) {
        logLv(msg, "E");
    }

    public static void logA(String msg) {
        logLv(msg, "A");
    }

    public static void logLv(String msg, String lv) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        StringBuffer sb = StrUtil.getSB();
        sb.append(lv).append(" ").append(DateUtils.currentStr()).append(" ");
        if (elements != null && elements.length > 4) {
            StackTraceElement element = elements[4];
            sb.append(element.getClassName()).append("#").append(element.getMethodName()).append("#").append(element.getLineNumber());
        }
        sb.append(" ===> ").append(msg).append("\r\n");
        log(sb.toString(), config.useFile, config.useConsole);
    }

    public static void log(final String log, final boolean isFile, final boolean isConso) {
        logService.execute(new Runnable() {
            @Override
            public void run() {
                if (isFile) {
                    File file = getLogFile();
                    logF(log, file);
                }
                if (isConso) logC(log);
            }
        });
    }

    private static synchronized void logC(String log) {
        System.out.println(log);
    }

    private static synchronized void logF(String log, File file) {
        if (file == null) return;
        if (log == null) return;
        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        try {
            fileOutputStream = new FileOutputStream(file, true);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(log);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStreamWriter != null) {
                    outputStreamWriter.flush();
                    outputStreamWriter.close();
                }
                if (fileOutputStream != null)
                    fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File getLogFile() {
        return config.getLogFile();
    }

    public static ConfigBuilder getConfig() {
        return config;
    }

    public static class ConfigBuilder {
        private boolean useFile = false;
        private boolean useConsole = false;
        private File logFile;
        private int logFileLimit = 20 * 1024 * 1024;

        public ConfigBuilder useFile(boolean useFile) {
            this.useFile = useFile;
            return this;
        }

        public ConfigBuilder useConsole(boolean useConsole) {
            this.useConsole = useConsole;
            return this;
        }

        public ConfigBuilder logFile(File logFile) {
            this.logFile = logFile;
            return this;
        }

        public boolean isUseFile() {
            return useFile;
        }

        public boolean isUseConsole() {
            return useConsole;
        }

        public File getLogFile() {
            return logFile;
        }

        public int getLogFileLimit() {
            return logFileLimit;
        }
    }
}
