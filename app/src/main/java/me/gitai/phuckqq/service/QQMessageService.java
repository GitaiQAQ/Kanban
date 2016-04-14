package me.gitai.phuckqq.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import me.gitai.library.utils.L;
import me.gitai.library.utils.StringUtils;
import me.gitai.phuckqq.data.QQMessage;
import me.gitai.phuckqq.xposed.IQQAidlInterface;

/**
 * Created by i@gitai.me on 16-4-14.
 */
public class QQMessageService extends Service{
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        L.d();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.d();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        L.d(StringUtils.toString(intent));
        return mBinder;
        /*if(IQQAidlInterface.class.getName().equals(intent.getAction())){
            return mBinder;
        }
        return null;*/

    }

    private IQQAidlInterface.Stub mBinder = new IQQAidlInterface.Stub(){

        @Override
        public boolean sendMessageByUin(int uin, String message)
                throws RemoteException {
            L.d(uin + message);
            return false;
        }

        @Override
        public void onMessageReciver(QQMessage message) throws RemoteException {
            L.d(message.toString());

        }
    };
}
