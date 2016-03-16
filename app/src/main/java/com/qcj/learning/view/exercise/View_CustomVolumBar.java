package com.qcj.learning.view.exercise;

import com.qcj.learning.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class View_CustomVolumBar extends View {
	private static final String TAG = "View_CustomVolumBar";
	private Paint mPaint; // 画笔
	private int mFirstColor = 0xffff8934;// 第一种圆圈的颜色
	private int mSecondColor = 0xffff0000;// 第二种圆圈的颜色
	private int mCircleWidth = 30;// 圆圈的宽度
	private int mSplitSize = 8;// 每个小方块的间隙
	private RectF mRect;// 所以小方块所在的矩形区域‘
	private int mSize; // 小方块的幅度
	private int mCurrentCount = 3;// 当前的小方块个数
	private int mCount = 12;// 小方块的个数
	private int mRadius; // 外圆的半径
	private int mCenter; // 圆心
	private int mRelRadius; // 内切圆的半径
	private Bitmap mBitmap; // 中间的图片
	private Rect mInnerRect; // 中间的图片外切矩形

	public View_CustomVolumBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public View_CustomVolumBar(Context context) {
		super(context);
	}

	public View_CustomVolumBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initData();
	}

	/**
	 * attr中 从布局文件中获取数据
	 */
	private void initData() {

	}

	// private Paint mPaint; // 画笔
	// private int mFirstColor = 0xff0000;// 第一种圆圈的颜色
	// private int mSecondColor = 0xff8934;// 第二种圆圈的颜色
	// private int mCircleWidth = 30;// 圆圈的宽度
	// private int mSplitSize = 8;// 每个小方块的间隙
	// private int mRect;// 所以小方块所在的矩形区域‘
	// private int mRectSize; //小方块的幅度
	// private int mCurrentCount = 3;// 当前的小方块个数
	// private int mCount = 12;// 小方块的个数
	//
	// private int mRadius; // 外圆的半径
	// private int mCenter; // 圆心
	// private int mRelRadius; // 内切圆的半径
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		initSet();
	}

	private void initSet() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeCap(Cap.ROUND);
		mPaint.setStrokeJoin(Join.ROUND);
		mPaint.setStrokeWidth(mCircleWidth);
		mPaint.setColor(mFirstColor);
		mRadius = getMeasuredWidth() / 2 - mCircleWidth / 2;
		mCenter = getMeasuredWidth() / 2;
		mSize = (int) ((300 * 1.0 - (mCount - 1) * mSplitSize) / mCount);
		mRect = new RectF(mCenter - mRadius, mCenter - mRadius, mCenter + mRadius, mCenter + mRadius);
		mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.girl);
		mInnerRect = new Rect();
		mRelRadius = mRadius - mCircleWidth / 2; // 获取内切圆的半径
		mInnerRect.left = (int) (mRelRadius - mRelRadius * Math.sqrt(2) / 2 + mCircleWidth);
		mInnerRect.top = (int) (mRelRadius - mRelRadius * Math.sqrt(2) / 2 + mCircleWidth);
		mInnerRect.right = (int) (mInnerRect.left + mRelRadius * Math.sqrt(2));
		mInnerRect.bottom = (int) (mInnerRect.top + mRelRadius * Math.sqrt(2));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		drawOval(canvas);
		drawBitmap(canvas); // 画中间的图片

	}

	/**
	 * 画中间的图片
	 * 
	 * @param canvas
	 */
	private void drawBitmap(Canvas canvas) {
		int bitmapWidth = mBitmap.getWidth();
		int bitmapHeight = mBitmap.getHeight();
		int maxDistance = Math.max(bitmapWidth, bitmapHeight);
		if (maxDistance < mInnerRect.width()) {
			mInnerRect.left = (int) (mInnerRect.left + mRelRadius * Math.sqrt(2) / 2 - mBitmap.getWidth() / 2);
			mInnerRect.top = (int) (mInnerRect.top + mRelRadius * Math.sqrt(2) / 2 - mBitmap.getHeight() / 2);
			mInnerRect.right = mInnerRect.left + mBitmap.getWidth();
			mInnerRect.bottom = mInnerRect.top + mBitmap.getHeight();
		}
		canvas.drawBitmap(mBitmap, null, mInnerRect, mPaint);
	}

	/**
	 * 画外面的密密麻麻的小方块
	 * 
	 * @param canvas
	 * @param mCenter2
	 * @param mRadius2
	 */
	private void drawOval(Canvas canvas) {
		mPaint.setColor(mFirstColor);
		for (int i = 0; i < mCount; i++) {
			canvas.drawArc(mRect, 125 + i * (mSize + mSplitSize), mSize, false, mPaint);
		}
		mPaint.setColor(mSecondColor);
		for (int i = 0; i < mCurrentCount; i++) {
			canvas.drawArc(mRect, 125 + i * (mSize + mSplitSize), mSize, false, mPaint);
		}
	}

	float currentY;
	float currentX;
	float lastY;
	float lastX;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = event.getX();
			lastY = event.getY();
			break;

		case MotionEvent.ACTION_UP:
			currentX = event.getX();
			currentY = event.getY();
			float dx = currentX - lastX;
			float dy = currentY - lastY;
			if (dy > 0) {
				if (Math.abs(dx) < Math.abs(dy)) {
					down();
				}
			} else {
				if (Math.abs(dx) < Math.abs(dy)) {
					up();
				}
			}

			break;
		}
		return true;
	}

	/**
	 * 关小声音
	 */
	public void down() {
		mCurrentCount--;
		if (mCurrentCount < 0) {
			mCurrentCount = 0;
		}
		postInvalidate();
	}

	/**
	 * 放大声音
	 */
	public void up() {
		mCurrentCount++;
		if (mCurrentCount > 12) {
			mCurrentCount = mCount;
		}
		postInvalidate();
	}

}
