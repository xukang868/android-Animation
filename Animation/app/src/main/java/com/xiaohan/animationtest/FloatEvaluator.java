package com.xiaohan.animationtest;

import android.animation.TypeEvaluator;

/**
 * Created by xiaohan on 2019/4/6
 * Describe:
 */
public class FloatEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        float startFloat = ((Number) startValue).floatValue();
        return startFloat + fraction * (((Number) endValue).floatValue() - startFloat);
    }
}
