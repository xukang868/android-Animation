package com.xiaohan.animationtest;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Time: 2019/3/28 19:50
 * Author: xiaohan
 */
public class DrawableActivity extends AppCompatActivity {
    @BindView(R.id.drawableShow)
    ImageView mDrawableShow;
    @BindView(R.id.start)
    Button mStart;
    @BindView(R.id.stop)
    Button mStop;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.title)
    TextView mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);
        ButterKnife.bind(this);
        mTitle.setText(getText(R.string.drawable_name));
        doAnimation(getAnimationDrawable(true), false);
    }

    @OnClick({R.id.start, R.id.stop,R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.start:
                doAnimation(getAnimationDrawable(true), true);
                break;
            case R.id.stop:
                doAnimation(getAnimationDrawable(true), false);
                break;
        }
    }

    private void doAnimation(AnimationDrawable animationDrawable, boolean isRun) {
        if (animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
        //When you want to restart the animation, stop the animation first.
        if(isRun){
            animationDrawable.start();
        }
    }


    private AnimationDrawable getAnimationDrawable(boolean fromXml) {
        if (fromXml) {
            AnimationDrawable animationDrawable = (AnimationDrawable) mDrawableShow.getDrawable();
            return animationDrawable;
        } else {
            AnimationDrawable animationDrawable = new AnimationDrawable();
            for (int i = 1; i < 8; i++) {
                int id = getResources().getIdentifier("run" + i, "drawable", getPackageName());
                Drawable drawable = getDrawable(id);
                if (null != drawable) {
                    animationDrawable.addFrame(drawable, 100);
                }
            }
            mDrawableShow.setImageDrawable(animationDrawable);
            return animationDrawable;
        }
    }
}
