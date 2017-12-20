package cn.xstar.welive;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xstar.welive.util.StrUtil;
import cn.xstar.welive.util.XLog;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.main)
    Button main;
    @BindView(R.id.nearby)
    Button nearby;
    @BindView(R.id.discover)
    Button discover;
    @BindView(R.id.mine)
    Button mine;
    @BindView(R.id.tabs)
    ConstraintLayout tabs;
    @BindView(R.id.content)
    FrameLayout content;

    MainFragment mainFragment;

    public static int permission_code = 0x10010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= 23)
            requestPermissions(new String[]{
                    "android.permission.WRITE_EXTERNAL_STORAGE",
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.MOUNT_UNMOUNT_FILESYSTEMS"}, permission_code);
        XLog.initLog(this).useFile(false);
        Flowable.just("").map(new Function<String, Object>() {
            @Override
            public Object apply(String s) throws Exception {
                initFragments();
                return "";
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                getSupportFragmentManager().beginTransaction().replace(R.id.content, mainFragment).commit();
            }
        });
        main.setOnClickListener(this);
        nearby.setOnClickListener(this);
        discover.setOnClickListener(this);
        mine.setOnClickListener(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        XLog.logE(StrUtil.getSB().append("W:").append(displayMetrics.widthPixels).append("dp").append("  H:").append(displayMetrics.heightPixels).append("dp").toString());
    }

    private void initFragments() {
        mainFragment = new MainFragment();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nearby:
                startActivity(new Intent(this, PlayerActivity.class));
                break;
        }
    }
}
