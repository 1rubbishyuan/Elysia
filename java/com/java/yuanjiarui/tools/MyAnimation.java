package com.java.yuanjiarui.tools;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

public class MyAnimation {
    static  public  void viewShake(View view){
        Animation shake=new RotateAnimation(-7,7,Animation.RELATIVE_TO_SELF,0.5f
                ,Animation.RELATIVE_TO_SELF, 0.5f);
        shake.setDuration(200);
        shake.setRepeatMode(Animation.REVERSE);
        shake.setRepeatCount(Integer.MAX_VALUE);
        view.startAnimation(shake);
    }
    static  public  void  upAndDisappear(View view){
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        alphaAnimator.setDuration(1000); // 设置透明度动画持续时间，单位为毫秒

        // 创建平移动画
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(view, "translationY", 0, -view.getHeight());
        translationAnimator.setDuration(1000); // 设置平移动画持续时间，单位为毫秒

        // 创建动画集合
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alphaAnimator, translationAnimator); // 同时播放透明度动画和平移动画
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator()); // 设置动画插值器，使动画速度在开始和结束时变慢

        animatorSet.start(); // 启动动画

        // 在动画结束后将视图返回原位置
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setTranslationY(0); // 恢复视图的原始位置
                view.setAlpha(1f); // 恢复视图的完全不透明状态
            }
        });
    }
    public static void leftWingFlap(View view) {
        // 获取视图的右下角坐标
       // int pivotX = view.getRight();
       // int pivotY = view.getBottom();

        // 创建旋转动画
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(view, "rotation", 0f, 15f, 0f, -15f, 0f);
        rotationAnimator.setDuration(500); // 设置旋转动画持续时间，单位为毫秒
        rotationAnimator.setInterpolator(new AccelerateDecelerateInterpolator()); // 设置动画插值器，使动画速度在开始和结束时变慢

        // 设置旋转动画的中心点
      //  view.setPivotX(pivotX);
      //  view.setPivotY(pivotY);

        rotationAnimator.start(); // 启动动画
    }
    public static void rightWingFlap(View view) {
        // 获取视图的左下角坐标
      //  int pivotX = view.getLeft();
     //   int pivotY = view.getBottom();

        // 创建旋转动画
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(view, "rotation", 0f, 15f, 0f, -15f, 0f);
        rotationAnimator.setDuration(500); // 设置旋转动画持续时间，单位为毫秒
        rotationAnimator.setInterpolator(new AccelerateDecelerateInterpolator()); // 设置动画插值器，使动画速度在开始和结束时变慢

        // 设置旋转动画的中心点
      //  view.setPivotX(pivotX);
       // view.setPivotY(pivotY);

        rotationAnimator.start(); // 启动动画
    }
}
