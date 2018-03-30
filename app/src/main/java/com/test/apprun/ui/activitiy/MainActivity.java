package com.test.apprun.ui.activitiy;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.test.apprun.utils.ApiUtils;
import com.test.apprun.utils.SystemTool;
import com.test.apprun.R;
import com.test.service.MyAppService;

public class MainActivity extends Activity {
    /**
     * 5.0以后获取app运行时间的权限
     */
    private static final int REQUESTCODE_USAGE = 1232;
    /**
     * 获取是否让应用在顶层显示权限
     */
    private static final int REQUESTCODE_OVER = 1234;

    private MyAppService appService;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            appService = ((MyAppService.MyAppBinder)iBinder).getService();
            appService.showMyInfo();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            appService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter("com.test.MainAcitiviy.EndReceiver");
        EndBindReceiver receiver = new EndBindReceiver();
        registerReceiver(receiver,filter);

        findViewById(R.id.tvJump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startService(intent1);
                startService();
            }
        });

        findViewById(R.id.tvIcon).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,IconActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUESTCODE_OVER){
//            startService();
            if (Build.VERSION.SDK_INT >= 23){
                if (!Settings.canDrawOverlays(MainActivity.this)){
                    Toast.makeText(MainActivity.this,"未获取到悬浮窗权限",Toast.LENGTH_SHORT).show();
                }else {
                    startService();
                }
            }
        }
    }

    private void startService(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            if (!SystemTool.getUsageStatsList(this) && SystemTool.isNoOption(this)){
                //5.0及以后
                Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                Toast.makeText(MainActivity.this,"需要取的查看手机app运行时间的权限",Toast.LENGTH_SHORT).show();
                startActivityForResult(intent,REQUESTCODE_USAGE);
                return;
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (!Settings.canDrawOverlays(MainActivity.this)){
                //已获取悬浮窗
                //若没有权限，提示获取.
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                Toast.makeText(MainActivity.this,"需要取得权限以使用悬浮窗",Toast.LENGTH_SHORT).show();
                startActivityForResult(intent, REQUESTCODE_OVER);
                return;
            }
        }

        Intent intent1 = new Intent(MainActivity.this,MyAppService.class);
        bindService(intent1,conn,BIND_AUTO_CREATE);

        Intent intent = MainActivity.this.getPackageManager().getLaunchIntentForPackage(ApiUtils.TARGET_PACKGAGE_NAME);
        MainActivity.this.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (appService != null){
            unbindService(conn);
        }
    }

    public class EndBindReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (appService != null){
                unbindService(conn);
            }
        }
    }
}
