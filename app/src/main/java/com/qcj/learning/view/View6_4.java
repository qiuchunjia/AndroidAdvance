package com.qcj.learning.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

public class View6_4 extends View {
	private static final String TAG = "View6_4";
	private int mWidth = 0;
	private int mHeigh = 0;
	private Paint mCiclePaint; // ��ԲȦ�Ļ���
	private Paint mDegreePaint; // ���̶ȵĻ���
	private Paint mHourPaint; // ��ʱ��Ļ���
	private Paint mMinutePaint; // �����ӵĻ���

	/****
	 * ʵ��һ��view�ӱ���ƣ���Ҫ��������ͼ��������
	 * 
	 **/
	public View6_4(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initSet();
	}

	public View6_4(Context context, AttributeSet attrs) {
		super(context, attrs);
		initSet();
	}

	public View6_4(Context context) {
		super(context);
		initSet();
	}

	private void initSet() {
		// ��ԲȦ�Ļ���
		mCiclePaint = new Paint();
		mCiclePaint.setColor(Color.GREEN);
		mCiclePaint.setStrokeWidth(5);
		mCiclePaint.setStyle(Paint.Style.STROKE);
		mCiclePaint.setAntiAlias(true);
		// ���̶ȵĻ���
		mDegreePaint = new Paint();
		mDegreePaint.setColor(Color.GRAY);
		mDegreePaint.setAntiAlias(true);
		// ��ʱ��Ļ���
		mHourPaint=new Paint();
		mHourPaint.setColor(Color.RED);
		mHourPaint.setStrokeWidth(10);
		mHourPaint.setAntiAlias(true);
		// �����ӵĻ���
		mMinutePaint=new Paint();
		mMinutePaint.setColor(Color.YELLOW);
		mMinutePaint.setStrokeWidth(5);
		mMinutePaint.setAntiAlias(true);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mWidth = getMeasuredWidth();
		mHeigh = getMeasuredHeight();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// ��ԲȦ
		canvas.drawCircle(mWidth / 2, mHeigh / 2, mWidth / 2, mCiclePaint);
		// ���̶Ⱥ�д��
		for (int i = 0; i < 24; i++) {
			if (i == 0 || i == 6 || i == 12 || i == 18) {
				mDegreePaint.setStrokeWidth(5);
				mDegreePaint.setTextSize(30);
				canvas.drawLine(mWidth / 2, mHeigh / 2 - mWidth / 2, mWidth / 2, mHeigh / 2 - mWidth / 2 + 45,
						mDegreePaint);
				String data = String.valueOf(i);
				canvas.drawText(data, mWidth / 2 - mDegreePaint.measureText(data) / 2, mHeigh / 2 - mWidth / 2 + 90,
						mDegreePaint);
			} else {
				mDegreePaint.setStrokeWidth(3);
				mDegreePaint.setTextSize(15);
				canvas.drawLine(mWidth / 2, mHeigh / 2 - mWidth / 2, mWidth / 2, mHeigh / 2 - mWidth / 2 + 30,
						mDegreePaint);
				String data = String.valueOf(i);
				canvas.drawText(data, mWidth / 2 - mDegreePaint.measureText(data) / 2, mHeigh / 2 - mWidth / 2 + 60,
						mDegreePaint);
			}
			canvas.rotate(15, mWidth / 2, mHeigh / 2);
		}
		// ��ʱ��ͷ���
		canvas.save();
		canvas.translate(mWidth / 2, mHeigh / 2);
		canvas.drawLine(0, 0, 50,80, mHourPaint);
		canvas.drawLine(0, 0, 30,150, mMinutePaint);
		canvas.restore();
	}

}
