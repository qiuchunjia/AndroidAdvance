package com.qcj.learning.util;



import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 閻€劋绨珻ommonUtils閻ㄥ嫬浼愰崗鐑芥肠閸氬牏琚�
 * 
 * @author qcj
 * 
 */
public class UIUtils {

	private static final String TAG = "UIUtils";

	/**
	 * 闂呮劘妫屾潏鎾冲弳濞夛拷
	 * 
	 * @param paramContext
	 * @param paramEditText
	 */
	public static void hideSoftKeyboard(Context paramContext,
			EditText paramEditText) {
		((InputMethodManager) paramContext
				.getSystemService(Context.INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(paramEditText.getWindowToken(), 0);
	}

	/**
	 * 閺勫墽銇氭潏鎾冲弳濞夛拷
	 * 
	 * @param paramContext
	 * @param paramEditText
	 */
	public static void showSoftKeyborad(Context paramContext,
			EditText paramEditText) {
		((InputMethodManager) paramContext
				.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(
				paramEditText, InputMethodManager.SHOW_FORCED);
	}

	/**
	 * 閺嶈宓侀幍瀣簚閻ㄥ嫬鍨庢潏銊у芳娴狅拷 dp 閻ㄥ嫬宕熸担锟� 鏉烆剚鍨氭稉锟� px(閸嶅繒绀�)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 閺嶈宓侀幍瀣簚閻ㄥ嫬鍨庢潏銊у芳娴狅拷 px(閸嶅繒绀�) 閻ㄥ嫬宕熸担锟� 鏉烆剚鍨氭稉锟� dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 閼惧嘲褰囩仦蹇撶鐎硅棄瀹�
	 * 
	 * @param context
	 * @return
	 */
	public static int getWindowWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 閼惧嘲褰囩仦蹇撶妤傛ê瀹�
	 * 
	 * @param context
	 * @return
	 */

	public static int getWindowHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

}
