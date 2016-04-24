package me.gitai.kanban.data;

import android.annotation.TargetApi;
import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import me.gitai.library.utils.StringUtils;

/**
 * Created by gitai on 16-4-11.
 */
public class QQNotification {
	public Notification mNotification;
	public String title,titleBig,text,subText,infoText,summaryText,tickerText;
	public int number,mId,icon,progress,progressMax;
	public Bitmap largeIcon,largeIconBig,picture;
	public boolean progressIndeterminate,showChronometer,showWhen;
	public CharSequence[] textLines;

	public String mGroupName,mNickName,mNickNameShort,mMessageText = "";

	@TargetApi(Build.VERSION_CODES.KITKAT)
	public QQNotification(int id, Notification notification) {
		this.mId = id;
		this.number = notification.number;

		this.mNotification = notification;

		this.tickerText = (String)notification.tickerText;

		this.title = (String)notification.extras.getCharSequence(Notification.EXTRA_TITLE); //Group/User Name
		this.titleBig = (String)notification.extras.getCharSequence(Notification.EXTRA_TITLE_BIG);
		this.text = (String)notification.extras.getCharSequence(Notification.EXTRA_TEXT); //NickName: MessageBody
		this.subText = (String)notification.extras.getCharSequence(Notification.EXTRA_SUB_TEXT);
		this.infoText = (String)notification.extras.getCharSequence(Notification.EXTRA_INFO_TEXT);
		this.summaryText = (String)notification.extras.getCharSequence(Notification.EXTRA_SUMMARY_TEXT);
		this.icon = notification.extras.getInt(Notification.EXTRA_SMALL_ICON); //Application Icon for Notification
		this.progress = notification.extras.getInt(Notification.EXTRA_PROGRESS);
		this.progressMax = notification.extras.getInt(Notification.EXTRA_PROGRESS_MAX);
		this.largeIcon = notification.largeIcon; //Group/User Avatar
		//this.notification.extras.getParcelable(Notification.EXTRA_LARGE_ICON);
		this.largeIconBig = notification.extras.getParcelable(Notification.EXTRA_LARGE_ICON_BIG);
		this.picture = notification.extras.getParcelable(Notification.EXTRA_PICTURE);
		this.progressIndeterminate = notification.extras.getBoolean(Notification.EXTRA_PROGRESS_INDETERMINATE);
		this.showChronometer = notification.extras.getBoolean(Notification.EXTRA_SHOW_CHRONOMETER);
		this.showWhen = notification.extras.getBoolean(Notification.EXTRA_SHOW_WHEN);
		this.textLines = notification.extras.getCharSequenceArray(Notification.EXTRA_TEXT_LINES);

		this.mGroupName = title;
		String[] msg = text.split(": ");
		if (msg.length > 1) { //拆通知～
			//群组类
			mNickName = msg[0];
			for (int i = 1; i < msg.length; i++) {
				mMessageText = mMessageText + msg[i];
			}
		}else{
			//私聊会话
			this.mNickName = this.mGroupName;
			this.mGroupName = null;
			this.mMessageText = text;
		}
    }

    public int getLinesLimit(){
    		//输出限制，用最后一个对象计算
    		int count = 10;
		try{count = Integer.parseInt(infoText);}catch(NumberFormatException ex){}
		return (count > 10)?10:count;
    }

    public Notification getNotification() {
		return mNotification;
    }

    public String getContact(){
    		if (!StringUtils.isEmpty(mGroupName))return mGroupName;
    		return mNickName;
    }

    public String getNickName(){
    		return mNickName;
    }

    public String getGroupName(){
    		return mGroupName;
    }

    public String getMessageText(){
    		return mMessageText;
    }

    public String getSummary(){
    		if (mNickName.length() <= 5) return mNickName + ":" + mMessageText; //昵称直接输出
    		if (!StringUtils.isEmpty(mNickNameShort)) return mNickNameShort + ":" + mMessageText; //昵称摘要输出
    		return (mNickNameShort = mNickName.substring(0, 3) + "...") + ":" + mMessageText; //
    }

    public NotificationCompat.Builder getBuilder(Context context){
    		return new NotificationCompat.Builder(context)
            .setSmallIcon(mNotification.icon)
            .setWhen(mNotification.when)
            .setLargeIcon(largeIcon)
            .setAutoCancel(true)
            //.setContent(notification.contentView)
            .setContentTitle(getContact())
            .setContentText(getSummary())
            .setContentInfo(infoText)
            .setTicker(tickerText)
            .setContentIntent(mNotification.contentIntent);
    }

    @Override
    public String toString() { //给其他方法调用
    		return getSummary();
    }
}