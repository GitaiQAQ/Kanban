package me.gitai.phuckqq.ui;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.preference.PreferenceActivity;
import android.os.Build;
import android.os.Bundle;
import me.gitai.library.utils.L;
import me.gitai.phuckqq.Constant;
import android.content.Intent;
import me.gitai.phuckqq.data.Message;
import me.gitai.phuckqq.service.QQMessageService;

/**
 * Created by gitai on 15-12-12.
 */
public class MainPreferences extends PreferenceActivity {
	private QQMessageService localQQMessageService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		L.d();

		getPreferenceManager().setSharedPreferencesMode(MODE_WORLD_READABLE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getListView().setPadding(0,0,0,0);
		}

		try{
			Message message = new Message();
			message.setMsg("妈的阿库娅");
			message.setNickName("Gitai");

			Intent intent =new Intent(Constant.ACTION_RECEIVER);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Bundle bundle = new Bundle();
			bundle.putInt(Constant.KEY_CONTACTS_COUNT, 10);
			bundle.putInt(Constant.KEY_UNREADS_COUNT, 5);
			bundle.putBoolean(Constant.KEY_NEEDTICKER, true);
			bundle.putParcelable(Constant.KEY_CURRENT_MESSAGE, message);
			//bundle.putParcelableArrayList(Constant.KEY_MESSAGES, mMessages);
			intent.putExtras(bundle);
			sendBroadcast(intent);
		}catch(Throwable e){
			L.e(e);
		}

		bindService(new Intent(this, QQMessageService.class), new ServiceConnection() {
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				localQQMessageService = ((QQMessageService.LocalBinder)service).getService();
			}

			@Override
			public void onServiceDisconnected(ComponentName name) {
				localQQMessageService = null;
			}
		}, BIND_AUTO_CREATE);
	}
}