package com.qcj.learning.view;

import com.qcj.learning.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class View6_7_2_ReflectView extends View {

	/****
	 * »­³öµ¹Ó°
	 * 
	 * 
	 * ******/
	private static final String TAG = "View_6_7_2_ReflectView";
	private Bitmap mSrcBitmap;
	private Bitmap mRefBitmap;
	private Paint mPaint;
	private PorterDuffXfermode mXfermode;

	public View6_7_2_ReflectView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initSet();
	}

	public View6_7_2_ReflectView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initSet();
	}

	public View6_7_2_ReflectView(Context context) {
		super(context);
		initSet();
	}

	private void initSet() {
		mSrcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.girl);
		Matrix matrix = new Matrix();
		matrix.setScale(1f, -1f);
		mRefBitmap = Bitmap.createBitmap(mSrcBitmap, 0, 0, mSrcBitmap.getWidth(), mSrcBitmap.getHeight(), matrix, true);
		mPaint = new Paint();
		mPaint.setShader(new LinearGradient(0, mSrcBitmap.getHeight(), 0, (int) (1.25 * mSrcBitmap.getHeight()),
				0xff000000, 0x0000000, Shader.TileMode.CLAMP));
		mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
		mPaint.setColor(Color.RED);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.GRAY);
		canvas.drawBitmap(mSrcBitmap, 0, 0, null);
		canvas.drawBitmap(mRefBitmap, 0, mSrcBitmap.getHeight(), null);
		mPaint.setXfermode(mXfermode);
		canvas.drawRect(0, mSrcBitmap.getHeight(), mSrcBitmap.getWidth(), 2 * mSrcBitmap.getHeight(), mPaint);
		mPaint.setXfermode(null);
	}

}
