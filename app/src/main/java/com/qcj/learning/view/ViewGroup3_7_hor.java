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

public class ViewGroup3_7_hor extends ViewGroup {
	private static final String TAG = "ViewGroup3_7_hor";
	private int mScreenHeight;
	private int mScreenWidth;
	private Scroller mScroller;

	public ViewGroup3_7_hor(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initset();
	}

	public ViewGroup3_7_hor(Context context, AttributeSet attrs) {
		super(context, attrs);
		initset();
	}

	public ViewGroup3_7_hor(Context context) {
		super(context);
		initset();
	}

	private void initset() {
		mScreenHeight = UIUtils.getWindowHeight(getContext());
		mScreenWidth = UIUtils.getWindowWidth(getContext());
		mScroller = new Scroller(getContext());

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		measureChildren(widthMeasureSpec, heightMeasureSpec); // �������ӵĳߴ�
		int cCount = getChildCount();
		int totalWidth = mScreenWidth * cCount;
		Log.i(TAG, "onMeasure======>=mScreenHeight" + mScreenHeight + "   totalWidth" + totalWidth);
		setMeasuredDimension(totalWidth, mScreenHeight);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int cCount = getChildCount();
		int lc = 0;
		int tc = 0;
		for (int i = 0; i < cCount; i++) {
			View view = getChildAt(i);
			view.layout(lc + mScreenWidth * i, tc, mScreenWidth * (i + 1), mScreenHeight);
		}
	}

	int currentX = 0;
	int lastX = 0;
	int dX = 0; // ������ƫ����

	/***************** ��ӻ��������ճ�� *******************/
	private int mStart = 0;
	private int mEnd = 0;
	private int mDscrollX = 0;
	private int mScrollWidth = 0; // ����������Ҫ���Ŀ��

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			currentX = (int) event.getX();
			lastX = currentX;
			mStart = getScrollX();
			break;

		case MotionEvent.ACTION_MOVE:
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			currentX = (int) event.getX();
			Log.i(TAG, "onTouchEvent======>=currentY" + currentX + "   lastX" + lastX);
			dX = currentX - lastX;
			Log.i(TAG + "dX", "onTouchEvent======>dX==" + dX);
			scrollBy(-dX, 0);
			lastX = currentX;
			break;
		case MotionEvent.ACTION_UP:
			// ��Ҫ�ж��Ƿ񻬶�������� ����ǵĻ���Ҫ����Ϊ0��
			if (getScrollX() < 0) {
				// scrollTo(0, 0);
				mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0, 1000);
			} else if ((getScrollX() > (getMeasuredWidth() - mScreenWidth))) {
				// scrollTo(0, getMeasuredHeight() - mScreenHeight);
				mScroller.startScroll(getScrollX(), 0, -(mScreenWidth - (getMeasuredWidth() - getScrollX())), 0, 1000);
			} else {
				mEnd = getScrollX();
				mDscrollX = mEnd - mStart;
				Log.i(TAG, "onTouchEvent======>=mDscrollX=" + mDscrollX);
				// ���󻬶�ʱ��
				if (mDscrollX > 0) {
					if (mDscrollX > mScreenWidth / 3) {
						mScrollWidth = (getScrollX() / mScreenWidth + 1) * mScreenWidth;
						mScroller.startScroll(getScrollX(), 0, mScrollWidth - getScrollX(), 0, 1000);
					} else {
						mScrollWidth = (getScrollX() / mScreenWidth) * mScreenWidth;
						mScroller.startScroll(getScrollX(), 0, mScrollWidth - getScrollX(), 0, 1000);
					}
				} else {
					// ���һ�����ʱ��
					if (-mDscrollX > mScreenWidth / 3) {
						mScrollWidth = (getScrollX() / mScreenWidth) * mScreenWidth;
						mScroller.startScroll(getScrollX(), 0, -(getScrollX() - mScrollWidth), 0, 1000);
					} else {
						mScrollWidth = (getScrollX() / mScreenWidth + 1) * mScreenWidth;
						mScroller.startScroll(getScrollX(), 0, mScrollWidth - getScrollX(), 0, 1000);
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
			Log.i(TAG, "computeScroll======>mScroller.getCurrY()=" + mScroller.getCurrX());
			scrollTo(mScroller.getCurrX(), 0);
			postInvalidate();
		}
	}

}
