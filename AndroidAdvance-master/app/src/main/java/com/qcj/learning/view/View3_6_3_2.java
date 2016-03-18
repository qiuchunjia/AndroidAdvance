package com.qcj.learning.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/************ ªÊ÷∆“Ù¿÷Ãı–ŒÕº ************/
public class View3_6_3_2 extends View {
	private static final String TAG = "View3_6_3_2";
	private Paint mPaint;
	private int mRectCount = 10;
	private int currentHeight;
	private int mWidth;
	private int mRectWidht;
	private int mRectHeight;
	private int offset = 10;
	private double mRandom;

	private LinearGradient mLinearGradient;

	public View3_6_3_2(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initSet();
	}

	public View3_6_3_2(Context context, AttributeSet attrs) {
		super(context, attrs);
		initSet();
	}

	public View3_6_3_2(Context context) {
		super(context);
		initSet();
	}

	private void initSet() {
		mPaint = new Paint();
		mPaint.setColor(getResources().getColor(android.R.color.holo_green_light));
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Log.i(TAG, "onSizeChanged=" + "w=" + w + "  h=" + h + "  oldw=" + oldw + "  oldh=" + oldh);
		mWidth = getWidth();
		mRectHeight = getHeight();
		mRectWidht = (int) (mWidth * 0.6 / mRectCount);
		mLinearGradient = new LinearGradient(0, 0, mWidth, mRectHeight, Color.YELLOW, Color.BLUE,
				Shader.TileMode.CLAMP);
		mPaint.setShader(mLinearGradient);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		for (int i = 0; i < mRectCount; i++) {
			mRandom = Math.random();
			currentHeight = (int) (mRandom * mRectHeight);
			canvas.drawRect(mRectWidht * i + offset, currentHeight, mRectWidht * (i + 1), mRectHeight, mPaint);
		}
		postInvalidateDelayed(300);
	}

}
