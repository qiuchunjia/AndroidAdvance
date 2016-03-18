package com.qcj.learning.view;

import com.qcj.learning.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class View6_7_XferModeView extends View {
	/*****
	 * 
	 * android 图像处理之画笔特效处理 实现刮刮卡效果
	 * 
	 ********/

	private static final String TAG = "View6_7_XferModeView";
	private Bitmap mBgbitmap;
	private Bitmap mFgbitmap;
	private Paint mPaint;
	private Canvas mCanvas;
	private Path mPath;

	public View6_7_XferModeView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initSet();
	}

	public View6_7_XferModeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initSet();
	}

	public View6_7_XferModeView(Context context) {
		super(context);
		initSet();
	}

	private void initSet() {
		mBgbitmap = BitmapFactory.decodeResource(getResources(), R.drawable.girl);
		mFgbitmap = Bitmap.createBitmap(mBgbitmap.getWidth(), mBgbitmap.getHeight(), Config.ARGB_8888);
		mCanvas = new Canvas(mFgbitmap);
		mCanvas.drawColor(Color.GRAY);
		mPaint = new Paint();
		mPaint.setColor(Color.BLACK);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeJoin(Join.ROUND);
		mPaint.setStrokeWidth(50);
		mPaint.setStrokeCap(Cap.ROUND);
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
		mPath = new Path();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mPath.reset();
			mPath.moveTo(event.getX(), event.getY());
			break;

		case MotionEvent.ACTION_MOVE:
			mPath.lineTo(event.getX(), event.getY());
			break;
		}
		mCanvas.drawPath(mPath, mPaint);
		invalidate();
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(mBgbitmap, 0, 0, null);
		canvas.drawBitmap(mFgbitmap, 0, 0, null);
	}

}
