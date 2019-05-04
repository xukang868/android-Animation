package com.xiaohan.animationtest;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Time: 2019/3/28 20:14
 * Author: xiaohan
 */
public class ObjectActivity extends AppCompatActivity {
    private static final String TAG = "ObjectActivity";
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.title)
    TextView mTitle;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object);
        ButterKnife.bind(this);
        mTitle.setText(getString(R.string.object_name));
    }

    @OnClick(R.id.back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @OnClick({R.id.back, R.id.alpha, R.id.roatate, R.id.scale, R.id.translate, R.id.set})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.alpha:
                doAnimation(getAlphaAnimation(mViewShow));
                break;
            case R.id.roatate:
                doAnimation(getRotateAnimation(mViewShow));
                break;
            case R.id.scale:
                doAnimation(getScaleAnimation(mViewShow));
                break;
            case R.id.translate:
                doAnimation(getTranslateAnimation(mViewShow));
                break;
            case R.id.set:
                getAnimatorSet(mViewShow, false);
                break;
        }
    }

    private void doAnimation(final ObjectAnimator animator) {
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.i(TAG, "onAnimationUpdate: " + animator.getAnimatedValue());
            }
        });
        animator.start();

    }

    private ObjectAnimator getAlphaAnimation(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f, 1f);
        animator.setDuration(5000);
        return animator;
    }

    private ObjectAnimator getRotateAnimation(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        animator.setDuration(5000);
        animator.start();
        return animator;

    }

    private ObjectAnimator getScaleAnimation(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "scaleY", 1f, 3f, 1f);
        animator.setDuration(5000);
        animator.start();
        return animator;
    }

    private ObjectAnimator getTranslateAnimation(View view) {
        float curTranslationX = view.getTranslationX();
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", curTranslationX, -500f, curTranslationX);
        animator.setDuration(5000);
        animator.start();
        return animator;
    }

    @SuppressLint("ResourceType")
    private void getAnimatorSet(View view, boolean isFromXML) {
        AnimatorSet animatorSet = null;
        if (isFromXML) {
            animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(ObjectActivity.this, R.anim.object_animator);
            animatorSet.setTarget(view);
        } else {
            animatorSet = new AnimatorSet();
            ObjectAnimator animatorRotate = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
            ObjectAnimator animatorAlpha1 = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f, 1f);
            ObjectAnimator animatorAlpha2 = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f, 1f);
            ObjectAnimator animatorScale = ObjectAnimator.ofFloat(view, "scaleY", 1f, 3f, 1f);
            float curTranslationX = view.getTranslationX();
            ObjectAnimator animatorTranslationX = ObjectAnimator.ofFloat(view, "translationX", curTranslationX, -500f, curTranslationX);

            animatorSet.play(animatorRotate).before(animatorAlpha1).after(animatorTranslationX).after(500).with(animatorScale).with(animatorAlpha2);
            animatorSet.setDuration(5000);
        }
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        animatorSet.start();
        ValueAnimator.ofFloat(1,2);
    }
}
