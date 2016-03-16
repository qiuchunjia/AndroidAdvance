package com.qcj.learning.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class View5_0_DragView extends View {
	private static final String TAG = "View5_0_DragView";

	/******
	 * 本demo主要是实现view的7种滑动方法
	 * 
	 * 1：layout方法
	 * 
	 * 2：offsetleftandright（offsetx） ， offsettopandbottom（offsety）
	 * 
	 * 3：layoutparams
	 * 
	 * 4：scrollto scrollby
	 * 
	 * 5：scroller
	 * 
	 * 6：属性动画
	 * 
	 * 7：viewdraghelper （兼容包里面的）
	 * 
	 ***/
	public View5_0_DragView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		mScroller = new Scroller(getContext());
	}

	public View5_0_DragView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mScroller = new Scroller(getContext());
	}

	public View5_0_DragView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mScroller = new Scroller(getContext());
	}

	private int mCurrentX = 0; // 当前的x
	private int mCurrentY = 0;// 当前的y；
	private int mLastX = 0;//
	private int mLastY = 0;//
	private Scroller mScroller; // 用于第五种

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// switch (event.getAction()) {
		// case MotionEvent.ACTION_DOWN:
		// mLastX = (int) event.getRawX();
		// mLastY = (int) event.getRawY();
		// break;
		// case MotionEvent.ACTION_MOVE:
		// mCurrentX = (int) event.getRawX();
		// mCurrentY = (int) event.getRawY();
		// int offsetX = mCurrentX - mLastX;
		// int offsetY = mCurrentY - mLastY;
		// layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX,
		// getBottom() + offsetY);
		// mLastX = mCurrentX;
		// mLastY = mCurrentY;
		// break;
		// case MotionEvent.ACTION_UP:
		//
		// break;
		// }
		// postInvalidate();
		// return true;
		/**************************************************************************/
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastX = (int) event.getX();
			mLastY = (int) event.getY();
			break;

		case MotionEvent.ACTION_MOVE:
			mCurrentX = (int) event.getX();
			mCurrentY = (int) event.getY();
			int offsetX = mCurrentX - mLastX;
			int offsetY = mCurrentY - mLastY;
			/********************************************************************/
			// TODO 第一种采用layout
			// layout(getLeft() + offsetX, getTop() + offsetY, getRight() +
			// offsetX, getBottom() + offsetY);
			/********************************************************************/
			// 第二种采用offsetleftandright（offsetx） ， offsettopandbottom（offsety）
			// offsetLeftAndRight(offsetX);
			// offsetTopAndBottom(offsetY);
			/********************************************************************/
			// 第三种种采用layoutparams
			// MarginLayoutParams params = (MarginLayoutParams)
			// getLayoutParams();
			// params.leftMargin = params.leftMargin + offsetX;
			// params.topMargin = params.topMargin + offsetY;
			// setLayoutParams(params);
			/********************************************************************/
			// 第四种采用4：scrollto scrollby
			ViewGroup viewGroup = (ViewGroup) getParent();
			viewGroup.scrollBy(-offsetX, -offsetY);
			// viewGroup.scrollTo((viewGroup.getScrollX()-offsetX),
			// (viewGroup.getScrollY()-offsetY));
			break;
		case MotionEvent.ACTION_UP:
			ViewGroup parentView = (ViewGroup) getParent();
			mScroller.startScroll(parentView.getScrollX(), parentView.getScrollY(), -parentView.getScrollX(),
					-parentView.getScrollY(), 2 * 1000);
			break;
		}
		postInvalidate();
		return true;

	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			ViewGroup viewGroup = (ViewGroup) getParent();
			viewGroup.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			invalidate();
		}
	}

}
