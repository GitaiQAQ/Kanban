package me.gitai.kanban.danmu;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;

import me.gitai.kanban.Constant;
import me.gitai.kanban.data.QQMessage;
import me.gitai.library.utils.L;

/**
 * Created by i@gitai.me on 16-4-12.
 */
public class NotificationReceiver extends BroadcastReceiver{
    private QQMessage currentQQMessage;
    private int contactCount,unreadCount,pid = 0;
    private boolean needTicker;
    private ArrayList<QQMessage> parcelQQMessages;

    @Override
    public void onReceive(Context context, Intent intent) {
        L.d();

        Bundle bundle = intent.getExtras();
        if (bundle == null){
            return;
        }
        L.d("bundle is not null");

        contactCount = bundle.getInt(Constant.KEY_CONTACTS_COUNT, 0);

        unreadCount = bundle.getInt(Constant.KEY_UNREADS_COUNT, 0);

        pid = bundle.getInt(Constant.KEY_APP_PID, 0);

        needTicker = bundle.getBoolean(Constant.KEY_NEEDTICKER, false);

        currentQQMessage = (QQMessage)bundle.getParcelable(Constant.KEY_CURRENT_MESSAGE);

        L.d(currentQQMessage.toString().replace(",", "\n"));

        if (currentQQMessage == null) {
            return;
        }

        parcelQQMessages = bundle.getParcelableArrayList(Constant.KEY_MESSAGES);

        if (parcelQQMessages == null) {
            parcelQQMessages = new ArrayList();
            //parcelQQMessages.add(currentQQMessage);
        }

        //String title = context.getString(R.string.notification_title);

        //if (parcelQQMessages.size() > 1){
            //title = String.format(context.getString(R.string.notification_title_over) , contactCount, unreadCount);
        //}
    }
}
