package com.qcj.learning;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.qcj.learning.view.exercise.clipimage.ClipImageLayout;

public class MainActivity extends Activity implements OnClickListener {
    // private View3_6_3_1 view3_6_3_1;
    // private float mProgressValue = 0;
    // private Handler mHandle = new Handler() {
    // public void handleMessage(android.os.Message msg) {
    // float value = (Float) msg.obj;
    // if (value <= 100) {
    // view3_6_3_1.setSweepValue(value);
    // }
    // };
    // };
    private ClipImageLayout clipImageLayout;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.viewgroup_clipimage);
        clipImageLayout = (ClipImageLayout) findViewById(R.id.clipImageLayout);
        clipImageLayout.setImageResoure(R.drawable.girl);
        // view3_6_3_1 = (View3_6_3_1) findViewById(R.id.view3_6_3_1);
        // view3_6_3_1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // case R.id.view3_6_3_1:
            // new Thread() {
            // public void run() {
            // mProgressValue = 0;
            // while (true) {
            // mProgressValue = mProgressValue +2;
            // Message message = Message.obtain();
            // message.obj = mProgressValue;
            // mHandle.sendMessage(message);
            // if (mProgressValue > 100) {
            // break;
            // }
            // try {
            // Thread.sleep(20);
            // } catch (InterruptedException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // }
            //
            // }
            // };
            // }.start();
            // break;


        }
    }

}
