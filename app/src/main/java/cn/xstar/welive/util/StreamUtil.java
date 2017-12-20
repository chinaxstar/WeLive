package cn.xstar.welive.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * @author: xstar
 * @since: 2017-11-28.
 */

public class StreamUtil {
    public static OutputStreamWriter writer;
    public static InputStreamReader reader;

    public static void writeFile(File file, String text) {
        try {
            writeStream(new FileOutputStream(file), text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeStream(OutputStream outputStream, String text) {
        try {
            writer = new OutputStreamWriter(outputStream);
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(File file) {
        try {
            return readStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String readStream(InputStream inputStream) {
        reader = new InputStreamReader(inputStream);
        char[] chars = new char[1024];
        StringBuffer sb = StrUtil.getSB();
        try {
            int len = -1;
            while ((len = reader.read(chars)) != -1) {
                sb.append(chars);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close() {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
