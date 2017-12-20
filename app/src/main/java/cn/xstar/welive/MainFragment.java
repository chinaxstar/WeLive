package cn.xstar.welive;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import butterknife.ButterKnife;
import cn.xstar.welive.util.ViewUtil;

/**
 * @author: xstar
 * @since: 2017-12-13.
 */

public class MainFragment extends Fragment {
    MagicIndicator labels;
    ViewPager pages;
    String titles[] = {"推荐", "星秀", "游戏", "户外", "DIY", "公开课", "推荐", "星秀", "游戏", "户外", "DIY", "公开课"};
    private FragmentPagerAdapter adapter;

    private CommonNavigatorAdapter commonNavigatorAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(view);
        labels = ViewUtil.find(view, R.id.labels);
        pages = ViewUtil.find(view, R.id.pages);
        adapter = new MainFragmentADapter(getFragmentManager());
        pages.setAdapter(adapter);
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigatorAdapter = new CommonNavigatorTitleAdapter();
        commonNavigator.setAdapter(commonNavigatorAdapter);
        labels.setNavigator(commonNavigator);
        ViewPagerHelper.bind(labels, pages);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private class MainFragmentADapter extends FragmentPagerAdapter {
        public MainFragmentADapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MainTypeFragment.newInsatance(titles[position]);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    private class CommonNavigatorTitleAdapter extends CommonNavigatorAdapter {

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public IPagerTitleView getTitleView(Context context, final int i) {
            ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
            colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
            colorTransitionPagerTitleView.setSelectedColor(Color.YELLOW);
            colorTransitionPagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            colorTransitionPagerTitleView.setText(titles[i]);
            colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pages.setCurrentItem(i);
                }
            });
            return colorTransitionPagerTitleView;
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator indicator = new LinePagerIndicator(context);
            indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
            return indicator;
        }
    }
}
