package com.qcj.learning.view.touchEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class MyViewGroupA extends ViewGroup {
	private static final String TAG = "onTouchEvent";

	public MyViewGroupA(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyViewGroupA(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyViewGroupA(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		measureChildren(widthMeasureSpec, heightMeasureSpec);
		super.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		View view = getChildAt(0);
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.i(TAG, "MyViewGroupA======>dispatchTouchEvent=");
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.i(TAG, "MyViewGroupA======>onInterceptTouchEvent=");
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.i(TAG, "MyViewGroupA======>onTouchEvent=");
		return super.onTouchEvent(event);
	}

}
