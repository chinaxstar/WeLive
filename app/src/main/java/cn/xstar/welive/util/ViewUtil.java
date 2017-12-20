package cn.xstar.welive.util;

import android.app.Activity;
import android.view.View;

/**
 * @author: xstar
 * @since: 2017-11-27.
 */

public class ViewUtil {
    public static <T extends View> T find(View v, int id) {
        return v.findViewById(id);
    }
    public static <T extends View> T find(Activity act, int id) {
        return act.findViewById(id);
    }
}
