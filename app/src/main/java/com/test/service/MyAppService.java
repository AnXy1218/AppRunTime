package com.test.service;

import android.app.Service;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.apprun.R;
import com.test.apprun.utils.ApiUtils;
import com.test.apprun.utils.SystemTool;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/12/20.
 */
public class MyAppService extends Service {
    private static final String TAG = "MyAppService";

    private MyAppBinder binder = new MyAppBinder();
    private Handler handler;

    private Timer timer;
    private WindowManager mWindowManager;
    private View view;

    private RelativeLayout rlContainer;
    private TextView tvLeft;

    private TextView tvCenter;

    private int time = 0;
    private int targetTime = 10;
    private UsageStatsManager usageStatsManager;

    private boolean runInBackground = true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"MyAppService----onBind");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"MyAppService----onCreate");
        handler = new Handler();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"MyAppService----onStartCommand");
        showMyInfo();
        return super.onStartCommand(intent, flags, startId);
    }

    public void showMyInfo(){
        Log.i(TAG,"MyAppService----showMyInfo");
        if (timer != null){
            return;
        }

        showWindow();
        tvLeft.setVisibility(View.GONE);
        tvCenter.setVisibility(View.GONE);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
//                Log.i(TAG,"Service在运行");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getApplicationContext(),"Service在运行",Toast.LENGTH_SHORT).show();
//                        dialog.show();

                        if (time > targetTime){
                            return;
                        }

                        Log.i(TAG,"MyAppService----showMyInfo-----timer1");

                        if (time >= targetTime){
                            tvLeft.setVisibility(View.GONE);
                            tvCenter.setVisibility(View.VISIBLE);
                            showCenterView("恭喜您，试玩" + targetTime + "秒");
                            time++;
                            timer.schedule(new TimerTask() {
                                @Override public void run() {
                                    endSevice();
                                }
                            },3000);
                            return;
                        }

                        boolean isRunForeground = SystemTool.isRunningForeground(MyAppService.this, ApiUtils.TARGET_PACKGAGE_NAME);

                        if (isRunForeground){
                            if (runInBackground){
                                runInBackground = false;
                                tvLeft.setVisibility(View.GONE);
                                tvCenter.setVisibility(View.VISIBLE);
                                if (time > 0){
                                    tvCenter.setText("暂停回来，上次玩了:" + time+"秒");
                                }else{
                                    tvCenter.setText("开始试玩，试玩时间：" + targetTime  + "秒");
                                }
                            }else{
                                tvLeft.setVisibility(View.VISIBLE);
                                tvCenter.setVisibility(View.GONE);
                                tvLeft.setText("已试玩：" + (time++) + "秒");
                            }
                        }else{
                            if (!runInBackground){
                                runInBackground = true;
                                showCenterView("试玩暂停，试玩" + time + "秒");
                            }
                        }
                    }
                });
            }
        },5*1000,1*1000);
    }

    private void showWindow(){
        if (view != null){
            return;
        }
        view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_window,null);
        tvLeft = view.findViewById(R.id.tvLeft);
        tvCenter = view.findViewById(R.id.tvCenter);
        rlContainer = view.findViewById(R.id.rlContainer);
        mWindowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        //设置type.系统提示型窗口，一般都在应用程序窗口之上.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else{
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        //设置效果为背景透明.
        params.format = PixelFormat.RGBA_8888;
        //设置flags.不可聚焦及不可使用按钮对悬浮窗进行操控.
//        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

//        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        //设置窗口初始停靠位置.
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = 0;
        params.y = 0;

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        tvCenter.setVisibility(View.GONE);

        mWindowManager.addView(view,params);
    }

    @Override
    public void onDestroy() {
//        Log.i(TAG,"MyAppService----onDestroy");
        super.onDestroy();
        if (timer != null){
            timer.cancel();
            timer = null;
        }
        if (mWindowManager != null){
            mWindowManager.removeViewImmediate(view);
        }
    }

    private void showCenterView(String message){
        if (timer == null){
            return;
        }
        tvLeft.setVisibility(View.GONE);
        tvCenter.setVisibility(View.VISIBLE);
        tvCenter.setText(message);
        Timer timer1 = new Timer();
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvCenter.setVisibility(View.GONE);
//                        if (removeView){
////                            mWindowManager.removeViewImmediate(view);
////                            timer.cancel();
////                            timer = null;
////                            MyAppService.this.onDestroy();
//                            endSevice();
//                        }else {
//                            tvCenter.setVisibility(View.GONE);
//                        }
                    }
                });
            }
        },3*1000);
    }

//    private void showCenterView(String message, final boolean removeView){
//        if (timer == null){
//            return;
//        }
//        tvLeft.setVisibility(View.GONE);
//        tvCenter.setVisibility(View.VISIBLE);
//        tvCenter.setText(message);
//        Timer timer1 = new Timer();
//        timer1.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        tvCenter.setVisibility(View.GONE);
////                        if (removeView){
//////                            mWindowManager.removeViewImmediate(view);
//////                            timer.cancel();
//////                            timer = null;
//////                            MyAppService.this.onDestroy();
////                            endSevice();
////                        }else {
////                            tvCenter.setVisibility(View.GONE);
////                        }
//                    }
//                });
//            }
//        },3*1000);
//    }

    /**
     * 手动调用解除绑定
     */
    private void endSevice(){
        Intent intent = new Intent();
        intent.setAction("com.test.MainAcitiviy.EndReceiver");
        sendBroadcast(intent);
    }

    public class MyAppBinder extends Binder{
        public MyAppService getService(){
            return MyAppService.this;
        }
    }
}
