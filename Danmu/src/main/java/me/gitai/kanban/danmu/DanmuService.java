package me.gitai.kanban.danmu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import java.util.ArrayList;

import me.gitai.kanban.Constant;
import me.gitai.kanban.data.QQMessage;
import me.gitai.library.utils.L;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.Binder;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.content.BroadcastReceiver;
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

import java.util.HashMap;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import master.flame.danmaku.danmaku.model.Duration;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.BaseCacheStuffer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.danmaku.parser.android.BiliDanmukuParser;
import master.flame.danmaku.danmaku.util.IOUtils;
import master.flame.danmaku.ui.widget.DanmakuView;

import master.flame.danmaku.danmaku.model.SpecialDanmaku;

import me.gitai.library.utils.L;
import me.gitai.library.utils.SharedPreferencesUtil;

/**
 * Created by i@gitai.me on 16-4-24.
 */
public class DanmuService extends Service{
    private QQMessage currentQQMessage;

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private DanmakuView mDanmakuView;
    private BaseDanmakuParser mParser;
    private DanmakuContext mContext;
    private GestureDetector gestureDetector;

    private float mDispScaleX, mDispScaleY;

    private IBinder binder = new DanmuBinder();

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            L.d();

            Bundle bundle = intent.getExtras();
            if (bundle == null){
                return;
            }
            L.d("bundle is not null");

            //contactCount = bundle.getInt(Constant.KEY_CONTACTS_COUNT, 0);

            //unreadCount = bundle.getInt(Constant.KEY_UNREADS_COUNT, 0);

            //pid = bundle.getInt(Constant.KEY_APP_PID, 0);

            //needTicker = bundle.getBoolean(Constant.KEY_NEEDTICKER, false);

            currentQQMessage = (QQMessage)bundle.getParcelable(Constant.KEY_CURRENT_MESSAGE);

            L.d(currentQQMessage.toString().replace(",", "\n"));

            if (currentQQMessage == null) {
                return;
            }
	        try{
	        	addDanmaku(currentQQMessage);
	        }catch(Exception ex){

	        }
        }
    };

    @Override
    public IBinder onBind(Intent arg0) {
        L.d();
        return binder;
    }

    @Override
    public void onCreate() {
        L.d();
        super.onCreate();
        createFloatView();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_RECEIVER);

        registerReceiver(receiver, filter);
    }

    @Override
    public void onDestroy() {
        L.d();
        super.onDestroy();
        if (mDanmakuView != null) {
            mDanmakuView.release();
            mDanmakuView = null;
        }
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    public class DanmuBinder extends Binder {
        DanmuService getService() {
            L.d();
            return DanmuService.this;
        }
    }

    private void createFloatView() {
        L.d();
        windowManager = (WindowManager) getApplicationContext()
                .getSystemService(WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        //width = displayMetrics.widthPixels;
        //height = displayMetrics.heightPixels;

        layoutParams = ((QQApp) getApplication())
                .getWindowManagerLayoutParams();

        layoutParams.type = LayoutParams.TYPE_PHONE;
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
                | LayoutParams.FLAG_NOT_FOCUSABLE
                | LayoutParams.FLAG_NOT_TOUCHABLE
                | LayoutParams.FLAG_FULLSCREEN;

        // 设置最大显示行数
        //HashMap<Integer, Integer> maxLinesPair = new HashMap<Integer, Integer>();
        //maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 8); // 滚动弹幕最大显示8行
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<Integer, Boolean>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);

        mContext = DanmakuContext.create();
        mContext
            .setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3)
            .setDuplicateMergingEnabled(false)
            .setScrollSpeedFactor(1.2f)
            .setScaleTextSize(1.2f)
            //.setCacheStuffer(new SpannedCacheStuffer(), mCacheStufferAdapter) // 图文混排使用SpannedCacheStuffer
            //.setCacheStuffer(new BackgroundCacheStuffer())  // 绘制背景使用BackgroundCacheStuffer
            //.setMaximumLines(maxLinesPair)
            .preventOverlapping(overlappingEnablePair);

        mDanmakuView = new DanmakuView(getApplicationContext());
        if (mDanmakuView != null) {
            mParser = new BaseDanmakuParser() {
                @Override
                protected Danmakus parse() {
                    return new Danmakus();
                }
            };
            mDanmakuView.setCallback(new master.flame.danmaku.controller.DrawHandler.Callback() {
                @Override
                public void updateTimer(DanmakuTimer timer) {
                }

                @Override
                public void drawingFinished() {

                }

                @Override
                public void danmakuShown(BaseDanmaku danmaku) {
                	L.d("danmakuShown(): text=" + danmaku.text);
                }

                @Override
                public void prepared() {
                    mDanmakuView.start();
                }
            });
             mDanmakuView.prepare(mParser, mContext);
             mDanmakuView.enableDanmakuDrawingCache(true);

             mDispScaleX = mContext.getDisplayer().getWidth() / mContext.mDanmakuFactory.BILI_PLAYER_WIDTH;
        	    mDispScaleY = mContext.getDisplayer().getHeight() / mContext.mDanmakuFactory.BILI_PLAYER_HEIGHT;

            SharedPreferences sp = SharedPreferencesUtil.getInstence(null);
            showFPS(sp.getBoolean("general_debug", false));
            setTextSize(sp.getString("prefs_style_size", "24f"));
            setTextColor(sp.getString("prefs_style_color", "white"));
            setTextShadow(sp.getBoolean("prefs_style_shadow", false));

            windowManager.addView(mDanmakuView, layoutParams);
        }
    }

    public void showFPS(boolean show){
        mDanmakuView.showFPS(show);
    }

    public float textSize = 0f;
    public int textColor = Color.WHITE;
    public boolean textShadow = false;

    public boolean setTextSize(Object textSize) {
        try{
            this.textSize = Float.parseFloat((String)textSize);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public float getTextSize() {
        return (textSize < 14f?24f:textSize) * (mParser.getDisplayer().getDensity() - 0.6f);
    }

    public boolean setTextColor(Object textColor) {
        try{
            this.textColor = Color.parseColor((String)textColor);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public void setTextShadow(boolean textShadow) {
        this.textShadow = textShadow;
    }

    public void addDanmaku(QQMessage qqMessage) {
        L.d();
        if (qqMessage == null) return;
        String uin = qqMessage.getFrienduin();
        String msg = ((String)qqMessage.getMessageText()).trim();
        if (msg == null || msg.length() <=0) return;

        int type = BaseDanmaku.TYPE_SCROLL_RL;
        char head = msg.charAt(0);
        msg = msg.substring(1);
        BaseDanmaku danmaku = null;
        if ("↑".equals(head)) {
        		type = BaseDanmaku.TYPE_FIX_TOP;
        }else if ("↓".equals(head)){
        		type = BaseDanmaku.TYPE_FIX_BOTTOM;
        }else if ("←".equals(head)){
			type = BaseDanmaku.TYPE_SCROLL_LR;
        }else if ("→".equals(head)){
        		type = BaseDanmaku.TYPE_SCROLL_RL;
        }else if ("↞".equals(head)){
        		type = BaseDanmaku.TYPE_SCROLL_LR;
        		msg = String.format("%" + msg.length() + "s", msg);
        }else if ("↠".equals(head)){
        		type = BaseDanmaku.TYPE_SCROLL_RL;
        		msg = String.format("%-" + msg.length() + "s", msg);
        }else if ("↖".equals(head)){
        		type = BaseDanmaku.TYPE_SPECIAL;
        		danmaku = mContext.mDanmakuFactory.createDanmaku(type);
        		mContext.mDanmakuFactory.fillTranslationData(danmaku, 374, 322, 0, 374, 500, 0, mDispScaleX, mDispScaleY);
        		long alphaDuraion = (long) (Float.parseFloat("4.5") * 1000);
        		danmaku.duration = new Duration(alphaDuraion);
        		//danmaku.rotationZ = 315;
              //danmaku.rotationY = 0;
        		mContext.mDanmakuFactory.fillAlphaData(danmaku, 255, 255, alphaDuraion);
        		/*float[][] points = new float[pointStrArray.length][2];
        		for (int i = 0; i < pointStrArray.length; i++) {
                String[] pointArray = pointStrArray[i].split(",");
                points[i][0] = Float.parseFloat(pointArray[0]);
                points[i][1] = Float.parseFloat(pointArray[1]);
            }
        		mContext.mDanmakuFactory.fillLinePathData(danmaku, points, mDispScaleX,
                                        mDispScaleY);*/
        		//((SpecialDanmaku)danmaku).setTranslationData(500,300,0,0,10,0);
        }else if ("↗".equals(head)){
        		type = BaseDanmaku.TYPE_SPECIAL;
        		danmaku = mContext.mDanmakuFactory.createDanmaku(type);
        		mContext.mDanmakuFactory.fillTranslationData(danmaku, 682, 0, 0, 0, 500, 0, mDispScaleX, mDispScaleY);
        		long alphaDuraion = (long) (Float.parseFloat("4.5") * 1000);
        		danmaku.duration = new Duration(alphaDuraion);
        		//danmaku.rotationZ = 315;
              //danmaku.rotationY = 0;
        		mContext.mDanmakuFactory.fillAlphaData(danmaku, 255, 255, alphaDuraion);
        }else if ("↘".equals(head)){
        		type = BaseDanmaku.TYPE_SPECIAL;
        }else if ("↙".equals(head)){
        		type = BaseDanmaku.TYPE_SPECIAL;
        }else if ("↜".equals(head)){
        		type = BaseDanmaku.TYPE_SPECIAL;
        		danmaku = mContext.mDanmakuFactory.createDanmaku(type);
        		float[][] points = {{500,200},{400,100},{300,200},{200,300},{100,200}};
        		((SpecialDanmaku)danmaku).setLinePathData(points);
        }else if ("↝".equals(head)){
        		type = BaseDanmaku.TYPE_SPECIAL;
        }else if ("↩".equals(head)){
        		type = BaseDanmaku.TYPE_SPECIAL;
        }else if ("↪".equals(head)){
        		type = BaseDanmaku.TYPE_SPECIAL;
        }else if ("↫".equals(head)){
        		type = BaseDanmaku.TYPE_SPECIAL;
        }else if ("↬".equals(head)){
        		type = BaseDanmaku.TYPE_SPECIAL;
        }else if ("↯".equals(head)){
        		type = BaseDanmaku.TYPE_SPECIAL;
        }else if ("↰".equals(head)){
        		type = BaseDanmaku.TYPE_SPECIAL;
        }else if ("↱".equals(head)){
        		type = BaseDanmaku.TYPE_SPECIAL;
        }else if ("↲".equals(head)){
        		type = BaseDanmaku.TYPE_SPECIAL;
        }else if ("↳".equals(head)){
        		type = BaseDanmaku.TYPE_SPECIAL;
        }else if ("↴".equals(head)){
        		type = BaseDanmaku.TYPE_SPECIAL;
        }else if ("↵".equals(head)){
        		type = BaseDanmaku.TYPE_SPECIAL;
        }else if ("↶".equals(head)){
        		type = BaseDanmaku.TYPE_SPECIAL;
        }else if ("↷".equals(head)){
        		type = BaseDanmaku.TYPE_SPECIAL;
        }else if ("↺".equals(head)){
        		type = BaseDanmaku.TYPE_SPECIAL;
        }else if ("↻".equals(head)){
        		type = BaseDanmaku.TYPE_SPECIAL;
        }else{
	        msg = head+msg;
	    }
		if (danmaku == null){
			danmaku = mContext.mDanmakuFactory.createDanmaku(type);
		}
        if (danmaku == null || mDanmakuView == null) {
            return;
        }

        danmaku.userHash = uin;
        danmaku.text = qqMessage.getNickName() + ":" + msg;

        danmaku.textSize = getTextSize();
        //danmaku.padding = 5;
        danmaku.priority = 0;  // 可能会被各种过滤器过滤并隐藏显示
        //danmaku.isLive = true;
        danmaku.time = mDanmakuView.getCurrentTime();

        danmaku.textColor = textColor;
        //danmaku.underlineColor = Color.GREEN;
        //danmaku.borderColor = Color.GREEN;


        if (textShadow)
            danmaku.textShadowColor = textColor <= Color.BLACK ? Color.WHITE : Color.BLACK;

        mDanmakuView.addDanmaku(danmaku);
    }
}
