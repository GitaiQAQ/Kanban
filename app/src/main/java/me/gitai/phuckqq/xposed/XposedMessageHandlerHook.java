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
import me.gitai.phuckqq.data.Message;
import me.gitai.phuckqq.data.SessionInfo;
import me.gitai.phuckqq.util.Config;

/**
 * Created by gitai on 16-2-29.
 */
public class XposedMessageHandlerHook implements IXposedHookLoadPackage {

    private HashMap<String, String> uins = new HashMap<>();

    private Object mRuntime = null;
    private HashMap<String, SessionInfo> sessionInfos = new HashMap<>();

    private Class<?> QQAppInterface,SessionInfo,ChatActivityFacade;

    private Context mContext;
    private Config mConfig;

    private void initConfig() {
        mConfig = new Config();
    }

    private static void printDeviceInfo() {
        /*XposedBridge.log("Phone manufacturer: " + Build.MANUFACTURER);
        XposedBridge.log("Phone model: " + Build.MODEL);
        XposedBridge.log("Android version: " + Build.VERSION.RELEASE);
        XposedBridge.log("Xposed bridge version: " + XposedBridge.XPOSED_BRIDGE_VERSION);*/
        XposedBridge.log("PhuckQQ version: " + BuildConfig.VERSION_NAME);
    }

    private void a1392(XC_LoadPackage.LoadPackageParam lpparam) throws ClassNotFoundException,NoSuchFieldException,IllegalAccessException{
        String className = "com.tencent.mobileqq.activity.ChatActivity";
        String methodName = "a";

        Class<?> SessionInfo = lpparam.classLoader.loadClass("com.tencent.mobileqq.activity.aio.SessionInfo");

        XposedBridge.log("Hooking a(SessionInfo sessionInfo, Intent intent)[1392]");

        XposedHelpers.findAndHookMethod(className, lpparam.classLoader, methodName,
                SessionInfo, Intent.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        SessionInfo sessionInfo = new SessionInfo(param.args[0]);
                        //sessionInfos.put(uin, sessionInfos);
                        XposedBridge.log(sessionInfo.toString());
                    }
                });
    }

    private void a1405(final XC_LoadPackage.LoadPackageParam lpparam) throws ClassNotFoundException,NoSuchFieldException,IllegalAccessException{
        String className = "com.tencent.mobileqq.activity.ChatActivity";
        String methodName = "a";

        Class<?> message = lpparam.classLoader.loadClass("com.tencent.mobileqq.app.message.QQMessageFacade$Message");

        XposedBridge.log("Hooking a(QQMessageFacade$Message qQMessageFacade$Message)[1405]");

        XposedHelpers.findAndHookMethod(className, lpparam.classLoader, methodName,
                message,
                new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Message message = new Message(param.args[0]);

                XposedBridge.log(message.toString());

                if (mContext == null) {
                    mContext = (Context)param.thisObject;
                }

                if(QQAppInterface == null){
                    QQAppInterface = lpparam.classLoader.loadClass("com.tencent.mobileqq.app.QQAppInterface");
                }

                if(SessionInfo == null){
                    SessionInfo = lpparam.classLoader.loadClass("com.tencent.mobileqq.activity.aio.SessionInfo");
                }

                if(mRuntime == null){
                    mRuntime = XposedHelpers.getObjectField(mContext, "mRuntime");
                }

                SessionInfo sessionInfo = null;
                if (!sessionInfos.containsKey(message.getSenderuin())){
                    Field[] fields = mContext.getClass().getDeclaredFields();
                    for (int i = 0; i < fields.length; i++) {
                        if (fields[i].getType().equals(SessionInfo)){
                            sessionInfo = new SessionInfo(fields[i].get(mContext));
                            sessionInfos.put(message.getSenderuin(), sessionInfo);
                        }
                    }
                }else{
                    sessionInfo = sessionInfos.get(message.getSenderuin());
                }

                if(ChatActivityFacade == null){
                    ChatActivityFacade = lpparam.classLoader.loadClass("com.tencent.mobileqq.activity.ChatActivityFacade");
                }

                sendBySessionInfo(sessionInfo, message.toString().replace(",", "\n"));
            }
        });
    }

    private void e2474(XC_LoadPackage.LoadPackageParam lpparam) {
        String className = "com.tencent.mobileqq.activity.ChatActivity";
        String methodName = "e";

        XposedBridge.log("Hooking e(Intent intent)[2474]");

        XposedHelpers.findAndHookMethod(className, lpparam.classLoader, methodName,
                Intent.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Intent intent = (Intent)param.args[0];
                        String uin = intent.getStringExtra("uin");
                        int uintype = intent.getIntExtra("uintype", -1);
                        String phonenum = intent.getStringExtra("phonenum");
                        int entrance = intent.getIntExtra("entrance", 0);
                        String troop_uin = intent.getStringExtra("troop_uin");
                        XposedBridge.log("Intent " +intent.toString());
                        sendByUid(uin, "uin: " + uin + ", uintype: " + uintype + ", phonenum: " + phonenum + ", entrance: " + entrance
                                + ", troop_uin: " + troop_uin);
                    }
                });
    }

    private void a3625(XC_LoadPackage.LoadPackageParam lpparam) {
        String className = "com.tencent.mobileqq.app.MessageHandler";
        String methodName = "a";

        XposedBridge.log("Hooking a(String string)[3625]");

        XposedHelpers.findAndHookMethod(className, lpparam.classLoader, methodName,
                String.class,
                new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String type = (String)param.args[0];
                XposedBridge.log("MsgType:" + type);
            }
        });
    }

    private void sendByUid(String uin, String message){
        if (sessionInfos.containsKey(uin))
            sendBySessionInfo(sessionInfos.get(uin), message);
    }

    private void sendBySessionInfo(SessionInfo sessionInfo, String message){
        if (!mConfig.getBoolean(Constant.KEY_SEND_ENABLE, false)) return;
        if (!((String)mConfig.getConfig(Constant.KEY_RES_LIST, "")).contains(sessionInfo.getUin())) return;
        XposedHelpers.callStaticMethod(ChatActivityFacade, "a",
                new Class<?>[]{QQAppInterface, Context.class, SessionInfo, String.class, ArrayList.class},
                mRuntime, AndroidAppHelper.currentApplication(), sessionInfo.getObject(), message, null);
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        initConfig();
        if ("com.tencent.qq.kddi".equals(lpparam.packageName) && mConfig.getBoolean(Constant.KEY_ENABLE, false)) {
            XposedBridge.log("PhuckQQ initializing...");
            printDeviceInfo();
            try {
                a1392(lpparam);
            } catch (Throwable e) {
                XposedBridge.log("Failed to hook QQ handler" + "\n" + Log.getStackTraceString(e));
                throw e;
            }
            try {
                a1405(lpparam);
            } catch (Throwable e) {
                XposedBridge.log("Failed to hook QQ handler" + "\n" + Log.getStackTraceString(e));
                throw e;
            }
            try {
                e2474(lpparam);
            } catch (Throwable e) {
                XposedBridge.log("Failed to hook QQ handler" + "\n" + Log.getStackTraceString(e));
                throw e;
            }
            try {
                a3625(lpparam);
            } catch (Throwable e) {
                XposedBridge.log("Failed to hook QQ handler" + "\n" + Log.getStackTraceString(e));
                throw e;
            }
            XposedBridge.log("PhuckQQ initialization complete!");
        }
    }
}
