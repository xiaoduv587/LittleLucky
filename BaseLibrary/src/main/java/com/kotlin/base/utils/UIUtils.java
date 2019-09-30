package com.kotlin.base.utils;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kotlin.base.R;
import com.kotlin.base.common.BaseApplication;


public class UIUtils {


    //先将BaseApplication中提供出来的所有的变量提供相应的一种获取方式
    public static Context getContext() {
        return BaseApplication.Companion.getContext();
    }

//    public static Handler getHandler() {
//        return BaseApplication.getHandler();
//    }

    private static Toast toast;

    public static void showToast(String text) {
        if (toast == null) {
            toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }


    public static Resources getResources() {
        return getContext().getResources();
    }

    //从string.xml中获取字符串
    public static String getString(int stringId) {
        //上下文环境获取资源文件夹
        return getResources().getString(stringId);
    }

    /**
     * 通过资源文件id获取图片对象
     */
    public static Drawable getDrawable(int drawableID) {
        return ContextCompat.getDrawable(getContext(), drawableID);
    }

    /**
     * 通过资源文件id获取颜色
     */
    public static int getColor(int ColorID) {

        return ContextCompat.getColor(getContext(), ColorID);

    }

    //添加string类型数组的方法
    public static String[] getStringArray(int stringArrayId) {
        return getResources().getStringArray(stringArrayId);
    }


    public static ColorStateList getColorStateList(int mTabTextColorResId) {
        return ContextCompat.getColorStateList(getContext(), mTabTextColorResId);
    }

    public static View inflate(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }

    //
    public static int getDimenPx(int dimenId) {
        return getResources().getDimensionPixelSize(dimenId);
    }



    /**
     * 设置相关的系统设置
     *
     * @param a activity
     */

    public static void setSettingOperate(Activity a) {
        //透明状态栏
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			Window window = a.getWindow();
//			// Translucent status bar
//			window.setFlags(
//					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//		}
        //防止横竖屏转换
        a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    /**
     * 密码显示隐藏控制按钮设置tag与src
     *
     * @param tagertIv 目标imageview
     */
    public static void setDefaultImage(ImageView tagertIv) {
        tagertIv.setImageResource(R.drawable.eye_close);
        tagertIv.setTag(R.drawable.eye_close);
    }

    /**
     * 密码显示隐藏控制
     *
     * @param tagertEt 目标edittext
     * @param tagertIv 目标imageview
     */
    public static void setPwdView(EditText tagertEt, ImageView tagertIv) {
        if (tagertIv.getTag().equals(R.drawable.eye)) {
            //显示密码
            tagertIv.setImageResource(R.drawable.eye_close);
            tagertIv.setTag(R.drawable.eye_close);
            tagertEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            //隐藏密码
            tagertIv.setImageResource(R.drawable.eye);
            tagertIv.setTag(R.drawable.eye);
            tagertEt.setInputType(InputType.TYPE_CLASS_TEXT);

        }
        String str = tagertEt.getText().toString();
        tagertEt.setSelection(str.length());
    }


}
