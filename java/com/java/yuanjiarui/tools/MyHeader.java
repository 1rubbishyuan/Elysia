package com.java.yuanjiarui.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.java.yuanjiarui.R;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;

public class MyHeader extends RelativeLayout implements RefreshHeader {
    private void initView(Context context) {
        // 加载布局文件 custom_header.xml
        View view = LayoutInflater.from(context).inflate(R.layout.myheader, this);
        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(context);
        progressDrawable.setStyle(CircularProgressDrawable.DEFAULT);
        progressDrawable.start();
        progressBar = findViewById(R.id.progressBar);
        progressBar.setIndeterminateDrawable(progressDrawable);

        imageView = view.findViewById(R.id.imageView);
        textView = view.findViewById(R.id.textView);
    }
    @SuppressLint("RestrictedApi")
    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        progressBar.setVisibility(View.VISIBLE);
    }
    @SuppressLint("RestrictedApi")
    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        progressBar.setVisibility(View.GONE);
        return 0;
    }
    private ProgressBar progressBar;
    private ImageView imageView;
    private TextView textView;
    public MyHeader(Context context) {
        this(context, null);
    }

    public MyHeader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }
    @SuppressLint("RestrictedApi")
    @Override
    public void setPrimaryColors(int... colors) {

    }
    @SuppressLint("RestrictedApi")
    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }
    @SuppressLint("RestrictedApi")
    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }
    @SuppressLint("RestrictedApi")
    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }
    @SuppressLint("RestrictedApi")
    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public boolean autoOpen(int duration, float dragRate, boolean animationOnly) {
        return false;
    }
    @SuppressLint("RestrictedApi")
    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

    }
}
