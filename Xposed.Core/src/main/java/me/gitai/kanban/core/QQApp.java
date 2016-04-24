package me.gitai.kanban.core;

import android.app.Application;

import me.gitai.kanban.Constant;
import me.gitai.library.utils.CrashUtil;
import me.gitai.library.utils.L;
import me.gitai.library.utils.SharedPreferencesUtil;
import me.gitai.library.utils.ToastUtil;

/**
 * Created by gitai on 16-04-12.
 */
public class QQApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        L.setLogcatEnable(this, true);
        L.setLogToFileEnable(true, this, Constant.PATH_DATA_LOG);
        SharedPreferencesUtil.initialize(this);
        ToastUtil.initialize(this);
        CrashUtil.initialize(this);
        CrashUtil.setToFileEnable(true, Constant.PATH_DATA_CRASH);
    }
}
