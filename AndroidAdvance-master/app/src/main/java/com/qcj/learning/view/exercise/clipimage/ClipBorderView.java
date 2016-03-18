package com.qcj.learning.view.exercise.clipimage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by qiuchunjia on 2016/3/18.
 * 裁剪图片的边框
 */
public class ClipBorderView extends View {
    /**
     * 水平方向与View的边距
     */
    private int mHorizontalPadding = 20;
    /**
     * 垂直方向与View的边距
     */
    private int mVerticalPadding;
    /**
     * 绘制的矩形的宽度
     */
    private int mWidth;
    /**
     * 边框的颜色，默认为白色
     */
    private int mBorderColor = Color.parseColor("#FFFFFF");
    /**
     * 边框的外面的颜色，默认为半透明黑色
     */
    private int mBorderOutColor = Color.parseColor("#aa000000");
    /**
     * 边框的宽度 单位dp
     */
    private int mBorderWidth = 1;
    private Paint mPaint;

    public ClipBorderView(Context context) {
        super(context);
        initData();
    }

    public ClipBorderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public ClipBorderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        // 计算padding的px
        mHorizontalPadding = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, mHorizontalPadding, getResources()
                        .getDisplayMetrics());
        mBorderWidth = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, mBorderWidth, getResources()
                        .getDisplayMetrics());
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mWidth = getWidth() - 2 * mHorizontalPadding;
        mVerticalPadding = (getHeight() - mWidth) / 2;
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mBorderOutColor);
        //画左边的图形
        canvas.drawRect(0, 0, mHorizontalPadding, getHeight(), mPaint);
        //画右边的图形
        canvas.drawRect(getWidth() - mHorizontalPadding, 0, getWidth(), getHeight(), mPaint);
        //话上边的图形
        canvas.drawRect(mHorizontalPadding, 0, getWidth() - mHorizontalPadding, mVerticalPadding, mPaint);
        canvas.drawRect(mHorizontalPadding, getHeight() - mVerticalPadding, getWidth() - mHorizontalPadding, getHeight(), mPaint);
        mPaint.setColor(mBorderColor);
        mPaint.setStyle(Paint.Style.STROKE);
        //画中间的矩形
        canvas.drawRect(mHorizontalPadding, mVerticalPadding, getWidth() - mHorizontalPadding, getHeight() - mVerticalPadding, mPaint);
    }

    public void setmHorizontalPadding(int mHorizontalPadding) {
        this.mHorizontalPadding = mHorizontalPadding;
    }

    public void setmBorderColor(int mBorderColor) {
        this.mBorderColor = mBorderColor;
    }

    public void setmBorderOutColor(int mBorderOutColor) {
        this.mBorderOutColor = mBorderOutColor;
    }
}
