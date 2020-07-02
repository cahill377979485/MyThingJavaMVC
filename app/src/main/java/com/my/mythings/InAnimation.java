package com.my.mythings;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @author 文琳
 * @time 2020/6/18 17:00
 * @desc
 */
public class InAnimation {
    private static final float DEFAULT_SCALE_FROM = .618f;
    private final float mFrom;

    public InAnimation() {
        this(DEFAULT_SCALE_FROM);
    }

    public InAnimation(float from) {
        mFrom = from;
    }

    public Animator[] getAnimators(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", mFrom, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", mFrom, 1f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", mFrom, 1f);
        return new ObjectAnimator[]{scaleX, scaleY, alpha};
    }
}
