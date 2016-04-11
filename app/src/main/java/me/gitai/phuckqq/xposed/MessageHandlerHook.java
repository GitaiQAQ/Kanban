package me.gitai.phuckqq.xposed;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import android.annotation.TargetApi;
import android.app.AndroidAppHelper;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.UserHandle;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import me.gitai.phuckqq.BuildConfig;
import me.gitai.phuckqq.Constant;
import me.gitai.phuckqq.data.ServiceMsg;
import me.gitai.phuckqq.data.Message;

import me.gitai.library.utils.L;
import me.gitai.library.utils.SharedPreferencesUtil;
import me.gitai.library.utils.StringUtils;

import com.qq.jce.wup.UniPacket;
import com.qq.taf.jce.HexUtil;

import java.io.UnsupportedEncodingException;


import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by gitai on 16-2-29.
 */
public class MessageHandlerHook implements IXposedHookLoadPackage {

    private Class<?> QQAppInterface,SessionInfo,ChatActivityFacade;

    private void hook1(XC_LoadPackage.LoadPackageParam lpparam) throws ClassNotFoundException,NoSuchFieldException,IllegalAccessException{
        String className = "com.tencent.mobileqq.app.QQAppInterface";
        String methodName = "a";

        Class<?> ToServiceMsg = lpparam.classLoader.loadClass("com.tencent.qphone.base.remote.ToServiceMsg");

        L.d("Hooking a(ToServiceMsg toServiceMsg)");

        XposedHelpers.findAndHookMethod(className, lpparam.classLoader, methodName,
                ToServiceMsg,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        L.d("hook1->beforeHookedMethod");
                        ServiceMsg toServiceMsg = new ServiceMsg(param.args[0]);
                        L.d(toServiceMsg.toString().replace(",", "\n"));
                    }
                });
    }

    private void hook2(XC_LoadPackage.LoadPackageParam lpparam) throws ClassNotFoundException,NoSuchFieldException,IllegalAccessException{
        String className = "com.tencent.mobileqq.app.QQAppInterface";
        String methodName = "a";

        Class<?> FromServiceMsg = lpparam.classLoader.loadClass("com.tencent.qphone.base.remote.FromServiceMsg");
        Class<?> ToServiceMsg = lpparam.classLoader.loadClass("com.tencent.qphone.base.remote.ToServiceMsg");

        L.d("Hooking a(ToServiceMsg toServiceMsg, FromServiceMsg fromServiceMsg)");

        XposedHelpers.findAndHookMethod(className, lpparam.classLoader, methodName,
                ToServiceMsg, FromServiceMsg,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        //L.d("hook2->beforeHookedMethod");
                        ServiceMsg toServiceMsg = new ServiceMsg(param.args[0]);
                        ServiceMsg fromServiceMsg = new ServiceMsg("FromServiceMsg", param.args[1]);
                        //L.d(toServiceMsg.toString().replace(",", "\n") + "\n" + fromServiceMsg.toString().replace(",", "\n"));
                    }
                });
    }

    private List<Message> mMessages = new ArrayList();

    private void hook3(XC_LoadPackage.LoadPackageParam lpparam) throws ClassNotFoundException,NoSuchFieldException,IllegalAccessException{
        String className = "com.tencent.mobileqq.app.QQAppInterface";
        String methodName = "a";

        Class<?> QQMessageFacadeMessage = lpparam.classLoader.loadClass("com.tencent.mobileqq.app.message.QQMessageFacade$Message");

        L.d("Hooking a(QQMessageFacade$Message message, boolean needTicker)");

        XposedHelpers.findAndHookMethod(className, lpparam.classLoader, methodName,
                QQMessageFacadeMessage, boolean.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        L.d("hook3->beforeHookedMethod");
                        Message message = new Message(param.args[0]);
                        boolean needTicker = (boolean)param.args[1];
                        L.d(message.toString().replace(",", "\n") +"\nneedTicker:" + needTicker);

                        mMessages.add(message);

                        
                        //bigContentView.setInt(R.id.big_text, "setMaxLines", calculateMaxLines());
                    }
                });
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        Application app = AndroidAppHelper.currentApplication();

        L.setLogcatEnable(app, true);
        L.setLogToFileEnable(true, app, "/sdcard/phuckqq/logs");
        L.setXposedMode(true);

        //L.d(lpparam.packageName);
        if (lpparam.packageName.startsWith("com.tencent.qq")) {
            L.d("initializing...");

            L.i("Phone manufacturer: %s", Build.MANUFACTURER);
            L.i("Phone model: %s", Build.MODEL);
            L.i("Android version: %s", Build.VERSION.RELEASE);
            L.i("Xposed bridge version: %d", XposedBridge.XPOSED_BRIDGE_VERSION);
            L.i("Module version: %s (%d)", BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);

            try {
                hook3(lpparam);
            } catch (Throwable e) {
                L.d("Failed to hook1 QQ handler" + "\n" + Log.getStackTraceString(e));
                throw e;
            }
            L.d("initialization complete!");
        }
    }
}
