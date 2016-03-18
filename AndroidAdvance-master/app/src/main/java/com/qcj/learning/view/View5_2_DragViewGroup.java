package com.qcj.learning.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class View5_2_DragViewGroup extends FrameLayout {
	private static final String TAG = "View5_2_DragViewGroup";

	private ViewDragHelper mDragHelper; // draghelp专门用来处理滑动的情况
	private View mMenuView, mContentView;
	private int mMenuWidth; // 菜单的宽度

	public View5_2_DragViewGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public View5_2_DragViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public View5_2_DragViewGroup(Context context) {
		super(context);
		initView();
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mMenuView = getChildAt(0);
		mContentView = getChildAt(1);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mMenuWidth = mMenuView.getMeasuredWidth(); // 获取菜单的宽度
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return mDragHelper.shouldInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mDragHelper.processTouchEvent(event);
		return true;
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		if (mDragHelper.continueSettling(true)) {
			View view = mDragHelper.getCapturedView();
			setContentViewYscale(view, view.getLeft());
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}

	/**
	 * 初始化viewdraghepler并添加回调事件
	 */
	private void initView() {
		mDragHelper = ViewDragHelper.create(this, new Callback() {

			@Override
			public boolean tryCaptureView(View arg0, int arg1) {
				// TODO Auto-generated method stub
				return arg0 == mContentView;
			}

			@Override
			public int clampViewPositionHorizontal(View child, int left, int dx) {
				Log.i(TAG, "clampViewPositionHorizontal======>left=" + left);
				if (left > mMenuWidth) {
					setContentViewYscale(mContentView, mMenuWidth);
					return mMenuWidth;
				}
				if (left < 0) {
					return 0;
				}
				setContentViewYscale(mContentView, left);
				return left;
			}

			@Override
			public int clampViewPositionVertical(View child, int top, int dy) {
				return 0;
			}

			@Override
			public void onViewReleased(View releasedChild, float xvel, float yvel) {
				super.onViewReleased(releasedChild, xvel, yvel);
				if (mContentView.getLeft() < ((mMenuWidth * (2.0 / 3.0)))) {
					mDragHelper.smoothSlideViewTo(mContentView, 0, 0);
					ViewCompat.postInvalidateOnAnimation(View5_2_DragViewGroup.this);
				} else {
					mDragHelper.smoothSlideViewTo(mContentView, mMenuWidth, 0);
					ViewCompat.postInvalidateOnAnimation(View5_2_DragViewGroup.this);
				}
			}

		});
	}

	private float getContentViewYScale(int left) {
		float scale = (float) (0.2 * ((float) left / (float) mMenuWidth));
		return 1 - scale;
	}

	/**
	 * 设置内容布局的大小
	 * 
	 * @param view
	 * @param left
	 */
	public void setContentViewYscale(View view, int left) {
		float scale = getContentViewYScale(left);
		view.setScaleY(scale);
	}
}
