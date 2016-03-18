package com.qcj.learning.view.exercise;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.qcj.learning.R;

/**
 * Created by qiuchunjia on 2016/3/7.
 * 实现点击后弹出菜单
 */
public class ViewGroup_ArcMenu extends ViewGroup implements View.OnClickListener {
    private static final String TAG="ViewGroup_ArcMenu";
    /*
    * 菜单显示的位置
    * */
    private Position mPosition = Position.LEFT_TOP;
    /*
    *半径的默认长度
    * */
    private int mRadius = 400;
    /*
    *用户点击的按钮
    * */

    private View mButton;
      /*
    *默认的状态
    * */

    private Status mCurrentStatus = Status.CLOSE;
    /**
         * 回调接口
         */

    private OnMenuItemClickListener onMenuItemClickListener;

    /**
     * 状态的枚举类
     *
     *
     *
     */
    public enum Status
    {
        OPEN, CLOSE
    }
    /**
     * 设置菜单现实的位置，四选1，默认右下
     *
     */
    public enum Position
    {
        LEFT_TOP, RIGHT_TOP, RIGHT_BOTTOM, LEFT_BOTTOM;
    }
    public ViewGroup_ArcMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context,attrs);
    }

    public ViewGroup_ArcMenu(Context context) {
        super(context);
    }

    public ViewGroup_ArcMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initData(context, attrs);
    }
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
public void initData(Context context,AttributeSet attrs){
    Log.e(TAG, "ViewGroup_ArcMenu" + " , ");
    // dp convert to px
    mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            mRadius, getResources().getDisplayMetrics());
    TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
           R.styleable.ArcMenu, 0, 0);


    int n = a.getIndexCount();
    Log.e(TAG, "n====" + " , " + n);
    for (int i = 0; i < n; i++)
    {
        int attr = a.getIndex(i);
        switch (attr)
        {
            case R.styleable.ArcMenu_position:
                int val = a.getInt(attr, 0);
                Log.e(TAG, "val=" + " , " + val);
                switch (val)
                {
                    case 0:
                        mPosition = Position.LEFT_TOP;
                        break;
                    case 1:
                        mPosition = Position.RIGHT_TOP;
                        break;
                    case 2:
                        mPosition = Position.RIGHT_BOTTOM;
                        break;
                    case 3:
                        mPosition = Position.LEFT_BOTTOM;
                        break;
                }
                break;
            case R.styleable.ArcMenu_radius:
                // dp convert to px
                mRadius = a.getDimensionPixelSize(attr, (int) TypedValue
                        .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f,
                                getResources().getDisplayMetrics()));
                Log.e(TAG, "mRadius=" + " , " + mRadius);
                break;

        }
    }
    a.recycle();
}

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec); // 测量子view的宽高
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
            if (changed){
                layoutButton();
                int count = getChildCount();
                for (int i = 0; i < count - 1; i++)
                            {
                                View child = getChildAt(i + 1);
                                 child.setVisibility(View.GONE);
                                /*
                                *Math.sin(double a),里面的参数是弧度制的角度，需要用Math.PI表示，比如90度，就要用Math.PI/2.
                                * */
                                int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2)
                                        * i));
                                 int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2)
                                            * i));
                                    // childview width
                                    int cWidth = child.getMeasuredWidth();
                                    // childview height
                                    int cHeight = child.getMeasuredHeight();

                                    // 左下，右下
                                    if (mPosition == Position.LEFT_BOTTOM
                                            || mPosition == Position.RIGHT_BOTTOM)
                                    {
                                            ct = getMeasuredHeight() - cHeight - ct;
                                        }
                                    // 右上，右下
                                    if (mPosition == Position.RIGHT_TOP
                                            || mPosition == Position.RIGHT_BOTTOM){
                                            cl = getMeasuredWidth() - cWidth - cl;
                                        }

                                    Log.e(TAG, cl + " , " + ct);
                                    child.layout(cl, ct, cl + cWidth, ct + cHeight);
                                }

            }
    }
/**
 * 第一个子元素为按钮，为按钮布局且初始化点击事件
  */
   private void layoutButton()
    {
            View cButton = getChildAt(0);
            cButton.setOnClickListener(this);
            int l = 0;
            int t = 0;
            int width = cButton.getMeasuredWidth();
            int height = cButton.getMeasuredHeight();
            switch (mPosition)
            {
                case LEFT_TOP:
                        l = 0;
                        t = 0;
                        break;
                case LEFT_BOTTOM:
                        l = 0;
                        t = getMeasuredHeight() - height;
                        break;
                case RIGHT_TOP:
                        l = getMeasuredWidth() - width;
                        t = 0;
                        break;
                case RIGHT_BOTTOM:
                    l = getMeasuredWidth() - width;
                        t = getMeasuredHeight() - height;
                        break;

            }
        Log.e(TAG, l + " , " + t + " , " + (l + width) + " , " + (t + height));
            cButton.layout(l, t, l + width, t + height);
        }

    @Override
    public void onClick(View v) {
        mButton = findViewById(R.id.id_button);
        if (mButton == null) {
            mButton = getChildAt(0);
        }
        rotateView(mButton, 0f, 270f, 300);
        toggleMenu(300);

    }

    /**
          * 按钮的旋转动画
       *
        * @param view
      * @param fromDegrees
   * @param toDegrees
.     * @param durationMillis
      */
    public static void rotateView(View view, float fromDegrees, float toDegrees, int durationMillis){
           RotateAnimation rotate = new RotateAnimation(fromDegrees, toDegrees,
                             Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                             0.5f);
             rotate.setDuration(durationMillis);
             rotate.setFillAfter(true);
           view.startAnimation(rotate);
          }
    /*
    * 开关菜单
    * */
    public void toggleMenu(int durationMillis){
        int count = getChildCount();
        for (int i = 0; i < count - 1; i++) {
            final View childView = getChildAt(i + 1);
            childView.setVisibility(View.VISIBLE);
                        int xflag = 1;
                        int yflag = 1;
                        if (mPosition == Position.LEFT_TOP
                                || mPosition == Position.LEFT_BOTTOM)
                            xflag = -1;
                     if (mPosition == Position.LEFT_TOP
                          || mPosition == Position.RIGHT_TOP)
                        yflag = -1;

                    // child left
                  int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
                     // child top
                    int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));

                     AnimationSet animset = new AnimationSet(true);
                     Animation animation = null;
                      if (mCurrentStatus == Status.CLOSE)
                          {// to open
                            animset.setInterpolator(new OvershootInterpolator(2F));
                            animation = new TranslateAnimation(xflag * cl, 0, yflag * ct, 0);
                             childView.setClickable(true);
                              childView.setFocusable(true);
                         } else
                     {// to close
                            animation = new TranslateAnimation(0f, xflag * cl, 0f, yflag
                                    * ct);
                             childView.setClickable(false);
                             childView.setFocusable(false);
                        }
                     animation.setAnimationListener(new Animation.AnimationListener() {
                         public void onAnimationStart(Animation animation) {
                         }

                         public void onAnimationRepeat(Animation animation) {
                         }

                         public void onAnimationEnd(Animation animation) {


                         }
                     });
            animation.setFillAfter(true);
                        animation.setDuration(durationMillis);
                        // 为动画设置一个开始延迟时间，纯属好看，可以不设
                 animation.setStartOffset((i * 500) / (count - 1));
                     RotateAnimation rotate = new RotateAnimation(0, 720,
                                  Animation.RELATIVE_TO_SELF, 0.5f,
                                     Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(durationMillis);
                    rotate.setFillAfter(true);
                  animset.addAnimation(rotate);
                    animset.addAnimation(animation);
                   childView.startAnimation(animset);
                      final int index = i + 1;
                      childView.setOnClickListener(new View.OnClickListener()
                    {
                       @Override
                               public void onClick(View v)
                              {
                                      if (onMenuItemClickListener != null)
                                            onMenuItemClickListener.onClick(childView, index - 1);
                                     menuItemAnin(index - 1);
//                                     changeStatus();

                                   }
                          });

                  }
//             changeStatus();
               Log.e(TAG, mCurrentStatus.name() +"");

    }

    /**
         * 开始菜单动画，点击的MenuItem放大消失，其他的缩小消失
        * @param item
        */
              private void menuItemAnin(int item)
       {
              for (int i = 0; i < getChildCount() - 1; i++)
                  {
                       View childView = getChildAt(i + 1);
                       if (i == item)
                          {
                               childView.startAnimation(scaleBigAnim(300));
                           } else {
                               childView.startAnimation(scaleSmallAnim(300));
                           }
                        childView.setClickable(false);
                        childView.setFocusable(false);

                    }

           }
    /**
         * 缩小消失
    * @param durationMillis
         * @return
       */
      private Animation scaleSmallAnim(int durationMillis)
      {
               Animation anim = new ScaleAnimation(1.0f, 0f, 1.0f, 0f,
                               Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                               0.5f);
             anim.setDuration(durationMillis);
               anim.setFillAfter(true);
               return anim;
          }
    /**
         * 放大，透明度降低
         * @param durationMillis
         * @return
          */
      private Animation scaleBigAnim(int durationMillis) {
              AnimationSet animationset = new AnimationSet(true);
               Animation anim = new ScaleAnimation(1.0f, 4.0f, 1.0f, 4.0f,
                       Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                       0.5f);
          Animation alphaAnimation = new AlphaAnimation(1, 0);
                animationset.addAnimation(anim);
                animationset.addAnimation(alphaAnimation);
               animationset.setDuration(durationMillis);
               animationset.setFillAfter(true);
               return animationset;
           }

  interface   OnMenuItemClickListener{
    void   onClick(View view, int pos);
  }



}

