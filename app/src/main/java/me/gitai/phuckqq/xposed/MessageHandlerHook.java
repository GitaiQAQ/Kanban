package me.gitai.phuckqq.xposed;

import android.app.AndroidAppHelper;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import me.gitai.library.utils.L;
import me.gitai.library.utils.StringUtils;
import me.gitai.phuckqq.Constant;
import me.gitai.phuckqq.data.Message;

/**
 * Created by gitai on 16-2-29.
 */
public class MessageHandlerHook implements IXposedHookLoadPackage {

    private Class<?> QQMessageFacadeMessage,QQAppInterface, SessionInfo, ChatActivityFacade, QQMessageFacade,MobileQQ,ContactUtils,FriendManager;
    private Field QQMessageFacadeField, ConcurrentHashMapField, HashMapField, ListField, MapField, AtomicIntegerField;

    private ArrayList<Message> mMessages = new ArrayList();


    private void init(XC_LoadPackage.LoadPackageParam lpparam) throws ClassNotFoundException,NoSuchFieldException,IllegalAccessException{
        if(QQAppInterface == null){
            QQAppInterface = lpparam.classLoader.loadClass("com.tencent.mobileqq.app.QQAppInterface");
        }

        if(MobileQQ == null){
            MobileQQ = lpparam.classLoader.loadClass("mqq.app.MobileQQ");
        }

        if(SessionInfo == null){
            SessionInfo = lpparam.classLoader.loadClass("com.tencent.mobileqq.activity.aio.SessionInfo");
        }

        if(QQMessageFacade == null){
            QQMessageFacade = lpparam.classLoader.loadClass("com.tencent.mobileqq.app.message.QQMessageFacade");
        }

        if(QQMessageFacadeMessage == null){
            QQMessageFacadeMessage = lpparam.classLoader.loadClass("com.tencent.mobileqq.app.message.QQMessageFacade$Message");
        }

        if(ContactUtils == null){
            ContactUtils = lpparam.classLoader.loadClass("com.tencent.mobileqq.utils.ContactUtils");
        }

        if(FriendManager == null){
            FriendManager = lpparam.classLoader.loadClass("com.tencent.mobileqq.model.FriendManager");
        }
    }

    private void hook3(XC_LoadPackage.LoadPackageParam lpparam) throws ClassNotFoundException,NoSuchFieldException,IllegalAccessException{
        String className = "com.tencent.mobileqq.app.QQAppInterface";
        String methodName = "a";

        init(lpparam);

        L.d("Hooking a(QQMessageFacade$Message message, boolean needTicker)");
        XposedHelpers.findAndHookMethod(className, lpparam.classLoader, methodName,
            QQMessageFacadeMessage, boolean.class,
            new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    L.d("hook3->beforeHookedMethod");
                    Message currentMessage = new Message(param.args[0]);
                    boolean needTicker = (boolean)param.args[1];

                    mMessages.add(currentMessage);

                    L.d(currentMessage.toString().replace(",", "\n") +"\nneedTicker:" + needTicker +"\n");

                    QQMessageFacadeField = (QQMessageFacadeField != null)?QQMessageFacadeField:findFieldbyType(QQAppInterface, QQMessageFacade);
                    Object qQMessageFacade = QQMessageFacadeField.get(param.thisObject);

                    /*try {//不知道有什么用，始终为空
                        ConcurrentHashMapField = (ConcurrentHashMapField != null)?ConcurrentHashMapField:findFieldbyType(QQMessageFacade, ConcurrentHashMap.class);
                        ConcurrentHashMap conCurrentHashMap = (ConcurrentHashMap)ConcurrentHashMapField.get(qQMessageFacade);

                        L.d("ConcurrentHashMapSize: " + conCurrentHashMap.size());
                    }catch (Throwable e) {
                        L.e(e);
                    }*/

                    /*try {//不知道有什么用，始终为空
                        AtomicIntegerField = (AtomicIntegerField != null)?AtomicIntegerField:findFieldbyType(QQMessageFacade, AtomicInteger.class);
                        AtomicInteger atomicInteger = (AtomicInteger)AtomicIntegerField.get(qQMessageFacade);

                        L.d("AtomicInteger:  " + atomicInteger.toString());
                    }catch (Throwable e) {
                        L.e(e);
                    }*/

                    /*try {//checkMsgCounts key<string>(tableName):value<int>(count)
                        HashMapField = (HashMapField != null)?HashMapField:findFieldbyType(QQMessageFacade, HashMap.class);
                        HashMap hashMap = (HashMap)HashMapField.get(qQMessageFacade);

                        L.d("HashMapSize: " + hashMap.size());
                    }catch (Throwable e) {
                        L.e(e);
                    }*/

                    /*try {//key<string>(senderuin):value<QQMessageFacade$Message>(Message) 每个会话最后一条消息
                        MapField = (MapField != null)?MapField:findFieldbyType(QQMessageFacade, Map.class);
                        Map map = (Map)MapField.get(qQMessageFacade);
                        Iterator itr = map.keySet().iterator();
                        while(itr.hasNext()){
                            String key = (String)itr.next();
                            new Message(map.get(key));
                        }
                    }catch (Throwable e) {
                        L.e(e);
                    }*/

                    int contactCount = 0;
                    int unreadCount = 0;

                    try {
                        ListField = (ListField != null)?ListField:findFieldbyType(QQMessageFacade, List.class);
                        List list = (List)ListField.get(qQMessageFacade);

                        contactCount = list.size();//contact Count

                        Iterator iterator = list.iterator();
                        while (iterator.hasNext()) {
                            unreadCount += new Message(iterator.next()).getCounter();//message Count
                        }
                    }catch (Throwable e) {
                        L.e(e);
                    }

                    ArrayList<Message> tmpMessages = new ArrayList();//因为删除太麻烦，直接构造新数组替换
                    int beginPos = (mMessages.size() > unreadCount)?(mMessages.size() - unreadCount):0;

                    for (int i = beginPos; i < mMessages.size(); i++) {//从 unreadCount 开始枚举
                        Message tmpMessage = mMessages.get(i);
                        if(StringUtils.isEmpty(tmpMessage.getNickName())){
                            tmpMessage.setNickName(getNickName(param.thisObject, tmpMessage.getSelfuin(), tmpMessage.getSenderuin(), tmpMessage.getFrienduin()));
                        }
                        tmpMessages.add(tmpMessage);//填入新数组
                    }
                    mMessages = tmpMessages;

                    if (StringUtils.isEmpty(currentMessage.getNickName())) {
                        currentMessage.setNickName(getNickName(param.thisObject, currentMessage.getSelfuin(), currentMessage.getSenderuin(), currentMessage.getFrienduin()));
                    }

                    try {
                        Application baseApplication = (Application)XposedHelpers.getObjectField(param.thisObject, "mContext");

                        Intent intent =new Intent(Constant.ACTION_RECEIVER);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constant.KEY_CONTACTS_COUNT, contactCount);
                        bundle.putInt(Constant.KEY_UNREADS_COUNT, unreadCount);
                        bundle.putBoolean(Constant.KEY_NEEDTICKER, needTicker);
                        bundle.putParcelable(Constant.KEY_CURRENT_MESSAGE, currentMessage);
                        bundle.putParcelableArrayList(Constant.KEY_MESSAGES, mMessages);
                        intent.putExtras(bundle);
                        baseApplication.sendBroadcast(intent);
                    }catch (Throwable e) {
                        L.e(e);
                    }
                }

            });
    }

    private Object friendManager;

    private String getNickName(Object appRuntime, String selfuin, String senderuin, String frienduin){
        if (selfuin.equals(senderuin)) return "\u6211";
        /*Object friendManager = XposedHelpers.callMethod(appRuntime, "getManager",
                new Class<?>[]{int.class},8);
        L.d("friendManager: " + friendManager.getClass().getName());*/
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

    private Field findFieldbyType(Class<?> thisClass, Class<?> targetClass) {
        Field[] fields = thisClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getType().equals(targetClass)){
                return fields[i];
            }
        }
        return null;
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        Application qQApplication = AndroidAppHelper.currentApplication();

        L.setLogcatEnable(qQApplication, true);
        L.setLogToFileEnable(true, qQApplication, Constant.PATH_DATA_LOG);
        L.setXposedMode(true);

        if (lpparam.packageName.startsWith("com.tencent.qq")) {
            try {
                hook3(lpparam);
            } catch (Throwable e) {
                L.d("Failed to hook3 QQ handler" + "\n" + Log.getStackTraceString(e));
                throw e;
            }
        }
    }
}
