package com.qcj.learning.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class View3_6_2 extends TextView {
	private static final String TAG = "View3_6_2";
	private int mViewWidth = 0;
	private Paint mpaint;
	private LinearGradient mLinearGradient;
	private Matrix mGradientMatrix;
	private int mTranslate=0;

	public View3_6_2(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public View3_6_2(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public View3_6_2(Context context) {
		super(context);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		Log.i(TAG, "onSizeChanged=" + "w=" + w + "  h=" + h + "  oldw=" + oldw + "  oldh=" + oldh);
		super.onSizeChanged(w, h, oldw, oldh);
		if (mViewWidth == 0) {
			mViewWidth = getMeasuredWidth();
			if (mViewWidth > 0) {
				mpaint = new Paint();
				mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0,
						new int[] { Color.BLUE, 0xffffffff, Color.BLUE }, null, Shader.TileMode.CLAMP);
				mpaint.setShader(mLinearGradient);
				mGradientMatrix=new Matrix();
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(mGradientMatrix!=null){
			mTranslate+=mViewWidth/5;
			if(mTranslate>2*mViewWidth){
				mTranslate=-mViewWidth;
			}
			mGradientMatrix.setTranslate(mTranslate, 0);
			mLinearGradient.setLocalMatrix(mGradientMatrix);
			postInvalidateDelayed(100);
		}

	}

}
