package com.xwjr.staple.util;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * <pre>

 *     desc  : Utils初始化相关
 * </pre>
 */
public class StapleUtils {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private StapleUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        StapleUtils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }

}