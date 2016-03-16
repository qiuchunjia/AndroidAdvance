package com.qcj.learning.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class ViewGroup_Preview extends ViewGroup {
	/**
	 * 
	 * 创建一个布局 让他们分别居于左右上下 来自于鸿洋的博客练习
	 * 
	 * 
	 ****/
	private static final String TAG = "ViewGroup_preview";

	public ViewGroup_Preview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public ViewGroup_Preview(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ViewGroup_Preview(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		int cacluteWidth = 0;
		int cacluteHeight = 0;
		measureChildren(widthMeasureSpec, heightMeasureSpec);
		int cCount = getChildCount();
		/********************************/
		MarginLayoutParams cParam = null;
		int cWidth = 0;
		int cHeight = 0;

		int tWidth = 0;
		int bWidth = 0;
		int lHeight = 0;
		int rHeight = 0;
		for (int i = 0; i < cCount; i++) {
			View view = getChildAt(i);
			cParam = (MarginLayoutParams) view.getLayoutParams();
			cWidth = view.getMeasuredWidth();
			cHeight = view.getMeasuredHeight();
			if (i == 0 || i == 1) {
				tWidth = tWidth +cWidth+ cParam.rightMargin + cParam.leftMargin;
			}
			if (i == 2 || i == 3) {
				bWidth = bWidth + cWidth + cParam.leftMargin + cParam.rightMargin;
			}
			if (i == 0 || i == 2) {
				lHeight = lHeight + cHeight + cParam.topMargin + cParam.bottomMargin;
			}
			if (i == 1 || i == 3) {
				rHeight = rHeight + cParam.topMargin + cParam.bottomMargin;
			}
		}
		cacluteWidth = Math.max(bWidth, tWidth);
		cacluteHeight = Math.max(lHeight, rHeight);
		Log.i(TAG, "onMeasure======>cacluteWidth" + cacluteWidth + ",   cacluteHeight" + cacluteHeight);
		setMeasuredDimension(widthMode == MeasureSpec.AT_MOST ? cacluteWidth : width,
				heightMode == MeasureSpec.AT_MOST ? cacluteHeight : height);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int cCount = getChildCount();
		int cl = 0;
		int ct = 0;
		int cr = 0;
		int cb = 0;
		MarginLayoutParams params = null;
		for (int i = 0; i < cCount; i++) {
			View view = getChildAt(i);
			params = (MarginLayoutParams) view.getLayoutParams();
			if (i == 0) {
				cl = params.leftMargin;
				ct = params.topMargin;
			} else if (i == 1) {
				cl = getWidth() - view.getWidth() - params.rightMargin;
				ct = params.topMargin;
			} else if (i == 2) {
				cl = params.leftMargin;
				ct = getHeight() - view.getHeight() - params.bottomMargin;
			} else if (i == 3) {
				cl = getWidth() - view.getWidth() - params.rightMargin;
				ct = getHeight() - view.getHeight() - params.bottomMargin;
			}
			cr = cl + view.getMeasuredWidth();
			cb = ct + view.getMeasuredHeight();
			Log.i(TAG, "onLayout======>cl" + cl + ",   ct" + ct + ",   cr" + cr + ",   cb" + cb);
			view.layout(cl, ct, cr, cb);
		}
	}
}
