package com.kotlin.base.utils;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class AppUtils {

    private AppUtils() {
        throw new AssertionError();
    }


    /**
     * whether application is in background
     * <ul>
     * <li>need use permission android.permission.GET_TASKS in Manifest.xml</li>
     * </ul>
     *
     * @param context
     * @return if application is in background return true, otherwise return false
     */
    public static boolean isApplicationInBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService( Context.ACTIVITY_SERVICE );
        List<RunningTaskInfo> taskList = am.getRunningTasks( 1 );
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName topActivity = taskList.get( 0 ).topActivity;
            if (topActivity != null && !topActivity.getPackageName().equals( context.getPackageName() )) {
                return true;
            }
        }
        return false;
    }

    public static boolean isFroyoOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static boolean isGingerbreadOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean isHoneycombOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean isICSOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public static boolean isJellyBeanOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    @TargetApi(17)
    public static boolean isJellyBeanMR1OrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    @TargetApi(18)
    public static boolean isJellyBeanMR2OrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    @TargetApi(19)
    public static boolean isKitkatOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean isWiFiActive(Context context) {
        WifiManager wm = null;
        try {
            wm = (WifiManager) context.getSystemService( Context.WIFI_SERVICE );
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (wm == null || wm.isWifiEnabled() == false)
            return false;

        return true;
    }

    /**
     * 安装apk
     *
     * @param fileName apk文件的绝对路径
     * @param context
     */
    public static void installAPK(String fileName, Context context) {
        Intent intent = new Intent( Intent.ACTION_VIEW );
        intent.setDataAndType( Uri.fromFile( new File( fileName ) ), "application/vnd.android.package-archive" );
        context.startActivity( intent );
    }

    /**
     * 判断某个应用当前是否正在运行
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppRunning(Context context, String packageName) {
        if (packageName == null)
            return false;

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService( Context.ACTIVITY_SERVICE );
        List<RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals( packageName )) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取手机网络类型名称
     *
     * @param networkType
     * @param mnc         Mobile NetworkCode，移动网络码，共2位
     * @return
     */
    public static String getNetWorkName(int networkType, String mnc) {
        if (networkType == TelephonyManager.NETWORK_TYPE_UNKNOWN) {
            return "Network type is unknown";
        } else if (networkType == TelephonyManager.NETWORK_TYPE_CDMA) {
            return "电信2G";
        } else if (networkType == TelephonyManager.NETWORK_TYPE_EVDO_0) {
            return "电信3G";
        } else if (networkType == TelephonyManager.NETWORK_TYPE_GPRS || networkType == TelephonyManager.NETWORK_TYPE_EDGE) {
            if ("00".equals( mnc ) || "02".equals( mnc )) {
                return "移动2G";
            } else if ("01".equals( mnc )) {
                return "联通2G";
            }
        } else if (networkType == TelephonyManager.NETWORK_TYPE_UMTS || networkType == TelephonyManager.NETWORK_TYPE_HSDPA) {
            return "联通3G";
        }
        return null;
    }

    /**
     * 检测网络状态
     *
     * @param context
     * @return
     */
    public static boolean checkNetworkStatus(Context context) {
        boolean resp = false;
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetInfo = connMgr.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.isAvailable()) {
            resp = true;
        }
        return resp;
    }

    /**
     * 检测gps状态
     *
     * @param context
     * @return
     */
    public static boolean checkGPSStatus(Context context) {
        boolean resp = false;
        LocationManager lm = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );
        if (lm.isProviderEnabled( LocationManager.GPS_PROVIDER )) {
            resp = true;
        }
        return resp;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从  px(像素) 转成为dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (0.5F + pxValue / scale);
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px(像素)
     *
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从  px(像素) 转成为sp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static float sp2px(Resources resources, float sp) {
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    /**
     * 判断谷歌地图是否可用,某些国行的手机不支持谷歌地图的服务
     *
     * @return
     */
    public static boolean googleMapAvailable() {
        boolean available = false;
        try {
            Class.forName( "com.google.android.maps.MapActivity" );
            available = true;
        } catch (Exception e) {
        }
        return available;
    }

    /**
     * 从Assets中读取文件
     *
     * @param context
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public static InputStream getFromAssets(Context context, String fileName)
            throws FileNotFoundException {
        InputStream inputStream = null;
        try {
            inputStream = context.getResources().getAssets().open( fileName );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    /**
     * 对文件设置root权限
     *
     * @param filePath
     * @return
     */
    public static boolean upgradeRootPermission(String filePath) {
        Process process = null;
        DataOutputStream os = null;
        try {
            String cmd = "chmod 777 " + filePath;
            process = Runtime.getRuntime().exec( "su" ); //切换到root帐号
            os = new DataOutputStream( process.getOutputStream() );
            os.writeBytes( cmd + "\n" );
            os.writeBytes( "exit\n" );
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
        return true;
    }

    /**
     * 判断当前线程是否为ui线程
     *
     * @return
     */
    public static boolean isUIThread() {
        long uiId = Looper.getMainLooper().getThread().getId();
        long cId = Thread.currentThread().getId();
        return uiId == cId;
    }

    /**
     * 关闭虚拟键盘
     *
     * @param context
     * @param view
     */
    public static void hideSoftInputFromWindow(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService( Context.INPUT_METHOD_SERVICE );
        imm.hideSoftInputFromWindow( view.getWindowToken(), 0 );
    }

    /**
     * 打卡软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService( Context.INPUT_METHOD_SERVICE );
        imm.showSoftInput( mEditText, InputMethodManager.RESULT_SHOWN );
        imm.toggleSoftInput( InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY );
    }

    /**
     * 关闭虚拟键盘
     *
     * @param context
     * @param views
     */
    public static void hideSoftInputFromWindow(Context context, View... views) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService( Context.INPUT_METHOD_SERVICE );
        if (views != null && views.length > 0) {
            for (View view : views) {
                imm.hideSoftInputFromWindow( view.getWindowToken(), 0 );
            }
        }
    }

    /**
     * 获取AndroidManifest.xml中<meta-data>元素的值
     *
     * @param context
     * @param name
     * @return
     */
    public static <T> T getMetaData(Context context, String name) {
        try {
            final ApplicationInfo ai = context.getPackageManager().getApplicationInfo( context.getPackageName(),
                    PackageManager.GET_META_DATA );

            if (ai.metaData != null) {
                return (T) ai.metaData.get( name );
            }
        } catch (Exception e) {
            System.out.print( "Couldn't find meta-data: " + name );
        }

        return null;
    }

    /**
     * 判断经纬度是否在中国
     *
     * @param mLocation
     * @return
     */
    public static boolean positionInChina(Location mLocation) {
        if (mLocation.getLatitude() > 18.167 && mLocation.getLatitude() < 53.55) {
            if (mLocation.getLongitude() > 73.667 && mLocation.getLongitude() < 135.033) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断经纬度是否在中国
     *
     * @return
     */
    public static boolean positionInChina(double latitude, double longitude) {
        if (latitude > 18.167 && latitude < 53.55) {
            if (longitude > 73.667 && longitude < 135.033) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否存在sd卡
     *
     * @return
     */
    public static boolean hasSdcard() {
        String status = Environment.getExternalStorageState();
        return status.equals( Environment.MEDIA_MOUNTED ) ? true : false;
    }

    /**
     * 获取手机可用的内存空间
     * 返回单位 G  如  “1.5GB”
     *
     * @return
     */
    public static String getAvailableSDRomString(Context context) {
        if (!hasSdcard())
            return "0";

        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs( path.getPath() );
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize( context, availableBlocks
                * blockSize );
    }

    /**
     * 判断某个APK是否安装过
     *
     * @param context
     * @param packageName 安装的包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo( packageName, PackageManager.GET_ACTIVITIES );
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }

    /**
     * 单位转换
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics() );
    }

    /**
     * 判断服务是否运行
     *
     * @param mContext
     * @param serviceName
     * @return true为运行，false为不在运行
     */
    public boolean isServiceRunning(Context mContext, String serviceName) {
        ActivityManager myManager = (ActivityManager) mContext
                .getSystemService( Context.ACTIVITY_SERVICE );
        ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager
                .getRunningServices( 30 );
        for (int i = 0; i < runningService.size(); i++) {
            String serName = runningService.get( i ).service.getClassName().toString();
            if (serName.equals( serviceName )) {
                return true;
            }
        }
        return false;
    }


    /**
     * 可用来做App信息分享
     */
    public static void shareAppInfo(Context context, String info) {
        if (!TextUtils.isEmpty( info )) {
            Intent intent = new Intent( Intent.ACTION_SEND );
            intent.setType( "text/plain" );
            intent.putExtra( Intent.EXTRA_TEXT, info );
            context.startActivity( intent );
        }
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo( context.getPackageName(), 0 );
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e( "VersionInfo", "Exception", e );
        }
        return versionName;
    }

    public static void jumpToLogin(Context context) {
        clearDb( context );
        clearData();
//        Intent intent = new Intent( context, LoginActivity.class );
//        intent.setFlags( FLAG_ACTIVITY_CLEAR_TASK );
//        context.startActivity( intent );
    }

    private static void clearDb(Context context) {
        //清除云仓的数据库数据
//        CloudSearchDao cloudSearchDao = new CloudSearchDao( context );
//        cloudSearchDao.deleteData( CloudSearchSQLiteOpenHelper.TABLE_NAME_CLOUD );
//        cloudSearchDao.deleteData( CloudSearchSQLiteOpenHelper.TABLE_NAME_STOCKCODE );
//        cloudSearchDao.deleteData( CloudSearchSQLiteOpenHelper.TABLE_NAME_WAREHOUSE );
//        //清除核销的数据库数据
//        WriteOffSearchDao writeOffDao = new WriteOffSearchDao( context );
//        writeOffDao.deleteData( WriteOffListSearchSQLiteOpenHelper.TABLE_WRITE_OFF_LIST );
//        writeOffDao.deleteData( WriteOffListSearchSQLiteOpenHelper.TABLE_GTIN_CODE );
//        //清除入库列表的数据库数据
//        StorageSearchDao storageSearchDao=new StorageSearchDao(context);
//        storageSearchDao.deleteData(StorageSearchSQLiteOpenHelper.TABLE_NAME_STORAGE);
    }

    private static void clearData() {
//        String accountNumber = Preference.getInstance().getAccountNumber();
//        Preference.getInstance().clean();
//        Preference.getInstance().setAccountNumber( accountNumber );
    }



    /**
     *
     * @param pw     popupWindow
     * @param anchor v
     * @param xoff   x轴偏移
     * @param yoff   y轴偏移
     */
    public static void showAsDropDown(final PopupWindow pw, final View anchor, final int xoff, final int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            pw.setHeight(height);
            pw.showAsDropDown(anchor, xoff, yoff);
        } else {
            pw.showAsDropDown(anchor, xoff, yoff);
        }
    }



}
