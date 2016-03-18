package com.qcj.learning.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class View3_6_3_1 extends View {
    private int length = 720;
    private int mCircleXY;
    private float mRadius;
    private RectF mArcRecF;
    private Paint mCirclePaint;
    private Paint mArcPaint;
    private Paint mTextPaint;
    /**********************************************/
    private int mSweep = 270;
    private String mText = "android";
    private int mShowTextSize = 30; // �����С

    public View3_6_3_1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSet();
    }

    public View3_6_3_1(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSet();
    }

    public View3_6_3_1(Context context) {
        super(context);
        initSet();
    }

    private void initSet() {
        mCircleXY = length / 2;
        mRadius = (float) (length * 0.5 / 2);
        mArcRecF = new RectF((float) (length * 0.1), (float) (length * 0.1), (float) (length * 0.9),
                (float) (length * 0.9));
        // �����м�԰Ȧ
        mCirclePaint = new Paint();
        mCirclePaint.setColor(getResources().getColor(android.R.color.holo_blue_bright));
        mCirclePaint.setAntiAlias(true);
        // ���û���
        mArcPaint = new Paint();
        mArcPaint.setColor(getResources().getColor(android.R.color.holo_orange_light));
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStrokeWidth(80f);
        // �������ֵ���ɫ
        mTextPaint = new Paint();
        mTextPaint.setColor(getResources().getColor(android.R.color.holo_green_dark));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(32);
        mTextPaint.setTextAlign(Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(mCircleXY, mCircleXY, mRadius, mCirclePaint);
        canvas.drawArc(mArcRecF, 0, mSweep, false, mArcPaint);
        canvas.drawText(mText, 0, mText.length(), mCircleXY, mCircleXY + (mShowTextSize / 4), mTextPaint);
    }

    public void setSweepValue(float value) {
        if (value != 0) {
            mSweep = (int) (360 * (value / 100));
        } else {
            mSweep = 25;
        }
        mText = value + "%";
        invalidate();
    }
}
