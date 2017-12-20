package cn.xstar.welive;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;

import cn.xstar.welive.util.XLog;

/**
 * @author: xstar
 * @since: 2017-12-14.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        XLog.initLog(this);
//        XLog.getConfig().useFile(false);
        Glide.init(this, new GlideBuilder());
    }
}
