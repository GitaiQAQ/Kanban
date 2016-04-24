package me.gitai.kanban.danmu;

import android.app.Application;

import me.gitai.kanban.Constant;
import me.gitai.library.utils.CrashUtil;
import me.gitai.library.utils.L;
import me.gitai.library.utils.SharedPreferencesUtil;
import me.gitai.library.utils.ToastUtil;


import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.WindowManager;
/**
 * Created by gitai on 16-04-12.
 */
public class QQApp extends Application{
    private WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
    private DanmuService danmuService;

    @Override
    public void onCreate() {
        super.onCreate();
        L.setLogcatEnable(this, true);
        L.setLogToFileEnable(true, this, Constant.PATH_DATA_LOG);
        SharedPreferencesUtil.initialize(this);
        ToastUtil.initialize(this);
        CrashUtil.initialize(this);
        CrashUtil.setToFileEnable(true, Constant.PATH_DATA_CRASH);

        if (SharedPreferencesUtil.getInstence(null).getBoolean("general_enable", false)){
            Intent intent = new Intent(this, DanmuService.class);
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onTerminate() {
        // TODO Auto-generated method stub
        super.onTerminate();
        unbindService(serviceConnection);
    }

    public WindowManager.LayoutParams getWindowManagerLayoutParams() {
        return layoutParams;
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            danmuService = ((DanmuService.DanmuBinder)service).getService();
        }
    };
}
