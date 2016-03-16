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
	 * ����һ�������Ĳ��ֲ��� ��ֻ�ǵ�������������
	 * 
	 * ����������������һ��һ���ſؼ��ģ��������������һ��ȥ
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
		measureChildren(widthMeasureSpec, heightMeasureSpec); // ��������������Ŀ��
		int cCount = getChildCount();
		/********************************/
		MarginLayoutParams cParam = null;
		int cWidth = 0; // ����Ŀ��
		int cHeight = 0; // ����ĸ߶�
		int lineWidth = 0; // ��һ�еĿ��
		int lineHeight = 0; // ��һ�еĸ߶�
		for (int i = 0; i < cCount; i++) {
			View view = getChildAt(i);
			cParam = (MarginLayoutParams) view.getLayoutParams();
			cWidth = view.getMeasuredWidth() + cParam.leftMargin + cParam.rightMargin;
			cHeight = view.getMeasuredHeight() + cParam.topMargin + cParam.bottomMargin;
			Log.i(TAG, "onMeasure======>=cParam.leftMargin=" + cParam.leftMargin + "   cParam.rightMargin="
					+ cParam.rightMargin);
			if (lineWidth + cWidth > width) {
				int compareWidth = Math.max(lineWidth, cWidth); // ���������֤cacluteWidth���
				cacluteWidth = Math.max(compareWidth, cacluteWidth);// ��ǰ������ȸ����ڵ�����ȱȽϣ�˭��˭����
				cacluteHeight = cacluteHeight + lineHeight;
				// ������Ϊ��һ�ŵ�ʱ������Ǹ߶�����Ϊ��
				lineHeight = 0;
				lineWidth = 0;
			}
			lineWidth = lineWidth + cWidth;
			lineHeight = Math.max(lineHeight, cHeight);

			// ��Ϊ���һ����ʱ�� ����Ҫ�������һ�ŵĸ߶�
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
		List<List<View>> allViews = new ArrayList<List<View>>(); // ת�����е�����
		List<View> lineViews = new ArrayList<View>();// װһ�е�view������
		// ��Щ�������
		allViews.clear();
		lineViews.clear();
		int cCount = getChildCount();
		int lineWidth = 0; // ÿһ�еĿ���ۼ���
		MarginLayoutParams params = null;
		// ��ÿһ�е�view�ŵ�һ��������
		for (int i = 0; i < cCount; i++) {
			View view = getChildAt(i);
			params = (MarginLayoutParams) view.getLayoutParams();
			int cWidth = view.getMeasuredWidth() + params.leftMargin + params.rightMargin;
			if (lineWidth + cWidth > getMeasuredWidth()) {
				Log.i(TAG, "onLayout======>lineWidth + cWidth" + (lineWidth + cWidth));
				// ����
				allViews.add(lineViews); // �����е�view�����������������
				lineViews = new ArrayList<View>();
				lineWidth = cWidth; // ���������view�Ŀ��
				lineViews.add(view);
			} else {
				// �ۼ�
				lineWidth = lineWidth + cWidth;
				lineViews.add(view);
			}
		}
		// �����һ��û������ʱ�� ҲҪ���ؽ�ȥ
		if (lineViews.size() > 0) {
			allViews.add(lineViews);
		}
		/************************** �������������view���õ������� **************************/
		int tHeight = 0; // ��������ĸ�
		int lWidth = 0; // ������������
		int compareHeight = 0; // ���ڱȽ���ߵ�view��
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
