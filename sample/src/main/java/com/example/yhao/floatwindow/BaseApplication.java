package com.example.yhao.floatwindow;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yhao.fixedfloatwindow.R;
import com.yhao.floatwindow.FloatWindow;
import com.yhao.floatwindow.IFloatWindow;
import com.yhao.floatwindow.MoveType;
import com.yhao.floatwindow.PermissionListener;
import com.yhao.floatwindow.Screen;
import com.yhao.floatwindow.Util;
import com.yhao.floatwindow.ViewStateListener;

import java.text.DecimalFormat;

/**
 * Created by yhao on 2017/12/18.
 * https://github.com/yhaolpz
 */

public class BaseApplication extends Application {


    private static final String TAG = "FloatWindow";
    private long baseTimer = 0L;

    @Override
    public void onCreate() {
        super.onCreate();

        int screenWidth = Util.getScreenWidth(getApplicationContext());
        int screenHeight = Util.getScreenHeight(getApplicationContext());
        int width = (int) Util.dip2px(getApplicationContext(), 86);
        int height = (int) Util.dip2px(getApplicationContext(), 40);
        int padding = (int) Util.dip2px(getApplicationContext(), 8);
        View floatView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.float_window_layout, null);

        final TextView timeText = (TextView)floatView.findViewById(R.id.timeText);
        baseTimer = SystemClock.elapsedRealtime();
        Handler myhandler = new Handler(Looper.myLooper()) {
            public void handleMessage(android.os.Message msg) {
                if (0 == baseTimer) {
                    baseTimer = SystemClock.elapsedRealtime();
                }

                int time = (int) ((SystemClock.elapsedRealtime() - baseTimer) / 1000);
                String mm = new DecimalFormat("00").format(time / 60);
                String ss = new DecimalFormat("00").format(time % 60);
                if (null != timeText) {
                    timeText.setText(mm + ":" + ss);
                }
                Message message = Message.obtain();
                message.what = 0x0;
                sendMessageDelayed(message, 1000);
            }
        };
        myhandler.sendMessageDelayed(Message.obtain(myhandler, 1), 1000);


        FloatWindow.with(getApplicationContext())
                .setView(floatView)
                .setWidth(width)
                .setHeight(height)
                .setX(screenWidth - width - padding)
                .setY(screenHeight - height - padding)
                .setDesktopShow(false)//桌面是否显示
                .setFilter(false)//app中不显示的页面
                .setMoveType(MoveType.slide,padding,padding)
                .setMoveStyle(200, new LinearInterpolator())
                .setViewStateListener(mViewStateListener)
                .setPermissionListener(mPermissionListener)
                .setDesktopShow(true)
                .build();

        IFloatWindow iFloatWindow = FloatWindow.get();
        View stopBtn = floatView.findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatWindow.destroy();
            }
        });
    }

    private PermissionListener mPermissionListener = new PermissionListener() {
        @Override
        public void onSuccess() {
            Log.d(TAG, "onSuccess");
        }

        @Override
        public void onFail() {
            Log.d(TAG, "onFail");
        }
    };

    private ViewStateListener mViewStateListener = new ViewStateListener() {
        @Override
        public void onPositionUpdate(int x, int y) {
            Log.d(TAG, "onPositionUpdate: x=" + x + " y=" + y);
        }

        @Override
        public void onShow() {
            Log.d(TAG, "onShow");
        }

        @Override
        public void onHide() {
            Log.d(TAG, "onHide");
        }

        @Override
        public void onDismiss() {
            Log.d(TAG, "onDismiss");
        }

        @Override
        public void onMoveAnimStart() {
            Log.d(TAG, "onMoveAnimStart");
        }

        @Override
        public void onMoveAnimEnd() {
            Log.d(TAG, "onMoveAnimEnd");
        }

        @Override
        public void onBackToDesktop() {
            Log.d(TAG, "onBackToDesktop");
        }
    };
}
