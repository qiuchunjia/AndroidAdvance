package com.qcj.learning.view.exercise.clipimage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.qcj.learning.R;

/**
 * 装裁剪图片的父布局
 * Created by qiuchunjia on 2016/3/18.
 */
public class ClipImageLayout extends RelativeLayout {
    private ClipBorderView mBorderView;
    private ClipImageView mImageView;
    /**
     * 边框的颜色，默认为白色
     */
    private int mBorderColor = Color.parseColor("#FFFFFF");
    /**
     * 边框的外面的颜色，默认为半透明黑色
     */
    private int mBorderOutColor = Color.parseColor("#aa000000");
    private int mHorizontalPadding = 20;    //设置距离

    public ClipImageLayout(Context context) {
        super(context);
    }

    public ClipImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context, attrs);
    }

    private void initData(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.ClipImageLayout, 0, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.ClipImageLayout_border_color:
                    mBorderColor = a.getColor(attr,
                            mBorderColor);
                    break;
                case R.styleable.ClipImageLayout_border_out_color:
                    mBorderOutColor = a.getColor(attr,
                            mBorderOutColor);
                    break;
                case R.styleable.ClipImageLayout_horizontalPadding:
                    mHorizontalPadding = (int) a.getDimension(attr, mHorizontalPadding);
                    break;
            }
        }
        a.recycle();
        mBorderView = new ClipBorderView(getContext());
        mImageView = new ClipImageView(getContext());
        RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBorderView.setLayoutParams(params);
        mImageView.setLayoutParams(params);
        mBorderView.setmBorderColor(mBorderColor);
        mBorderView.setmBorderOutColor(mBorderOutColor);
        mBorderView.setmHorizontalPadding(mHorizontalPadding);
        mImageView.setmHorizontalPadding(mHorizontalPadding);
        addView(mImageView);
        addView(mBorderView);
    }

    public Bitmap clipBorderImage() {
        return mImageView.clipBorderImage();
    }

    public void setDrawable(Drawable drawable) {
        if (drawable != null) {
            mImageView.setImageDrawable(drawable);
        }
        try {
            throw new Exception("drawable 不能为空");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setImageResoure(int rid) {
        mImageView.setImageResource(rid);
    }

    public void setBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            mImageView.setImageBitmap(bitmap);
            return;
        }
        try {
            throw new Exception("bitmap 不能为空");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
