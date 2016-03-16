package com.qcj.learning.view;

import com.qcj.learning.util.UIUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/******
 * getScrollX() ���ǵ�ǰview�����Ͻ������ĸ��ͼ�����Ͻǵ�X��ƫ������
 * ��ʵ������ڸ�����ͼ�����Ͻ�����Ϊԭ�㣨0��0��������������ViewGroup�����Ͻ�Ϊԭ�㡣
 * 
 * 
 * 
 * 
 **************/

public class ViewGroup3_7 extends ViewGroup {
	private static final String TAG = "ViewGroup3_7";
	private int mScreenHeight;
	private int mScreenWidth;
	private Scroller mScroller;

	public ViewGroup3_7(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initset();
	}

	public ViewGroup3_7(Context context, AttributeSet attrs) {
		super(context, attrs);
		initset();
	}

	public ViewGroup3_7(Context context) {
		super(context);
		initset();
	}

	private void initset() {
		mScreenHeight = UIUtils.getWindowHeight(getContext());
		mScreenWidth = UIUtils.getWindowWidth(getContext());
		Log.i(TAG, "initset======>=mScreenHeight" + mScreenHeight + "   mScreenWidth" + mScreenWidth);
		mScroller = new Scroller(getContext());

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		measureChildren(widthMeasureSpec, heightMeasureSpec); // �������ӵĳߴ�
		int cCount = getChildCount();
		int totalHeight = mScreenHeight * cCount;
		Log.i(TAG, "onMeasure======>=mScreenWidth" + mScreenWidth + "   totalHeight" + totalHeight);
		setMeasuredDimension(mScreenWidth, totalHeight);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int cCount = getChildCount();
		int lc = 0;
		int tc = 0;
		for (int i = 0; i < cCount; i++) {
			View view = getChildAt(i);
			Log.i(TAG, "onLayout======>=lc" + lc + "   tc" + (tc + mScreenHeight * i) + "mScreenWidth=" + mScreenWidth
					+ "tc=" + mScreenHeight * (i + 1));
			view.layout(lc, tc + mScreenHeight * i, mScreenWidth, mScreenHeight * (i + 1));
		}
	}

	int currentY = 0;
	int lastY = 0;
	int dy = 0; // ������ƫ����

	/***************** ��ӻ��������ճ�� *******************/
	private int mStart = 0;
	private int mEnd = 0;
	private int mDscrollY = 0;
	private int mScrollHeight = 0; // ����������Ҫ����ĸ߶�

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.i(TAG, "onTouchEvent======>");
		Log.i(TAG, "onTouchEvent======>getScrollY����=" + getScrollY());
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			currentY = (int) event.getY();
			lastY = currentY;
			mStart = getScrollY();
			break;

		case MotionEvent.ACTION_MOVE:
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			currentY = (int) event.getY();
			Log.i(TAG, "onTouchEvent======>=currentY" + currentY + "   lastY" + lastY);
			dy = currentY - lastY;
			Log.i(TAG + "dy", "onTouchEvent======>dy==" + dy);
			scrollBy(0, -dy);
			lastY = currentY;
			break;
		case MotionEvent.ACTION_UP:
			// ��Ҫ�ж��Ƿ񻬶������� ����ǵĻ���Ҫ����Ϊ0��
			if (getScrollY() < 0) {
				// scrollTo(0, 0);
				mScroller.startScroll(0, getScrollY(), 0, -getScrollY(), 1000);
			} else if ((getScrollY() > (getMeasuredHeight() - mScreenHeight))) {
				// scrollTo(0, getMeasuredHeight() - mScreenHeight);
				mScroller.startScroll(0, getScrollY(), 0, -(mScreenHeight - (getMeasuredHeight() - getScrollY())),
						1000);
			} else {
				mEnd = getScrollY();
				mDscrollY = mEnd - mStart;
				Log.i(TAG, "onTouchEvent======>=mDscrollY=" + mDscrollY);
				// ���»���ʱ��
				if (mDscrollY > 0) {
					if (mDscrollY > mScreenHeight / 3) {
						mScrollHeight = (getScrollY() / mScreenHeight + 1) * mScreenHeight;
						Log.i(TAG, "startScroll======>=mDscrollY=" + mDscrollY);
						mScroller.startScroll(0, getScrollY(), 0, mScrollHeight - getScrollY(), 1000);
					} else {
						mScrollHeight = (getScrollY() / mScreenHeight) * mScreenHeight;
						Log.i(TAG, "startScroll======>=mDscrollY=" + mDscrollY);
						mScroller.startScroll(0, getScrollY(), 0, mScrollHeight - getScrollY(), 1000);
					}
				} else {
					// ���ϻ�����ʱ��
					if (-mDscrollY > mScreenHeight / 3) {
						mScrollHeight = (getScrollY() / mScreenHeight) * mScreenHeight;
						mScroller.startScroll(0, getScrollY(), 0, -(getScrollY() - mScrollHeight), 1000);
					} else {
						mScrollHeight = (getScrollY() / mScreenHeight + 1) * mScreenHeight;
						mScroller.startScroll(0, getScrollY(), 0, mScrollHeight - getScrollY(), 1000);
					}
				}
			}
			break;
		}
		postInvalidate();
		return true;
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		if (mScroller.computeScrollOffset()) {
			Log.i(TAG, "computeScroll======>mScroller.getCurrY()=" + mScroller.getCurrY());
			scrollTo(0, mScroller.getCurrY());
			postInvalidate();
		}
	}

}
