package com.wangrunxiang.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.wangrunxiang.loopview.LoopView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoopView loopView = (LoopView) findViewById(R.id.loop_view);
        List<View> views = new ArrayList<>();
        for (int i=0; i<3; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            if (i==0) {
                imageView.setBackgroundColor(0xff00ff00);
            } else if (i==1) {
                imageView.setBackgroundColor(0xffff0000);
            } else if (i==2) {
                imageView.setBackgroundColor(0xff0000ff);
            }
            final int finalI = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "点击了"+ finalI, Toast.LENGTH_SHORT).show();
                }
            });
            views.add(imageView);
        }
        if (loopView!=null)loopView.setViews(views);
    }
}
