package com.wangrunxiang.loopview;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Created by aipai on 2015/10/29.
 */
public class LoopHandler implements ViewPager.OnPageChangeListener {

    private long duration = 3000;

    private boolean shouldAutoScroll = true;
    private ViewPager viewPager = null;
    private int viewCount = 0;

    private final Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (shouldAutoScroll) {
                setCurrentItem(getNextItemIndex(), true);
                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0, duration);
            }
        }
    };

    public void setDuration(long duration) {
        this.duration = duration;
    }

    private boolean directionToRight = true;

    private int getNextItemIndex() {
        int nextItem;
        final int currentItem = viewPager.getCurrentItem();
        if (directionToRight) {
            nextItem = currentItem + 1;
            if (nextItem >= viewCount) {
//                nextItem = currentItem - 1;
//                directionToRight = true;
                nextItem = 0;
            }
        } else {
            nextItem = currentItem - 1;
            if (nextItem < 0) {
                nextItem = currentItem + 1;
                directionToRight = true;
            }
        }
        return nextItem;
    }

    private int beforeItem = 0;

    private void setCurrentItem(int currentItem, final boolean auto) {
        if (auto) {
            viewPager.setCurrentItem(currentItem);
        }
        if (shouldHoldDirection) {
            if (currentItem > beforeItem) {
                directionToRight = true;
            } else if (currentItem < beforeItem){
                directionToRight = false;
            }
            shouldHoldDirection = false;
        }
        beforeItem = currentItem;
    }

    private boolean shouldHoldDirection = false;

    public void start(final Context context, final ViewPager viewPager, final int viewCount) {
        setAutoScrollDisable();
        if (this.viewPager == null) {
            this.viewPager = viewPager;
            final FrameScroller scroller = new FrameScroller(context);
            scroller.initScroller(viewPager);
        }
        this.viewCount = viewCount;
        this.viewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    shouldHoldDirection = true;
                    shouldAutoScroll = true;
                    handler.sendEmptyMessageDelayed(0, duration);
                } else {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        beforeItem = viewPager.getCurrentItem();
                    }
                    handler.removeMessages(0);
                    shouldAutoScroll = false;
                }
                return false;
            }
        });
        if (viewCount < 2) {
            return;
        }
        setShouldAutoScroll();
    }

    private void setAutoScrollDisable() {
        handler.removeMessages(0);
        shouldAutoScroll = false;
    }

    private void setShouldAutoScroll() {
        handler.removeMessages(0);
        shouldAutoScroll = true;
        handler.sendEmptyMessageDelayed(0, duration);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setCurrentItem(position, false);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class FrameScroller extends Scroller {

        private int DURATION_TIME = 1200;

        public FrameScroller(Context context) {
            super(context);
        }

        public void initScroller(final ViewPager viewPager) {
            try {
                Field mScroller = ViewPager.class.getDeclaredField("mScroller");
                mScroller.setAccessible(true);
                mScroller.set(viewPager, this);
            } catch (NoSuchFieldException e) {

            } catch (IllegalArgumentException e) {

            } catch (IllegalAccessException e) {
            }
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, shouldAutoScroll ? DURATION_TIME : duration);
        }
    }
}
