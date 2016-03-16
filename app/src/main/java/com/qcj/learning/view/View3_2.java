package com.qcj.learning.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class View3_2 extends View {
	private static final String TAG = "View3_2";

	public View3_2(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public View3_2(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public View3_2(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		Log.i(TAG, "onFinishInflate======>=");
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		Log.i(TAG, "onSizeChanged======>=");
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		Log.i(TAG, "onLayout======>=");
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onMeasure=====>widthMeasureSpec=" + widthMeasureSpec + " heightMeasureSpec=" + heightMeasureSpec);
		setMeasuredDimension(getMeasureHeight(widthMeasureSpec), getMeasureHeight(heightMeasureSpec));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.i(TAG, "onDraw======>=");
		super.onDraw(canvas);
	}

	/**
	 * @param heightMeasureSpec
	 * @return
	 */
	private int getMeasureHeight(int heightMeasureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(heightMeasureSpec);
		int specResult = MeasureSpec.getSize(heightMeasureSpec);
		Log.i(TAG, "getMeasureHeight======>specResult=" + specResult);
		if (specMode == MeasureSpec.EXACTLY) {
			return result;
		} else {
			result = 500;
			return Math.min(result, specResult);
		}
	}

}
