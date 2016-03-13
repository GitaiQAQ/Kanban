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

/**
 * Created by gitai on 16-3-12.
 */
public class QLogHook implements IXposedHookLoadPackage {
	private String className = "com.tencent.qphone.base.util.QLog";

	private void hookisColorLevel(XC_LoadPackage.LoadPackageParam lpparam){
		String methodName = "isColorLevel";

		XposedBridge.log("Hooking isColorLevel()");
		XposedHelpers.findAndHookMethod(className, lpparam.classLoader, methodName,
                String.class, Integer.class, String.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(true);
                    }
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(true);
                    }
                });
	}

	private void hookLogE(XC_LoadPackage.LoadPackageParam lpparam){
		String methodName = "e";

		XposedBridge.log("Hooking e(String tag, int i, String msg)");
		XposedHelpers.findAndHookMethod(className, lpparam.classLoader, methodName,
                String.class, Integer.class, String.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        String tag = (String)param.args[0];
                        int i1 = (int)param.args[1];
                        String msg = (String)param.args[2];
                        XposedBridge.log("Qlog.e tag=" + tag + ",int=" + i1 + ",msg=" + msg);
                    }
                });
	}

	private void hookLogD(XC_LoadPackage.LoadPackageParam lpparam){
		String methodName = "d";

		XposedBridge.log("Hooking d(String tag, int i, String msg)");
		XposedHelpers.findAndHookMethod(className, lpparam.classLoader, methodName,
                String.class, Integer.class, String.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        String tag = (String)param.args[0];
                        int i1 = (int)param.args[1];
                        String msg = (String)param.args[2];
                        XposedBridge.log("Qlog.d tag=" + tag + ",int=" + i1 + ",msg=" + msg);
                    }
                });
	}

	private void hookLogI(XC_LoadPackage.LoadPackageParam lpparam){
		String methodName = "i";

		XposedBridge.log("Hooking i(String tag, int i, String msg)");
		XposedHelpers.findAndHookMethod(className, lpparam.classLoader, methodName,
                String.class, Integer.class, String.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        String tag = (String)param.args[0];
                        int i1 = (int)param.args[1];
                        String msg = (String)param.args[2];
                        XposedBridge.log("Qlog.i tag=" + tag + ",int=" + i1 + ",msg=" + msg);
                    }
                });
	}

	private void hookLogW(XC_LoadPackage.LoadPackageParam lpparam){
		String methodName = "w";

		XposedBridge.log("Hooking w(String tag, int i, String msg)");
		XposedHelpers.findAndHookMethod(className, lpparam.classLoader, methodName,
                String.class, Integer.class, String.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        String tag = (String)param.args[0];
                        int i1 = (int)param.args[1];
                        String msg = (String)param.args[2];
                        XposedBridge.log("Qlog.w tag=" + tag + ",int=" + i1 + ",msg=" + msg);
                    }
                });
	}

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if ("com.tencent.qq.kddi".equals(lpparam.packageName)) {
            XposedBridge.log("QQLog initializing...");
            try {
                hookisColorLevel(lpparam);
            } catch (Throwable e) {
                XposedBridge.log("Failed to hook QQLog.isColorLevel handler" + "\n" + Log.getStackTraceString(e));
                throw e;
            }
            try {
                hookLogE(lpparam);
            } catch (Throwable e) {
                XposedBridge.log("Failed to hook QQLog.e handler" + "\n" + Log.getStackTraceString(e));
                throw e;
            }
            try {
                hookLogD(lpparam);
            } catch (Throwable e) {
                XposedBridge.log("Failed to hook QQLog.d handler" + "\n" + Log.getStackTraceString(e));
                throw e;
            }
            try {
                hookLogI(lpparam);
            } catch (Throwable e) {
                XposedBridge.log("Failed to hook QQLog.i handler" + "\n" + Log.getStackTraceString(e));
                throw e;
            }
            try {
                hookLogW(lpparam);
            } catch (Throwable e) {
                XposedBridge.log("Failed to hook QQLog.w handler" + "\n" + Log.getStackTraceString(e));
                throw e;
            }
            XposedBridge.log("QQLog initialization complete!");
        }
    }
}
