package com.wangrunxiang.loopview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.wangrunxiang.loopview.widget.viewPagerIndicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimmy on 2016/5/26.
 */
public class LoopView extends RelativeLayout {

    private float mCirclePointRadius, mCirclePointRange;
    private int mCirclePointColor, mCirclePointSelectedColor, mCirclePointStrokeColor;
    private ViewPager mViewPager;
    private CirclePageIndicator mCirclePageIndicator;
    private Context mContext;
    private LoopHandler loopHandler;
    private List<View> views = new ArrayList<>();

    public LoopView(Context context) {
        this(context, null);
    }

    public LoopView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttribute(context, attrs, defStyleAttr);
        initViewPager();
        initCirclePageIndicator();
    }

    private void initViewPager() {
        mViewPager = new ViewPager(mContext);
        mViewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mViewPager.setAdapter(new ViewPagerAdapter(views));
        addView(mViewPager);
    }

    private void initCirclePageIndicator() {
        mCirclePageIndicator = new CirclePageIndicator(mContext);
        mCirclePageIndicator.setRadius(mCirclePointRadius);
        mCirclePageIndicator.setGap(mCirclePointRange);
        mCirclePageIndicator.setFillColor(mCirclePointSelectedColor);
        mCirclePageIndicator.setPageColor(mCirclePointColor);
        mCirclePageIndicator.setStrokeColor(mCirclePointStrokeColor);
        mCirclePageIndicator.setViewPager(mViewPager);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(CENTER_HORIZONTAL, TRUE);
        layoutParams.addRule(ALIGN_PARENT_BOTTOM, TRUE);
        mCirclePageIndicator.setLayoutParams(layoutParams);
        addView(mCirclePageIndicator);
        loopHandler = new LoopHandler();
        loopHandler.setDuration(3000);
        mCirclePageIndicator.setOnPageChangeListener(loopHandler);
        loopHandler.start(mContext, mViewPager, mViewPager.getAdapter().getCount());
    }

    private void initAttribute(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoopView, defStyleAttr, 0);
        mCirclePointRadius = typedArray.getDimension(R.styleable.LoopView_circlePointSize, 10);
        mCirclePointColor = typedArray.getColor(R.styleable.LoopView_circlePointColor, Color.parseColor("#b2ffffff"));
        mCirclePointStrokeColor = typedArray.getColor(R.styleable.LoopView_circlePointStrokeColor, Color.parseColor("#b2ffffff"));
        mCirclePointSelectedColor = typedArray.getColor(R.styleable.LoopView_circlePointSelectedColor, Color.parseColor("#b2ffff00"));
        mCirclePointRange = typedArray.getDimension(R.styleable.LoopView_circlePointRange, 30);
        typedArray.recycle();
    }

    public void setViews(List<View> views) {
        this.views.addAll(views);
        mViewPager.getAdapter().notifyDataSetChanged();
        loopHandler.start(mContext, mViewPager, mViewPager.getAdapter().getCount());
    }

    public interface LoopViewOnClickListener {
        void onClick(int position, View view);
    }
}
