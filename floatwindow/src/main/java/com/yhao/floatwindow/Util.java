package com.yhao.floatwindow;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;


/**
 * Created by yhao on 2017/12/22.
 * https://github.com/yhaolpz
 */

public class Util {


    public static View inflate(Context applicationContext, int layoutId) {
        LayoutInflater inflate = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflate.inflate(layoutId, null);
    }

    private static Point sPoint;

    public static int getScreenWidth(Context context) {
        if (sPoint == null) {
            sPoint = new Point();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getSize(sPoint);
        }
        return sPoint.x;
    }

    public static int getScreenHeight(Context context) {
        if (sPoint == null) {
            sPoint = new Point();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getSize(sPoint);
        }
        return sPoint.y;
    }

    public static boolean isViewVisible(View view) {
        return view.getGlobalVisibleRect(new Rect());
    }

    /**
     * 根据从 dp 的单位转成为 px(像素) 手机的分辨率
     *
     * @param context 上下文
     * @param dpValue dp数据
     * @return
     */
    public static float dip2px(Context context, float dpValue) {
        return (dpValue * getDensity(context));
    }

    private static float density = -1;

    public static float getDensity(Context context) {
        if (density < 0) {
            density = context.getResources().getDisplayMetrics().density;
        }
        return density;
    }
}
