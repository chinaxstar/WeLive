package cn.xstar.welive;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;

import java.security.MessageDigest;
import java.util.Arrays;

import cn.xstar.welive.util.StrUtil;
import cn.xstar.welive.util.ViewUtil;

/**
 * @author: xstar
 * @since: 2017-12-14.
 */

public class MainTypeFragment extends Fragment {
    private String type;
    private String TYPE_STRING_KEY = "cn.xstar.welive.fragment.TYPE_STRING_KEY";
    Integer ints[] = {R.mipmap.view_1, R.mipmap.view_2, R.mipmap.view_3, R.mipmap.view_4, R.mipmap.view_5, R.mipmap.view_6};
    LayoutInflater inflate;
    private Banner banner;
    private RecyclerView live_contents;
    private RecyclerView.Adapter adapter;
    private RequestOptions radiusOption;

    public static MainTypeFragment newInsatance(String type) {
        MainTypeFragment mainTypeFragment = new MainTypeFragment();
        mainTypeFragment.type = type;
        return mainTypeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null) && savedInstanceState.containsKey(TYPE_STRING_KEY)) {
            type = savedInstanceState.getString(TYPE_STRING_KEY);
        }
        inflate = LayoutInflater.from(getContext());
        radiusOption = RequestOptions.bitmapTransform(new RoundedCorners(20));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View convertView = inflate.inflate(R.layout.main_recommend_layout, container, false);
        banner = ViewUtil.find(convertView, R.id.banner);
        live_contents = ViewUtil.find(convertView, R.id.live_contents);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片集合
        //设置holder.banner动画效果
        banner.setImageLoader(new ImageLoaderInterface() {
            @Override
            public void displayImage(Context context, Object path, View imageView) {
                if (path instanceof Integer)
                    imageView.setBackgroundResource((Integer) path);
            }

            @Override
            public View createImageView(Context context) {
                return null;
            }
        });
        banner.setImages(Arrays.asList(ints));
//                banner.setBannerAnimation(Transformer.DepthPage);
//                //设置标题集合（当banner样式有显示title时）
//                banner.setBannerTitles(null);
//                //设置自动轮播，默认为true
//                banner.isAutoPlay(true);
//                //设置轮播时间
//                banner.setDelayTime(1500);
//                //设置指示器位置（当banner模式中有指示器时）
//                banner.setIndicatorGravity(BannerConfig.CENTER);
//                //banner设置方法全部调用完毕时最后调用
        live_contents.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new MyRecyclerAdapter();
        live_contents.setAdapter(adapter);
        return convertView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TYPE_STRING_KEY, type);
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        banner.releaseBanner();
    }

    private class VH extends RecyclerView.ViewHolder {

        ImageView live_pic;
        TextView live_text;

        public VH(View itemView) {
            super(itemView);
            live_pic = ViewUtil.find(itemView, R.id.live_pic);
            live_text = ViewUtil.find(itemView, R.id.live_text);
        }
    }


    private class MyRecyclerAdapter extends RecyclerView.Adapter<VH> {

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(inflate.inflate(R.layout.lives_item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            Glide.with(MainTypeFragment.this).load(ints[position]).apply(radiusOption).into(holder.live_pic);
            holder.live_text.setText(StrUtil.getSB().append("风景 ").append(position).toString());
        }

        @Override
        public int getItemCount() {
            return ints.length;
        }
    }
}
