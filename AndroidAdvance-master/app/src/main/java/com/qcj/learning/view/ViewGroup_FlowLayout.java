package com.qcj.learning.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class ViewGroup_FlowLayout extends ViewGroup {
	/**
	 * 2016-1-25 qcj
	 * 
	 * 创建一个浮动的布局布局 ，只是单纯拿来练练手
	 * 
	 * 它是用来从坐往右一个一个放控件的，不够宽就往到下一排去
	 * 
	 ****/
	private static final String TAG = "ViewGroup_FlowLayout";

	public ViewGroup_FlowLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public ViewGroup_FlowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ViewGroup_FlowLayout(Context context) {
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
		Log.i(TAG, "onMeasure======>=width=" + width + "   height=" + height);
		int cacluteWidth = 0;
		int cacluteHeight = 0;
		measureChildren(widthMeasureSpec, heightMeasureSpec); // 测量各个的子类的宽高
		int cCount = getChildCount();
		/********************************/
		MarginLayoutParams cParam = null;
		int cWidth = 0; // 子类的宽度
		int cHeight = 0; // 子类的高度
		int lineWidth = 0; // 这一行的宽度
		int lineHeight = 0; // 这一行的高度
		for (int i = 0; i < cCount; i++) {
			View view = getChildAt(i);
			cParam = (MarginLayoutParams) view.getLayoutParams();
			cWidth = view.getMeasuredWidth() + cParam.leftMargin + cParam.rightMargin;
			cHeight = view.getMeasuredHeight() + cParam.topMargin + cParam.bottomMargin;
			Log.i(TAG, "onMeasure======>=cParam.leftMargin=" + cParam.leftMargin + "   cParam.rightMargin="
					+ cParam.rightMargin);
			if (lineWidth + cWidth > width) {
				int compareWidth = Math.max(lineWidth, cWidth); // 这个用来保证cacluteWidth最大
				cacluteWidth = Math.max(compareWidth, cacluteWidth);// 以前的最大宽度跟现在的最大宽度比较，谁大谁保存
				cacluteHeight = cacluteHeight + lineHeight;
				// 当它们为下一排的时候把他们高度设置为零
				lineHeight = 0;
				lineWidth = 0;
			}
			lineWidth = lineWidth + cWidth;
			lineHeight = Math.max(lineHeight, cHeight);

			// 当为最后一个的时候 ，需要加上最后一排的高度
			if (i == cCount - 1) {
				cacluteHeight = cacluteHeight + lineHeight;
				Log.i(TAG, "onMeasure======>=cacluteWidth=" + cacluteWidth + "   lineWidth=" + lineWidth);
				cacluteWidth = Math.max(cacluteWidth, lineWidth);
			}
		}
		Log.i(TAG, "onMeasure======>=cacluteWidth=" + cacluteWidth + "   cacluteHeight=" + cacluteHeight);
		setMeasuredDimension(widthMode == MeasureSpec.AT_MOST ? cacluteWidth : width,
				heightMode == MeasureSpec.AT_MOST ? cacluteHeight : height);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		List<List<View>> allViews = new ArrayList<List<View>>(); // 转载所有的子类
		List<View> lineViews = new ArrayList<View>();// 装一行的view的容器
		// 这些容器清空
		allViews.clear();
		lineViews.clear();
		int cCount = getChildCount();
		int lineWidth = 0; // 每一行的宽度累加器
		MarginLayoutParams params = null;
		// 把每一行的view放到一个容器中
		for (int i = 0; i < cCount; i++) {
			View view = getChildAt(i);
			params = (MarginLayoutParams) view.getLayoutParams();
			int cWidth = view.getMeasuredWidth() + params.leftMargin + params.rightMargin;
			if (lineWidth + cWidth > getMeasuredWidth()) {
				Log.i(TAG, "onLayout======>lineWidth + cWidth" + (lineWidth + cWidth));
				// 换行
				allViews.add(lineViews); // 把这行的view容器添加了总容器中
				lineViews = new ArrayList<View>();
				lineWidth = cWidth; // 设置这个子view的宽度
				lineViews.add(view);
			} else {
				// 累加
				lineWidth = lineWidth + cWidth;
				lineViews.add(view);
			}
		}
		// 当最后一行没有满的时候 也要加载进去
		if (lineViews.size() > 0) {
			allViews.add(lineViews);
		}
		/************************** 把总容器里面的view放置到界面中 **************************/
		int tHeight = 0; // 放置子类的高
		int lWidth = 0; // 放置子类的左边
		int compareHeight = 0; // 用于比较最高的view；
		for (int i = 0; i < allViews.size(); i++) {
			lineViews = allViews.get(i);
			for (int j = 0; j < lineViews.size(); j++) {
				View view = lineViews.get(j);
				params = (MarginLayoutParams) view.getLayoutParams();
				int lc = lWidth + params.leftMargin;
				int tc = tHeight + params.topMargin;
				int rc = lc + view.getMeasuredWidth();
				int bc = tc + view.getMeasuredHeight();
				Log.i(TAG, view + " , l = " + lc + " , t = " + tc + " , r =" + rc + " , b = " + bc);
				view.layout(lc, tc, rc, bc);
				lWidth = lWidth + view.getMeasuredWidth() + params.leftMargin + params.rightMargin;
				compareHeight = Math.max(compareHeight,
						params.topMargin + params.bottomMargin + view.getMeasuredHeight());
			}
			tHeight = tHeight + compareHeight;
			lWidth = 0;
		}

	}
}
