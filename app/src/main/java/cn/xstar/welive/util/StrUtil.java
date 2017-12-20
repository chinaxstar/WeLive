package cn.xstar.welive.util;

/**
 * @author: xstar
 * @since: 2017-11-28.
 */

public class StrUtil {
    private static StringBuffer builder;

    public static StringBuffer getSB() {
        if (builder == null) builder = new StringBuffer();
        else
            builder.setLength(0);
        return builder;
    }

    public static String arrStr(String[] strings, String split) {
        getSB();
        builder.append("]");
        for (String s : strings) {
            builder.append(s).append(split);
        }
        int len = builder.length();
        if (len > 2)
            builder.delete(len - 1, len);
        builder.append("]");
        return builder.toString();
    }
}
