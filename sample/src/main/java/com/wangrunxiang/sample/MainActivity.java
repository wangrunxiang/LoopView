package com.wangrunxiang.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.wangrunxiang.loopview.ImageLoopView;
import com.wangrunxiang.loopview.LoopView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageLoopView loopView = (ImageLoopView) findViewById(R.id.loop_view);
        if (loopView != null) {
            List<ImageView> imageViews = loopView.getImageViews(3);
            for (int i=0; i<imageViews.size(); i++) {
                if (i==0) {
                    imageViews.get(0).setBackgroundColor(0xff00ff00);
                } else if (i==1) {
                    imageViews.get(1).setBackgroundColor(0xffff0000);
                } else if (i==2) {
                    imageViews.get(2).setBackgroundColor(0xff0000ff);
                }
            }
        }
    }
}
