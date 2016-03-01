package me.gitai.fuckqq.xposed;


import android.annotation.TargetApi;
import android.app.AndroidAppHelper;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import me.gitai.fuckqq.BuildConfig;

/**
 * Created by gitai on 16-2-29.
 */
public class XposedMessageHandlerHook implements IXposedHookLoadPackage {

    private static void printDeviceInfo() {
        XposedBridge.log("Phone manufacturer: " + Build.MANUFACTURER);
        XposedBridge.log("Phone model: " + Build.MODEL);
        XposedBridge.log("Android version: " + Build.VERSION.RELEASE);
        XposedBridge.log("Xposed bridge version: " + XposedBridge.XPOSED_BRIDGE_VERSION);
        XposedBridge.log("FuckQQ version: " + BuildConfig.VERSION_NAME);
    }

    /*private void a536(XC_LoadPackage.LoadPackageParam lpparam) {
        String className = "com.tencent.mobileqq.app.MessageHandler";
        String methodName = "a";

        XposedBridge.log("Hooking a(String string, int n)[536]");

        XposedHelpers.findAndHookMethod(className, lpparam.classLoader, methodName,
                String.class, int.class,
                new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String uin = (String)param.args[0];
                int type = (int)param.args[1];
                XposedBridge.log("guessPullMsgStartSeq uin = " +uin + " ,type = " + type);
            }
        });
    }

    private void a613(XC_LoadPackage.LoadPackageParam lpparam) {
        String className = "com.tencent.mobileqq.app.MessageHandler";
        String methodName = "a";

        XposedBridge.log("Hooking a(String string, int n, long l, long l2, long l3, long l4)[536]");

        XposedHelpers.findAndHookMethod(className, lpparam.classLoader, methodName,
                String.class, int.class,long.class , long.class, long.class, long.class,
                new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String uin = (String)param.args[0];
                int type = (int)param.args[1];
                long lastSeq = (long)param.args[2];
                long expiredSeq = (long)param.args[2];
                long delSeq = (long)param.args[2];
                long svrSeq = (long)param.args[2];
                XposedBridge.log("getPullMsgLowSeq uin = " +uin + " ,type = " + type
                     + " ,lastSeq = " + lastSeq + " ,expiredSeq = " + expiredSeq + " ,delSeq = " + delSeq + " ,svrSeq = " + svrSeq);
            }
        });
    }

    private void a690(XC_LoadPackage.LoadPackageParam lpparam) throws ClassNotFoundException{
        String className = "com.tencent.mobileqq.app.MessageHandler";
        String methodName = "a";
        Class<?> fromServiceMsg = lpparam.classLoader.loadClass("com.tencent.qphone.base.remote.FromServiceMsg");
        //Class<?> toServiceMsg = lpparam.ClassLoader.loadClass("com.tencent.qphone.base.remote.ToServiceMsg");

        XposedBridge.log("Hooking a(FromServiceMsg fromServiceMsg)[690]");

        XposedHelpers.findAndHookMethod(className, lpparam.classLoader, methodName,
                fromServiceMsg,
                new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("fromServiceMsg");
            }
        });
    }

    private void a1295(XC_LoadPackage.LoadPackageParam lpparam) {
        String className = "com.tencent.mobileqq.app.MessageHandler";
        String methodName = "a";

        XposedBridge.log("Hooking a(String string, String string2, byte by, boolean bl, String string3, long l, long l2, long l3)[1295]");

        XposedHelpers.findAndHookMethod(className, lpparam.classLoader, methodName,
            String.class, String.class, byte.class, boolean.class, String.class, long.class, long.class, long.class,
            new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                long startTime = System.currentTimeMillis();
                long to = Long.valueOf((String)param.args[0]).longValue();
                String msg = (String)param.args[1];
                byte cType = (byte)param.args[2];
                boolean hello = (boolean)param.args[3];
                String pyNickname = (String)param.args[4];
                long uniseq = (long)param.args[5];
                long msgSeq = (long)param.args[6];
                long timeOut = (long)param.args[7];
                XposedBridge.log("AccostSvc.ClientMsg startTime = " +startTime + " ,to = " + to
                         + " ,msg = " + msg + " ,cType = " + cType + " ,hello = " + hello + " ,pyNickname = " + pyNickname
                         + " ,uniseq = " + uniseq + " ,msgSeq = " + msgSeq + " ,timeOut = " + timeOut);
            }
        });
    }*/

    private void hookMessageHandler(XC_LoadPackage.LoadPackageParam lpparam) throws ClassNotFoundException{
        String className = "com.tencent.mobileqq.app.MessageHandler";
        String methodName = "a";
        Class<?> fromServiceMsg = lpparam.classLoader.loadClass("com.tencent.qphone.base.remote.FromServiceMsg");
        Class<?> toServiceMsg = lpparam.classLoader.loadClass("com.tencent.qphone.base.remote.ToServiceMsg");

        XposedBridge.log("Hooking a(FromServiceMsg fromServiceMsg)[4174]");

        XposedHelpers.findAndHookMethod(className, lpparam.classLoader, methodName,
                toServiceMsg, fromServiceMsg,
                new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("toServiceMsg/fromServiceMsg");
            }
        });
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if ("com.tencent.qq.kddi".equals(lpparam.packageName)) {
            XposedBridge.log("FuckQQ initializing...");
            printDeviceInfo();
            try {
                hookMessageHandler(lpparam);
            } catch (Throwable e) {
                XposedBridge.log("Failed to hook QQ handler" + "\n" + Log.getStackTraceString(e));
                throw e;
            }
            XposedBridge.log("FuckQQ initialization complete!");
        }
    }
}
