package me.gitai.phuckqq.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import me.gitai.phuckqq.Constant;
import android.graphics.BitmapFactory;

import me.gitai.library.utils.L;
import java.util.ArrayList;
import me.gitai.phuckqq.R;
import me.gitai.phuckqq.data.QQMessage;

/**
 * Created by i@gitai.me on 16-4-12.
 */
public class NotificationReceiver extends BroadcastReceiver{
    private QQMessage currentQQMessage;
    private int contactCount,unreadCount;
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

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(parcelQQMessages.size() + " new messages");
        inboxStyle.setSummaryText(currentQQMessage.getSummary());
        for (QQMessage tmpQQMessage : parcelQQMessages) {
            inboxStyle.addLine(tmpQQMessage.getSummary());
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.ic_notify)
            .setLargeIcon(
                    BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
            .setWhen(System.currentTimeMillis())
            //.setLargeIcon(largeIcon)
            .setAutoCancel(true)
            .setContentTitle(parcelQQMessages.size() + " new messages")
            .setContentText(currentQQMessage.getSummary())
            .setContentInfo(""+ parcelQQMessages.size())
            .setTicker("New message")
            .setDefaults(Notification.DEFAULT_ALL)
            //.setContentIntent(mNotification.contentIntent)
            .setStyle(inboxStyle);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            builder.setCategory(Notification.CATEGORY_ALARM);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            builder.setPriority(Notification.PRIORITY_MAX);
            //notification.bigContentView.setInt(android.R.id.big_text, "setMaxLines", /*calculateMaxLines()*/15);
        }

        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(12535, builder.build());
        //bigContentView.setInt(R.id.big_text, "setMaxLines", calculateMaxLines());

    }
}
