package me.gitai.kanban.core.xposed;

import android.app.AndroidAppHelper;
import android.app.Application;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import me.gitai.kanban.core.BuildConfig;
import me.gitai.library.utils.L;
import me.gitai.library.utils.StringUtils;
import me.gitai.kanban.Constant;
import me.gitai.kanban.data.QQMessage;

/**
 * Created by gitai on 16-2-29.
 */
public class MessageHandlerHook implements IXposedHookLoadPackage {

    private Class<?> QQMessageFacadeMessage,QQAppInterface, SessionInfo, ChatActivityFacade, QQMessageFacade,MobileQQ,ContactUtils,FriendManager;
    private Field QQMessageFacadeField, ConcurrentHashMapField, HashMapField, ListField, MapField, AtomicIntegerField;

    private ArrayList mQQMessages = new ArrayList();

    private Application baseApplication;

    private Object friendManager;

    private String packageName;

    /*private IQQAidlInterface mInterface;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mInterface = IQQAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mInterface = null;
        }
    };*/

    private void initStaticClass(XC_LoadPackage.LoadPackageParam lpparam)
            throws ClassNotFoundException,NoSuchFieldException,IllegalAccessException{

        QQAppInterface = (QQAppInterface != null)?
                QQAppInterface:lpparam.classLoader.loadClass("com.tencent.mobileqq.app.QQAppInterface");

        MobileQQ = (MobileQQ != null)?
                MobileQQ:lpparam.classLoader.loadClass("mqq.app.MobileQQ");

        SessionInfo = (SessionInfo != null)?
                SessionInfo:lpparam.classLoader.loadClass("com.tencent.mobileqq.activity.aio.SessionInfo");

        QQMessageFacade = (QQMessageFacade != null)?
                QQMessageFacade:lpparam.classLoader.loadClass("com.tencent.mobileqq.app.message.QQMessageFacade");

        QQMessageFacadeMessage = (QQMessageFacadeMessage != null)?
                QQMessageFacadeMessage:lpparam.classLoader.loadClass("com.tencent.mobileqq.app.message.QQMessageFacade$Message");

        ContactUtils = (ContactUtils != null)?
                ContactUtils:lpparam.classLoader.loadClass("com.tencent.mobileqq.utils.ContactUtils");

        FriendManager = (FriendManager != null)?
                FriendManager:lpparam.classLoader.loadClass("com.tencent.mobileqq.model.FriendManager");

        ChatActivityFacade = (ChatActivityFacade != null)?
                ChatActivityFacade:lpparam.classLoader.loadClass("com.tencent.mobileqq.activity.ChatActivityFacade");
    }

    private void initField()
            throws ClassNotFoundException,NoSuchFieldException,IllegalAccessException{

        QQMessageFacadeField = (QQMessageFacadeField != null)?
                QQMessageFacadeField: XposedHelpers.findFirstFieldByExactType(QQAppInterface, QQMessageFacade);

        ListField = (ListField != null)?
                ListField: XposedHelpers.findFirstFieldByExactType(QQMessageFacade, List.class);

        /*if (QQMessageFacadeField == null || ListField == null){
            Class clz = QQAppInterface;
            do {
                Field[] fields;
                int fieldCount = (fields = QQAppInterface.getDeclaredFields()).length;
                for (int i = 0; i < fieldCount; ++i) {
                    Class type = fields[i].getType();
                    L.d("Field Name: "  + fields[i].getName() + ", Type: " + fields[i].getType().getName());
                    if (type.equals(QQMessageFacade) && QQMessageFacadeField == null){
                        fields[i].setAccessible(true);
                        QQMessageFacadeField = fields[i];
                        L.d("QQMessageFacadeField");
                    }else if(type.equals(List.class) && ListField == null){
                        fields[i].setAccessible(true);
                        ListField = fields[i];
                        L.d("ListField");
                    }else if(QQMessageFacadeField != null && ListField != null){
                        L.d("break");
                        break;
                    }
                }
            } while((clz = clz.getSuperclass()) != null);
        }*/
    }

    private void hookQQMessagehandler(XC_LoadPackage.LoadPackageParam lpparam)
            throws ClassNotFoundException,NoSuchFieldException,IllegalAccessException{

        if (!sp.getBoolean("prefs_task_hook",false)) return;

        String className = "com.tencent.mobileqq.app.QQAppInterface";
        String methodName = "a";

        /*XposedHelpers.findAndHookConstructor(className, lpparam.classLoader, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                baseApplication = (baseApplication != null)?
                        baseApplication:(Application)XposedHelpers.getObjectField(param.thisObject, "mContext");

                try{
                    Intent intent = new Intent();
                    intent.setAction(QQMessageService.class.getName());
                    baseApplication.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
                }catch (Throwable e) {
                    L.e(e);
                }
            }
        });*/

        L.d("Hooking a(QQMessageFacade$QQMessage message, boolean needTicker)");
        XposedHelpers.findAndHookMethod(className, lpparam.classLoader, methodName,
            QQMessageFacadeMessage, boolean.class,
            new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    QQMessage currentQQMessage = new QQMessage(param.args[0]);
                    boolean needTicker = (boolean)param.args[1];

                    initField();

                    mQQMessages.add(currentQQMessage);

                    L.d(currentQQMessage.toString().replace(",", "\n") +"\nneedTicker:" + needTicker +"\n");

                    Object qQMessageFacade = QQMessageFacadeField.get(param.thisObject);

                    int contactCount = 0;
                    int unreadCount = 0;

                    try {
                        List list = (List)ListField.get(qQMessageFacade);
                        contactCount = list.size();//contact Count
                        Iterator iterator = list.iterator();
                        while (iterator.hasNext()) {
                            unreadCount += new QQMessage(iterator.next()).getCounter();//message Count
                        }
                    }catch (Throwable e) {
                        L.e(e);
                    }

                    ArrayList<QQMessage> tmpQQMessages = new ArrayList();//因为删除太麻烦，直接构造新数组替换
                    int beginPos = (mQQMessages.size() > unreadCount)?(mQQMessages.size() - unreadCount):0;

                    for (int i = beginPos; i < mQQMessages.size(); i++) {//从 unreadCount 开始枚举
                        QQMessage tmpQQMessage = (QQMessage)mQQMessages.get(i);
                        if(StringUtils.isEmpty(tmpQQMessage.getNickName())){
                            tmpQQMessage.setNickName(
                                    getNickName(param.thisObject,
                                            tmpQQMessage.getSelfuin(),
                                            tmpQQMessage.getSenderuin(),
                                            tmpQQMessage.getFrienduin()));
                        }
                        tmpQQMessages.add(tmpQQMessage);//填入新数组
                    }
                    mQQMessages = tmpQQMessages;

                    if (StringUtils.isEmpty(currentQQMessage.getNickName())) {
                        currentQQMessage.setNickName(
                                getNickName(param.thisObject,
                                        currentQQMessage.getSelfuin(),
                                        currentQQMessage.getSenderuin(),
                                        currentQQMessage.getFrienduin()));
                    }

                    /*try{
                        mInterface.sendMessageByUin(currentQQMessage.getSenderuin(), currentQQMessage.getMsg());
                    }catch (Throwable e) {
                        L.e(e);
                    }*/

                    if (!sp.getBoolean("prefs_task_broadcast",false)) return;
                    try {
                        baseApplication = (baseApplication != null)?
                                baseApplication:(Application)XposedHelpers.getObjectField(param.thisObject, "mContext");

                        Intent intent =new Intent(Constant.ACTION_RECEIVER);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.KEY_PACKAGENAME, packageName);
                        bundle.putInt(Constant.KEY_APP_PID, Process.myPid());
                        bundle.putInt(Constant.KEY_CONTACTS_COUNT, contactCount);
                        bundle.putInt(Constant.KEY_UNREADS_COUNT, unreadCount);
                        bundle.putBoolean(Constant.KEY_NEEDTICKER, needTicker);
                        bundle.putParcelable(Constant.KEY_CURRENT_MESSAGE, currentQQMessage);
                        bundle.putParcelableArrayList(Constant.KEY_MESSAGES, (ArrayList<QQMessage>)mQQMessages);
                        intent.putExtras(bundle);
                        baseApplication.sendBroadcast(intent);
                    }catch (Throwable e) {
                        L.e(e);
                    }
                }

            });
    }

    private void hookNotification(XC_LoadPackage.LoadPackageParam lpparam)
            throws ClassNotFoundException,NoSuchFieldException,IllegalAccessException{
        if (!sp.getBoolean("prefs_intercept",false)) return;

        String className = "android.app.NotificationManager"; //类名
        String methodName = "notify";  //方法名

        L.d("Hooking notify(int id, Notification notification)");
        XposedHelpers.findAndHookMethod(className, lpparam.classLoader, methodName,
                int.class, Notification.class,
                new XC_MethodReplacement() { //替换
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        L.d("notify()");
                        return null;
                    }
                });
    }

    private String getNickName(Object appRuntime, String selfuin, String senderuin, String frienduin){
        if (selfuin.equals(senderuin)) return "\u6211";
        try{
            if (friendManager == null) {
                HashMap managers = (HashMap)XposedHelpers.getObjectField(appRuntime, "managers");
                Iterator itr = managers.keySet().iterator();
                while(itr.hasNext()){
                    Integer key = (Integer)itr.next();
                    L.d("Manager:" + key + "  " + managers.get(key).getClass().getName());
                    if(managers.get(key).getClass().getName().equals(FriendManager)){
                        friendManager = managers.get(key);
                        break;
                    }
                }
            }
            if (friendManager != null) {
                 return (String)XposedHelpers.callMethod(friendManager, "c",
                    new Class<?>[]{String.class, String.class},senderuin, frienduin);
            }
            return (String)XposedHelpers.callStaticMethod(ContactUtils, "a",
                new Class<?>[]{QQAppInterface, String.class, int.class},
                appRuntime, senderuin, 0);
        }catch(Throwable e){
            L.e(e);
            return senderuin;
        }
    }

    private XSharedPreferences sp;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        Application qQApplication = AndroidAppHelper.currentApplication();

        L.setLogcatEnable(qQApplication, true);
        L.setLogToFileEnable(true, qQApplication, Constant.PATH_DATA_LOG);
        L.setXposedMode(true);

        sp = new XSharedPreferences(BuildConfig.APPLICATION_ID);
        if (!sp.getBoolean("general_enable", false)) {
            L.d(BuildConfig.APPLICATION_ID);
            L.d("Enable: " + "false");
            return;
        }

        if (!(packageName = lpparam.packageName)./*equals("com.tencent.qq.kddi")*/startsWith("com.tencent.qq")) {
            L.d("packageName: " + packageName);
            return;
        }

        initStaticClass(lpparam);
        try {
            hookQQMessagehandler(lpparam);
        } catch (Throwable e) {
            L.d("Failed to Hook QQMessagehandler", Log.getStackTraceString(e));
        }
        try {
            hookNotification(lpparam);
        } catch (Throwable e) {
            L.d("Failed to Hook Notification", Log.getStackTraceString(e));
        }
    }
}
