package com.json.util;
import android.util.Log;
/**
 * Created by zhouguizhi on 2017/11/17.
 */
public class Logger {
    private static final String  TAG = Logger.class.getSimpleName();
    public static void e(String msg){
        Log.e(TAG,"msg="+msg);
    }
    public static void i(String msg){
        Log.i(TAG,"msg="+msg);
    }
    public static void d(String msg){
        Log.d(TAG,"msg="+msg);
    }
    public static void w(String msg){
        Log.w(TAG,"msg="+msg);
    }
}
