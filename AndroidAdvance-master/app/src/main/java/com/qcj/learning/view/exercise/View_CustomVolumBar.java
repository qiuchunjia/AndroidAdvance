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
	private Paint mPaint; // ����
	private int mFirstColor = 0xffff8934;// ��һ��ԲȦ����ɫ
	private int mSecondColor = 0xffff0000;// �ڶ���ԲȦ����ɫ
	private int mCircleWidth = 30;// ԲȦ�Ŀ��
	private int mSplitSize = 8;// ÿ��С����ļ�϶
	private RectF mRect;// ����С�������ڵľ�������
	private int mSize; // С����ķ���
	private int mCurrentCount = 3;// ��ǰ��С�������
	private int mCount = 12;// С����ĸ���
	private int mRadius; // ��Բ�İ뾶
	private int mCenter; // Բ��
	private int mRelRadius; // ����Բ�İ뾶
	private Bitmap mBitmap; // �м��ͼƬ
	private Rect mInnerRect; // �м��ͼƬ���о���

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
	 * attr�� �Ӳ����ļ��л�ȡ����
	 */
	private void initData() {

	}

	// private Paint mPaint; // ����
	// private int mFirstColor = 0xff0000;// ��һ��ԲȦ����ɫ
	// private int mSecondColor = 0xff8934;// �ڶ���ԲȦ����ɫ
	// private int mCircleWidth = 30;// ԲȦ�Ŀ��
	// private int mSplitSize = 8;// ÿ��С����ļ�϶
	// private int mRect;// ����С�������ڵľ�������
	// private int mRectSize; //С����ķ���
	// private int mCurrentCount = 3;// ��ǰ��С�������
	// private int mCount = 12;// С����ĸ���
	//
	// private int mRadius; // ��Բ�İ뾶
	// private int mCenter; // Բ��
	// private int mRelRadius; // ����Բ�İ뾶
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
		mRelRadius = mRadius - mCircleWidth / 2; // ��ȡ����Բ�İ뾶
		mInnerRect.left = (int) (mRelRadius - mRelRadius * Math.sqrt(2) / 2 + mCircleWidth);
		mInnerRect.top = (int) (mRelRadius - mRelRadius * Math.sqrt(2) / 2 + mCircleWidth);
		mInnerRect.right = (int) (mInnerRect.left + mRelRadius * Math.sqrt(2));
		mInnerRect.bottom = (int) (mInnerRect.top + mRelRadius * Math.sqrt(2));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		drawOval(canvas);
		drawBitmap(canvas); // ���м��ͼƬ

	}

	/**
	 * ���м��ͼƬ
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
	 * ����������������С����
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
	 * ��С����
	 */
	public void down() {
		mCurrentCount--;
		if (mCurrentCount < 0) {
			mCurrentCount = 0;
		}
		postInvalidate();
	}

	/**
	 * �Ŵ�����
	 */
	public void up() {
		mCurrentCount++;
		if (mCurrentCount > 12) {
			mCurrentCount = mCount;
		}
		postInvalidate();
	}

}
