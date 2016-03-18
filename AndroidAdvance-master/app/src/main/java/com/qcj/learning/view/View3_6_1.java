package com.qcj.learning.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

public class View3_6_1 extends TextView {
	private Paint mPaint1;
	private Paint mPaint2;

	public View3_6_1(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initPaint();
	}

	public View3_6_1(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	public View3_6_1(Context context) {
		super(context);
		initPaint();
	}

	/**
	 * ��ʼ������
	 */
	private void initPaint() {
		mPaint1 = new Paint();
		mPaint1.setColor(getResources().getColor(android.R.color.holo_blue_light));
		mPaint1.setStyle(Paint.Style.FILL);
		mPaint2 = new Paint();
		mPaint2.setColor(Color.YELLOW);
		mPaint2.setStyle(Paint.Style.FILL);
		mPaint2.setAntiAlias(true);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
//		// ���ø���֮ǰ
		canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint1);
		RectF oval = new RectF(60, 60, getMeasuredWidth() - 60, getMeasuredHeight() - 60);
		canvas.drawOval(oval, mPaint2);
		canvas.save();
		canvas.translate(180, 180);
//		int px = getMeasuredWidth();
//		int py = getMeasuredWidth();
//		// Draw background
//		canvas.drawRect(0, 0, px, py, mPaint1);
//		canvas.save();
//		canvas.rotate(90, px/2, py/2);
//		// Draw up arrow
//		canvas.drawLine(px / 2, 0, 0, py / 2, mPaint2);
//		canvas.drawLine(px / 2, 0, px, py / 2, mPaint2);
//		canvas.drawLine(px / 2, 0, px / 2, py, mPaint2);
//		canvas.restore();
//		// Draw circle
//		canvas.drawCircle(px - 10, py - 10, 10, mPaint2);
		super.onDraw(canvas);
		canvas.restore();
		// ���ø���֮��
	}

}
