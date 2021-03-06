package com.wangrunxiang.loopview;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimmy on 2016/5/27.
 */
public class ImageLoopView extends LoopView {

    private Context mContext;
    private List<View> views = new ArrayList<>();

    public ImageLoopView(Context context) {
        this(context, null);
    }

    public ImageLoopView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageLoopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    private void setImageCount(int count) {
        for (int i=0; i<count; i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            views.add(imageView);
        }
        setViews(views);
    }

    public List<ImageView> getImageViews(int count) {
        setImageCount(count);
        List<ImageView> imageViews= new ArrayList<>();
        for(int i=0; i<views.size(); i++) {
            imageViews.add((ImageView) views.get(i));
        }
        return imageViews;
    }
}
