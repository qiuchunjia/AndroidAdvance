package com.qcj.learning.view;

import java.util.Map;

import com.qcj.learning.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.view.View;

public class View6_7 extends View {
	/*****
	 * 
	 * android 图像处理之画笔特效处理
	 * 
	 ********/

	private static final String TAG = "View6_7";
	private Bitmap mBitmap;
	private Bitmap mOut;
	private Paint mPaint;

	public View6_7(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initSet();
	}

	public View6_7(Context context, AttributeSet attrs) {
		super(context, attrs);
		initSet();
	}

	public View6_7(Context context) {
		super(context);
		initSet();
	}

	private void initSet() {
		mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.girl);
		mOut = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Config.ARGB_8888);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.YELLOW);
		mPaint.setStyle(Style.FILL);
		Canvas canvas = new Canvas(mOut);
		canvas.drawRoundRect(new RectF(0, 0, mBitmap.getWidth(),mBitmap.getHeight()),30,30, mPaint);
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(mBitmap, 0, 0, mPaint);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(mOut, 0, 0, null);
	}

}
