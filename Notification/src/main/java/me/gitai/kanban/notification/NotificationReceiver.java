package me.gitai.kanban.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.PendingIntent;
import android.os.Build;
import android.os.Bundle;
import android.content.ComponentName;
import java.io.File;
import android.support.v4.app.NotificationCompat;

import me.gitai.kanban.Constant;
import me.gitai.library.utils.StringUtils;
import android.graphics.BitmapFactory;

import me.gitai.library.utils.L;
import java.util.ArrayList;
import me.gitai.kanban.data.QQMessage;
import me.gitai.kanban.utils.Avatar;
import me.gitai.kanban.utils.SystemUtil;
import me.gitai.library.utils.SharedPreferencesUtil;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by i@gitai.me on 16-4-12.
 */
public class NotificationReceiver extends BroadcastReceiver{
    private QQMessage currentQQMessage;
    private String packageName;
    private int contactCount,unreadCount,pid = 0;
    private boolean needTicker;
    private ArrayList<QQMessage> parcelQQMessages;

    @Override
    public void onReceive(Context context, Intent intent) {
        L.d();

        if (!SharedPreferencesUtil.getInstence(null).getBoolean("general_enable",false)) return;

        Bundle bundle = intent.getExtras();
        if (bundle == null){
            return;
        }
        L.d("bundle is not null");

        contactCount = bundle.getInt(Constant.KEY_CONTACTS_COUNT, 0);

        unreadCount = bundle.getInt(Constant.KEY_UNREADS_COUNT, 0);

        packageName = bundle.getString(Constant.KEY_PACKAGENAME, SystemUtil.getPackageName(0));

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

        String title = context.getString(R.string.notification_title);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        if (parcelQQMessages.size() > 1){
            title = String.format(context.getString(R.string.notification_title_over) , unreadCount, contactCount);
        }
        inboxStyle.setBigContentTitle(title);
        inboxStyle.setSummaryText(currentQQMessage.getSummary());
        for (QQMessage tmpQQMessage : parcelQQMessages) {
            inboxStyle.addLine(tmpQQMessage.getSummary());
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.ic_notify)
            .setLargeIcon(
                    BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
            .setWhen(System.currentTimeMillis())
            .setAutoCancel(true)
            .setContentTitle(title)
            .setContentText(currentQQMessage.getSummary())
            .setContentInfo(""+ unreadCount)
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentIntent(getIntent(context))
            .setStyle(inboxStyle);

        String head = null;
        SystemUtil.setPackageName(packageName);
        if (!StringUtils.isEmpty(currentQQMessage.getSenderuin())) {
            head = Avatar.getHead(0, currentQQMessage.getSenderuin());
        }
        if (!StringUtils.isEmpty(currentQQMessage.getFrienduin())
         && currentQQMessage.getFrienduin().equals(currentQQMessage.getSenderuin())
         && (head == null || !new File(head).exists())) {
            head = Avatar.getHead(4, currentQQMessage.getFrienduin());
        }
        if (head != null && new File(head).exists()) {
            builder.setLargeIcon(new BitmapDrawable(head).getBitmap());
        }

        if (needTicker){
            builder.setTicker(title);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            builder.setCategory(Notification.CATEGORY_ALARM);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            builder.setPriority(Notification.PRIORITY_MAX);
            //notification.bigContentView.setInt(android.R.id.big_text, "setMaxLines", /*calculateMaxLines()*/15);
        }

        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(pid, builder.build());
        //bigContentView.setInt(R.id.big_text, "setMaxLines", calculateMaxLines());
    }

    private PendingIntent getIntent(Context context) {
        //Permission Denial: starting Intent { act=com.tencent.qqlite.action.CHAT flg=0x14000000 cmp=com.tencent.qqlite/com.tencent.mobileqq.activity.ChatActivity (has extras) } from null (pid=-1, uid=10491) not exported from uid 10080
        /*Intent intent = new Intent();
        intent.setClassName(packageName, "com.tencent.mobileqq.activity.ChatActivity");
        intent.addFlags(335544320);
        intent.putExtra("uin", currentQQMessage.getFrienduin());
        intent.putExtra("troop_uin", currentQQMessage.getSenderuin());
        intent.putExtra("uintype", currentQQMessage.getIstroop());
        intent.putExtra("uinname", currentQQMessage.getNickName());
        intent.setAction("com.tencent.qqlite.action.CHAT");*/
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        return PendingIntent.getActivity(context, (int)0, (Intent)intent, (int)268435456);
    }
}
