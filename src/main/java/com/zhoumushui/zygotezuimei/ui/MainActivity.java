package com.zhoumushui.zygotezuimei.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;

import com.handmark.pulltorefresh.library.extras.viewpager.PullToRefreshViewPager;
import com.zhoumushui.zygotezuimei.R;
import com.zhoumushui.zygotezuimei.bean.Card;
import com.zhoumushui.zygotezuimei.control.CardPagerAdapter;
import com.zhoumushui.zygotezuimei.control.IRhythmItemListener;
import com.zhoumushui.zygotezuimei.control.RhythmAdapter;
import com.zhoumushui.zygotezuimei.control.RhythmLayout;
import com.zhoumushui.zygotezuimei.util.AnimatorUtils;
import com.zhoumushui.zygotezuimei.view.ViewPagerScroller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {

    /**
     * 钢琴布局
     */
    private RhythmLayout rhythmLayout;

    /**
     * 钢琴布局的适配器
     */
    private RhythmAdapter rhythmAdapter;

    /**
     * 接收PullToRefreshViewPager中的ViewPager控件
     */
    private ViewPager mViewPager;

    /**
     * 可以侧拉刷新的ViewPager，其实是一个LinearLayout控件
     */
    private PullToRefreshViewPager pullToRefreshViewPager;

    /**
     * ViewPager的适配器
     */
    private CardPagerAdapter cardPagerAdapter;

    /**
     * 最外层的View，为了设置背景颜色而使用
     */
    private View layoutMain;

    private List<Card> mCardList;

    /**
     * 记录上一个选项卡的颜色值
     */
    private int mPreColor;


    private IRhythmItemListener iRhythmItemListener = new IRhythmItemListener() {
        @Override
        public void onSelected(final int position) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    mViewPager.setCurrentItem(position);
                }
            }, 100L);
        }
    };

    private ViewPager.OnPageChangeListener onPageChangeListener =
            new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset,
                                           int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    int currColor = mCardList.get(position).getBackgroundColor();
                    AnimatorUtils.showBackgroundColorAnimation(layoutMain, mPreColor, currColor,
                            400);
                    mPreColor = currColor;

                    layoutMain.setBackgroundColor(mCardList.get(position).getBackgroundColor());
                    rhythmLayout.showRhythmAtPosition(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();
    }

    private void initial() {
        //实例化控件
        layoutMain = findViewById(R.id.layoutMain);
        rhythmLayout = (RhythmLayout) findViewById(R.id.rhythmLayout);
        pullToRefreshViewPager = (PullToRefreshViewPager) findViewById(R.id.pullToRefreshViewPager);
        //获取PullToRefreshViewPager中的ViewPager控件
        mViewPager = pullToRefreshViewPager.getRefreshableView();
        //设置钢琴布局的高度 高度为钢琴布局item的宽度+10dp
        int height = (int) rhythmLayout.getRhythmItemWidth() + (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 10.0F, getResources().getDisplayMetrics());
        rhythmLayout.getLayoutParams().height = height;

        ((RelativeLayout.LayoutParams) this.pullToRefreshViewPager.getLayoutParams()).bottomMargin
                = height;

        mCardList = new ArrayList<Card>();
        for (int i = 0; i < 30; i++) {
            Card card = new Card();
            card.setBackgroundColor((int) -(Math.random() * (16777216 - 1) + 1)); // 随机生成颜色值
            mCardList.add(card);
        }
        //设置ViewPager的适配器
        cardPagerAdapter = new CardPagerAdapter(getSupportFragmentManager(), mCardList);
        mViewPager.setAdapter(cardPagerAdapter);
        //设置钢琴布局的适配器
        rhythmAdapter = new RhythmAdapter(this, mCardList);
        rhythmLayout.setAdapter(rhythmAdapter);

        //设置ViewPager的滚动速度
        setViewPagerScrollSpeed(this.mViewPager, 400);

        //设置控件的监听
        rhythmLayout.setRhythmListener(iRhythmItemListener);
        mViewPager.setOnPageChangeListener(onPageChangeListener);
        //设置ScrollView滚动动画延迟执行的时间
        rhythmLayout.setScrollRhythmStartDelayTime(400);

        //初始化时将第一个键帽弹出,并设置背景颜色
        rhythmLayout.showRhythmAtPosition(0);
        mPreColor = mCardList.get(0).getBackgroundColor();
        layoutMain.setBackgroundColor(mPreColor);
    }

    /**
     * 设置ViewPager的滚动速度，即每个选项卡的切换速度
     *
     * @param viewPager ViewPager控件
     * @param speed     滚动速度，毫秒为单位
     */
    private void setViewPagerScrollSpeed(ViewPager viewPager, int speed) {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            ViewPagerScroller viewPagerScroller = new ViewPagerScroller(viewPager.getContext(),
                    new OvershootInterpolator(0.6F));
            field.set(viewPager, viewPagerScroller);
            viewPagerScroller.setDuration(speed);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
