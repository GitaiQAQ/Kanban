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

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import me.gitai.phuckqq.BuildConfig;
import me.gitai.phuckqq.Constant;
import me.gitai.phuckqq.data.ServiceMsg;

/**
 * Created by gitai on 16-2-29.
 */
public class MessageHandlerHook implements IXposedHookLoadPackage {

    private Class<?> QQAppInterface,SessionInfo,ChatActivityFacade;

    private void hook1(XC_LoadPackage.LoadPackageParam lpparam) throws ClassNotFoundException,NoSuchFieldException,IllegalAccessException{
        String className = "com.tencent.mobileqq.app.QQAppInterface";
        String methodName = "a";

        Class<?> ToServiceMsg = lpparam.classLoader.loadClass("com.tencent.qphone.base.remote.ToServiceMsg");

        XposedBridge.log("Hooking a(SessionInfo sessionInfo, Intent intent)[1392]");

        XposedHelpers.findAndHookMethod(className, lpparam.classLoader, methodName,
                ToServiceMsg,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        ServiceMsg toServiceMsg = new ServiceMsg(param.args[0]);
                        XposedBridge.log(toServiceMsg.toString().replace(",", "\n"));
                    }
                });
    }

    private void hook2(XC_LoadPackage.LoadPackageParam lpparam) throws ClassNotFoundException,NoSuchFieldException,IllegalAccessException{
        String className = "com.tencent.mobileqq.app.QQAppInterface";
        String methodName = "a";

        Class<?> FromServiceMsg = lpparam.classLoader.loadClass("com.tencent.qphone.base.remote.FromServiceMsg");
        Class<?> ToServiceMsg = lpparam.classLoader.loadClass("com.tencent.qphone.base.remote.ToServiceMsg");

        XposedBridge.log("Hooking a(SessionInfo sessionInfo, Intent intent)[1392]");

        XposedHelpers.findAndHookMethod(className, lpparam.classLoader, methodName,
                ToServiceMsg, FromServiceMsg,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        ServiceMsg toServiceMsg = new ServiceMsg(param.args[0]);
                        ServiceMsg fromServiceMsg = new ServiceMsg("FromServiceMsg", param.args[1]);
                        XposedBridge.log(toServiceMsg.toString().replace(",", "\n") + "\n" + fromServiceMsg.toString().replace(",", "\n"));
                    }
                });
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        //initConfig();
        //&& mConfig.getBoolean(Constant.KEY_ENABLE, false)
        if ("com.tencent.qq.kddi".equals(lpparam.packageName)) {
            XposedBridge.log("PhuckQQ initializing...");
            //printDeviceInfo();
            try {
                hook1(lpparam);
            } catch (Throwable e) {
                XposedBridge.log("Failed to hook1 QQ handler" + "\n" + Log.getStackTraceString(e));
                throw e;
            }
            try {
                hook2(lpparam);
            } catch (Throwable e) {
                XposedBridge.log("Failed to hook1 QQ handler" + "\n" + Log.getStackTraceString(e));
                throw e;
            }
            XposedBridge.log("PhuckQQ initialization complete!");
        }
    }
}
