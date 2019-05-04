package com.xiaohan.animationtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Time: 2019/3/16 15:49
 * Author: xiaohan
 */
public class CommonActivity extends AppCompatActivity {
    private static final String TAG = "CommonActivity";
    @BindView(R.id.alpha)
    Button mAlpha;
    @BindView(R.id.roatate)
    Button mRoatate;
    @BindView(R.id.scale)
    Button mScale;
    @BindView(R.id.translate)
    Button mTranslate;
    @BindView(R.id.set)
    Button mSet;
    @BindView(R.id.viewShow)
    ImageView mViewShow;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.showPOP)
    TextView mShowPOP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        ButterKnife.bind(this);
        mTitle.setText(getString(R.string.view_name));
        //插值器 在xml中必须用于set目录中
        mShowPOP.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.back, R.id.alpha, R.id.roatate, R.id.scale, R.id.translate, R.id.set,R.id.showPOP})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.alpha:
                doAnimation(getAlphaAnimation(), mViewShow);
                break;
            case R.id.roatate:
                doAnimation(getRotateAnimation(), mViewShow);
                break;
            case R.id.scale:
                doAnimation(getScaleAnimation(), mViewShow);
                break;
            case R.id.translate:
                doAnimation(getTranslateAnimation(), mViewShow);
                break;
            case R.id.set:
                doAnimation(getAnimationSet(true), mViewShow);
                break;
            case R.id.showPOP:
                showPop();
                break;
        }
    }
    PopupWindow popupWindow = null;
    private void showPop() {
        final View view = LayoutInflater.from(CommonActivity.this).inflate(R.layout.pop_layout,null);
         popupWindow = new PopupWindow(view, 100, 100);
        view.findViewById(R.id.diss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setAnimationStyle(R.style.popup_anim_style);
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(mViewShow,340,-610);
        }
    }

    private void doAnimation(Animation animation, View view) {
        Animation oldAnimation = view.getAnimation();
        if (oldAnimation != null && oldAnimation.hasStarted() && !oldAnimation.hasEnded()) {
            view.clearAnimation();
        }
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.i(TAG, "onAnimationStart: ");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.i(TAG, "onAnimationEnd: ");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.i(TAG, "onAnimationRepeat: ");
            }
        });

        view.startAnimation(animation);

    }

    private Animation getAnimationSet(boolean fromXML) {
        if (fromXML) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.view_animation);
            return animation;
        } else {
            AnimationSet innerAnimationSet = new AnimationSet(true);
            innerAnimationSet.setInterpolator(new BounceInterpolator());
            innerAnimationSet.setStartOffset(1000);
            innerAnimationSet.addAnimation(getScaleAnimation());
            innerAnimationSet.addAnimation(getTranslateAnimation());

            AnimationSet animationSet = new AnimationSet(true);
            animationSet.setInterpolator(new LinearInterpolator());

            animationSet.addAnimation(getAlphaAnimation());
            animationSet.addAnimation(getRotateAnimation());
            animationSet.addAnimation(innerAnimationSet);

            return animationSet;
        }
    }

    private Animation getAlphaAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setFillBefore(false);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        return alphaAnimation;
    }

    private Animation getRotateAnimation() {
        RotateAnimation rotateAnimation = new RotateAnimation(0f, 360f,
                getWidth() / 2, getHeight() / 2);
        rotateAnimation.setDuration(2000);
        rotateAnimation.setRepeatCount(1);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setFillBefore(false);
        rotateAnimation.setRepeatMode(Animation.REVERSE);
        rotateAnimation.setInterpolator(new AccelerateInterpolator());//动画始末速率较慢，中间加速
        return rotateAnimation;
    }

    private Animation getScaleAnimation() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 2f,
                1f, 2f,
                getWidth() / 2, getHeight() / 2);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setRepeatCount(2);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setFillBefore(false);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        scaleAnimation.setInterpolator(new BounceInterpolator());//动画结束时弹起
        return scaleAnimation;
    }

    private Animation getTranslateAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0, getWidth() * 2,
                0, getHeight() * 2);
        translateAnimation.setDuration(2000);
        translateAnimation.setRepeatCount(2);
        translateAnimation.setFillAfter(true);
        translateAnimation.setFillBefore(false);
        translateAnimation.setRepeatMode(Animation.REVERSE);
        translateAnimation.setInterpolator(new DecelerateAccelerateInterpolator());
        return translateAnimation;
    }

    private int getWidth() {
        return mViewShow.getWidth();
    }

    private int getHeight() {
        return mViewShow.getHeight();
    }


}
