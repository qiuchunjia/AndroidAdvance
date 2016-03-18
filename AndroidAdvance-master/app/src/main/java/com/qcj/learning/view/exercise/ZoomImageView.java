package com.qcj.learning.view.exercise;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by qiuchunjia on 2016/3/17
 * 实现点击图片放大放小
 */
public class ZoomImageView extends ImageView implements ScaleGestureDetector.OnScaleGestureListener, ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = ZoomImageView.class.getSimpleName();
    public static final float SCALE_MAX = 4.0f;
    public static final float SCALE_MIDDLE =2.0f;
    private float initScale = 1.0f;
    /**
     * 初始化时的缩放比例，如果图片宽或高大于屏幕，此值将小于1
     * /
     * /
     * 用于存放矩阵的9个值
     */
    private final float[] matrixValues = new float[9];
    private boolean once = true;
    /**
     * 缩放的手势检测
     */
    private ScaleGestureDetector mScaleGestureDetector = null;
    private final Matrix mScaleMatrix = new Matrix();
    private boolean isCheckleftAndRight; //移动的时候是否超过左右
    private boolean isCheckTopAndBottom; //移动的时候是否超过上下
    private float mLastX, mLastY = 0;
    private int mLastPointCount;
    private GestureDetector mGestureDetetor;
    private boolean isAutoScale;

    public ZoomImageView(Context context) {
        super(context);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setScaleType(ScaleType.MATRIX);
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), this);
        mGestureDetetor = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (isAutoScale) {
                    return true;
                }
                final float x = e.getX();
                final float y = e.getY();
                if (getScale() < SCALE_MIDDLE) {
                    //第一种方式
                    AutoScaleThread autoScaleThread = new AutoScaleThread(SCALE_MIDDLE, x, y);
                    ZoomImageView.this.postDelayed(autoScaleThread, 16);
                    isAutoScale = true;
//                    ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(ZoomImageView.this,"qcj",1,2);
//                    objectAnimator.setDuration(1000);
//                    objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator animation) {
//                            float factorScale = 1.07f;
//                            if (getScale() >= SCALE_MIDDLE) {
//                                factorScale = SCALE_MIDDLE / getScale();
//                                isAutoScale=false;
//                            }
//                            mScaleMatrix.postScale(factorScale, factorScale, x, y);
//                            checkBorderAndCenter();
//                            setImageMatrix(mScaleMatrix);
//                        }
//                    });
//                    objectAnimator.start();
                } else {
                    //第一种方式
                    AutoScaleThread autoScaleThread = new AutoScaleThread(initScale, x, y);
                    ZoomImageView.this.postDelayed(autoScaleThread, 16);
                    isAutoScale = true;
                    //第二种方式
//                    ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(ZoomImageView.this,"qcj",1,2);
//                    objectAnimator.setDuration(500);
//                    objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator animation) {
//                            float factorScale=0.93f;
//                            if(getScale()<initScale){
//                                factorScale=initScale/getScale();
//                                isAutoScale=false;
//                            }
//                            mScaleMatrix.postScale(factorScale, factorScale, x, y);
//                            checkBorderAndCenter();
//                            setImageMatrix(mScaleMatrix);
//                        }
//                    });
//                    objectAnimator.start();

                }
                return true;
            }
        });
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        Log.v(TAG, "onScale==================");
        float scale = getScale();
        float factorScale = detector.getScaleFactor();
        if (scale * factorScale >= SCALE_MAX) {
            factorScale = SCALE_MAX / scale;
        }
        if (scale * factorScale <= initScale) {
            factorScale = initScale / scale;
        }
        mScaleMatrix.postScale(factorScale, factorScale, detector.getFocusX(), detector.getFocusY());
        checkBorderAndCenter();
        setImageMatrix(mScaleMatrix);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        if (mGestureDetetor.onTouchEvent(event)) {
            return true;
        }
        float x = 0, y = 0;
        int pointCount = event.getPointerCount();
        for (int i = 0; i < pointCount; i++) {
            x = x + event.getX(i);
            y = y + event.getY(i);
        }
        x = x / pointCount;
        y = y / pointCount;
        //判断手指接触的数量，当改变了的话 重置mlastx，mlasty
        if (mLastPointCount != pointCount) {
            mLastX = x;
            mLastY = y;
        }
        mLastPointCount = pointCount;
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - mLastX;
                float dy = y - mLastY;
                isCheckleftAndRight = isCheckTopAndBottom = true;
                RectF rectF = getMaxtrixRecF();
                //当图片小于屏幕的时候
                if (rectF.width() < getWidth()) {
                    isCheckleftAndRight = false;
                    dx = 0;
                }
                if (rectF.height() < getHeight()) {
                    isCheckTopAndBottom = false;
                    dy = 0;
                }
                mScaleMatrix.postTranslate(dx, dy);
                checkMatrixBounds();
                setImageMatrix(mScaleMatrix);
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastPointCount = 0;
                break;
        }


        return true;
    }

    private class AutoScaleThread implements Runnable {
        static final float bigger = (float) 1.07;
        static final float smaller = (float) 0.93;
        private float mTargetScale;
        private float mTmpScale;
        float mX;
        float mY;

        public AutoScaleThread(float targetScale, float x, float y) {
            this.mTargetScale = targetScale;
            this.mX = x;
            this.mY = y;
            if (getScale() > targetScale) {
                mTmpScale = smaller;
            } else {
                mTmpScale = bigger;
            }
        }

        @Override
        public void run() {
            checkBorderAndCenter();
            mScaleMatrix.postScale(mTmpScale, mTmpScale, mX, mY);
            setImageMatrix(mScaleMatrix);
            Log.e(TAG, "getscale==================" + getScale());
            if (mTmpScale > 1.0f && (getScale() * mTmpScale < mTargetScale) || (mTmpScale < 1.0 && (getScale() * mTmpScale > mTargetScale))) {
                ZoomImageView.this.postDelayed(this, 16);
            } else {
                mTmpScale = mTargetScale / getScale();
                mScaleMatrix.postScale(mTmpScale, mTmpScale, mX, mY);
                checkBorderAndCenter();
                setImageMatrix(mScaleMatrix);
                isAutoScale = false;
            }
        }
    }

    /**
     * 移动的时候检查边界
     */
    public void checkMatrixBounds() {
        RectF rectF = getMaxtrixRecF();
        float dx = 0;
        float dy = 0;
        float width = getWidth();
        float height = getHeight();
        if (rectF.left > 0 && isCheckleftAndRight) {
            dx = -rectF.left;
        }
        if (rectF.right < width && isCheckleftAndRight) {
            dx = width - rectF.right;
        }
        if (rectF.top > 0 && isCheckTopAndBottom) {
            dy = -rectF.top;
        }
        if (rectF.bottom < height && isCheckTopAndBottom) {
            dy = height - rectF.bottom;
        }
        Log.e(TAG, "dx=" + dx + "   dy=" + dy);
        mScaleMatrix.postTranslate(dx, dy);

    }

    /**
     * 检查边界和中心点 当变化的时候
     */
    public void checkBorderAndCenter() {
        RectF rectF = getMaxtrixRecF();
        float dx = 0;
        float dy = 0;
        int width = getWidth();
        int height = getHeight();
        if (rectF.width() > width) {
            if (rectF.left > 0) {
                dx = -rectF.left;
            }
            if (rectF.right > width) {
                dx = width - rectF.right;
            }
        }
        if (rectF.height() > height) {
            if (rectF.top > 0) {
                dy = -rectF.top;
            }
            if (rectF.bottom > height) {
                dy = height - rectF.bottom;
            }
        }
        if (rectF.width() <= width) {
//            dx = width * 0.5f - rectF.right + 0.5f * rectF.width();
            Log.e(TAG, " width * 0.5f=" + width * 0.5f + "   rectF.right=" + rectF.right + "   0.5f * rectF.width()=" + 0.5f * rectF.width());
            //重点步骤
            dx = width / 2 - (rectF.right - rectF.width() / 2);
        }
        if (rectF.height() <= height) {
//            dy = height * 0.5f - rectF.bottom + 0.5f * rectF.height();   //重点步骤
            dy = height / 2 - (rectF.bottom - rectF.height() / 2);
        }
        Log.e(TAG, "dx====" + dx + "   dy=" + dy);
        mScaleMatrix.postTranslate(dx
                , dy);

    }

    /**
     * 获取图片的矩阵
     *
     * @return
     */
    private RectF getMaxtrixRecF() {
        Matrix matrix = mScaleMatrix;
        Drawable drawable = getDrawable();
        RectF rectF = new RectF();
        if (drawable != null) {
            rectF.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }
        return rectF;
    }


    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        //返回ture才会进行下一步操作 onScale
        Log.v(TAG, "onScaleBegin==================");
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        Log.v(TAG, "onScaleEnd==================");
    }


    public final float getScale() {
        mScaleMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        if (once) {
            Drawable drawable = getDrawable();
            if (drawable == null) {
                return;
            }
            int dw = drawable.getIntrinsicWidth();
            int dh = drawable.getIntrinsicHeight();
            Log.e(TAG, "dw==" + dw + "   dh=" + dh);
            int width = getWidth();
            int height = getHeight();
            Log.e(TAG, "width==" + width + "   height=" + height);
            float scale = 1;
            //当宽高于屏幕的时候
            if (dw > width && dh <= dh) {
                scale = (float) (width * 1.0 / dw);
            }
            //当高高于屏幕的时候
            if (dh > height && dw <= width) {
                scale = (float) (height * 1.0 / dh);
            }
            //当高高于屏幕的时候,宽大于屏幕的时候
            if (dw > width && dh > height) {
                scale = Math.min((float) (width * 1.0 / dw), (float) (height * 1.0 / dh));
            }
            initScale = scale;
            mScaleMatrix.postTranslate((width - dw) / 2, (height - dh) / 2); //dx 大于零 向右移动，dy大于零就向下移动
            mScaleMatrix.postScale(initScale, initScale, width / 2, height / 2);
            setImageMatrix(mScaleMatrix);
        }
        once = false;
    }

}
