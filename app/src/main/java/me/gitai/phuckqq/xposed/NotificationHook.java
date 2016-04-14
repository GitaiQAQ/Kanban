package me.gitai.phuckqq.xposed;

import android.app.Notification;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;

import me.gitai.library.utils.L;

import android.app.AndroidAppHelper;
import android.app.Application;
import android.util.Log;

import android.os.Build;

import me.gitai.phuckqq.BuildConfig;

import android.content.Context;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import me.gitai.phuckqq.data.QQNotification;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
/**
 * Created by gitai on 16-4-10.
 */
public class NotificationHook implements IXposedHookLoadPackage {
    private List<QQNotification> mMessages = new ArrayList();

	private void notificationHook(XC_LoadPackage.LoadPackageParam lpparam) throws ClassNotFoundException,NoSuchFieldException,IllegalAccessException{
		String className = "android.app.NotificationManager"; //类名
        	String methodName = "notify";  //方法名

        	XposedHelpers.findAndHookMethod(className, lpparam.classLoader, methodName,
                int.class, Notification.class,
                new XC_MethodReplacement() { //替换
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        int id = (int)param.args[0]; //操作id，可用于后期删除，更新等
                        QQNotification notification = new QQNotification(id, (Notification)param.args[1]);//构造自定义通知对象

                        mMessages.add(notification);//插入全局数组

                        HashSet<String> contacts = new HashSet();//统计来源用户，Set不可重复特性，HashSet比较快～
                        StringBuilder messagesBuilder = new StringBuilder();//构造通知正文
                        List<QQNotification> messages = new ArrayList();//因为删除太麻烦，直接构造新数组替换
                        for (int i = (mMessages.size()>notification.getLinesLimit())?(mMessages.size()-notification.getLinesLimit()):0; i < mMessages.size(); i++) {//从-10开始枚举
                            QQNotification n = mMessages.get(i);
                            messages.add(n);//填入新数组
                            contacts.add(n.getContact());//填入联系人，用于计数
                            messagesBuilder.append(n.getSummary());//获取摘要，格式 NickName：Body
                            if (i < mMessages.size() - 1) {
                                messagesBuilder.append("\n");
                            }
                        }
                        mMessages = messages;

                        Context context = (Context)XposedHelpers.getObjectField(param.thisObject, "mContext");

                        String summary = String.format("%d messages from %d contacts", mMessages.size(), contacts.size());

                        NotificationCompat.Builder builder = notification.getBuilder(context) //构造通知对象
                            .setStyle(new NotificationCompat.BigTextStyle()
                                .setBigContentTitle(notification.getContact())
                                .setSummaryText(summary)
                                .bigText(messagesBuilder.toString()));

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                            builder.setPriority(Notification.PRIORITY_MAX);
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                            builder.setCategory(Notification.CATEGORY_ALARM);
                        }

                        ((NotificationManager)param.thisObject).notify(null, notification.number, builder.build());
                        //((NotificationManager)param.thisObject).notify(null, notification.number + 1, notification.getNotification()); //启用原通知
                        return null;
                    }
                });
	}

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        Application app = AndroidAppHelper.currentApplication();

        //初始化各种工具库
        L.setLogcatEnable(app, true); //启用
        L.setLogToFileEnable(true, app, "/sdcard/phuckqq/logs"); //输出外部文件
        L.setXposedMode(true); //启用xposed输出

        //L.d(lpparam.packageName);
        if (lpparam.packageName.startsWith("com.tencent.qq")) { //所有（大概 的QQ的包名
            L.d("initializing...");

            //设备信息，习惯性输出
            L.i("Phone manufacturer: %s", Build.MANUFACTURER);
            L.i("Phone model: %s", Build.MODEL);
            L.i("Android version: %s", Build.VERSION.RELEASE);
            L.i("Xposed bridge version: %d", XposedBridge.XPOSED_BRIDGE_VERSION);
            L.i("Module Version: %s (%d)", BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);

            try {
                notificationHook(lpparam);
            } catch (Throwable e) {
                L.d("Failed to hook NotificationManager of QQ" + "\n" + Log.getStackTraceString(e));
                throw e;
            }
            L.d("initialization complete!");
        }
    }
}

