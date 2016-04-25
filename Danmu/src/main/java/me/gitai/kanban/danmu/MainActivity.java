package me.gitai.kanban.danmu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.Process;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import java.util.ArrayList;
import java.util.Random;

import me.gitai.kanban.Constant;
import me.gitai.kanban.data.QQMessage;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import me.gitai.library.utils.L;
import me.gitai.library.utils.SharedPreferencesUtil;

import android.os.IBinder;
/**
 * Created by i@gitai.me on 16-4-23.
 */
public class MainActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
    private DanmuService danmuService;
    private Random random;
    private String[][] testMsgs = {
            {"阿库娅",
                " 汝，迷茫的家里蹲啊。不要太过自责……",
                "不想努力是世间的错，本性恶劣是环境的错，长得丑是遗传基因的错。",
                "不要责备自己，将责任推卸给他人便好……",
                "请放心，神会赦免一切。汝，要爱巨乳，汝，要爱贫乳；阿库西斯教的教义是容许一切。",
                "即使是同性恋者，即使是喜好非人兽耳少女者，即使是萝莉控，即使是尼特；只要对象并非不死者或恶魔少女，只要有爱且不犯法，全都可以得到赦免。",
                "汝，虔诚的教徒啊。为了不再受到恶魔所诱惑，记住这句咒语吧。",
                "“厄里斯的胸部是垫出来的”。",
                "今后若是你的心又受到诱惑，就记得咏唱这句咒语。若是遇见其他受到诱惑的人，告诉他们这句咒语也是好事一桩。",
                "阿库西斯教教义第七项：汝，勿要忍耐。想喝的时候就喝，想吃的时候就吃便好。因为明天并不见得还能吃得到。"},
            {"天野远子",
                "人就是切断了母亲和自己的联系，才能诞生在这个世上。如果不试着斩断过去，就无法迈向未来！",
                "有时必须破坏原有的一切，受尽伤害，才有办法了解某些事，见到某些风景，体会到某些心情。",
                "她相信着希望，相信着未来，继续翻开着故事的下一页。",
                "当夜幕降临，黑暗笼罩全身，在一片漆黑的路上独自前进，也许会感疲惫，也许会感胆怯，只要抬头仰望夜空中闪烁的星光。那清澈的光辉必定会驻留在我们心中，给予我们继续前进的勇气与力量。"},
            {"千反田爱瑠",
                "我很好奇！"},
            {"晓美焰",
                "谁都无法相信未来，谁都无法接受未来。",
                "虽然这是一个不断重复着悲哀和憎恨的无可救药的世界。",
                "这里毕竟曾是她试图保护的地方。我会牢记这一点，永不放弃。",
                "比希望更炽热，比绝望更深邃的，是爱啊！"},
            {"夏达",
                "关山万里路，拔剑起长歌",
                "人只会相信自己意愿相信的东西。",
                "无他，凭心而行，后果自负而已",
                "错过，不是错了，而是过了"},
            {"折木奉太郎",
                "要变得坚强。如果很软弱的话，终有一日会连悲鸣都无法发出，活得像行尸走肉一般。",
                "避免受诡计欺骗的最好方法,就是弄清诡计的所有手法",
                "向消耗大量能量的生活方式敬礼。",
                "没必要的事就不做，必要的事就尽快做。"},
            {"源氏物语",
                "山樱若是多情种，今岁应开墨色花。",
                "心迹未予外人阅，花枝一束故人香。",
                "月华幽光羡登临，红尘悲怆我自知。",
                "不似明灯照，又非暗幕张。朦胧春月夜，美景世无双。",
                "哀此东篱菊，当年共护持。今秋花上露，只湿一人衣。一花一木，故人相植。一思一念，令人成痴。",
                "我好不容易找到了春天，却把它落在了池塘里。",
                "仅仅一张竹帘，便隔开了这未果的恋情。",
                "每晚走过的旅途，仿佛是为了与公子邂逅一般。",
                "艳阳高升，原野上的朝露很快便了无痕迹。源氏痛感人生如梦，像朝露一般，愈加万念俱灰。"},
            {"尼采",
                "凡具有生命者，都不断的在超越自己。而人类，你们又做了什么？",
                "人类的伟大之处在于它是桥梁，而非目标。人值得被爱-在于他是横越，是下降。",
                "人们必须在心中怀着混乱，为了能够创造一个舞动的新星。",
                "儿童就是纯真和遗忘，是一种新生，是一场游戏，一个自己转动的圈圈，一种最先存在的运动，一种神圣的肯定。",
                "现在一般人都把这怜悯称为一种道德，他们对那种种了不起的不幸、丑陋和失败丝毫不懂得尊重。"},
            {"宫园薰",
                "或许前路永夜，即便如此我也要前进，因为星光即使微弱也会为我照亮前路.",
                "干燥的空气，尘埃的味道，我在其中…踏上旅途",
                "就算悲伤难抑，遍体鳞伤地处于谷底，也不能停止演奏！",
                "四月是你的谎言，我知道...是谎言就一定会有被揭穿的时候。"},
            {"冬马和纱",
                "梦里不觉秋已深，余情岂是为他人。",
                "为了我，你就永远当个废物吧",
                "为了能让我好好努力，就一生，都成为我的负担吧",
                "只是，在成为朋友的瞬间，就绝交了。",
                "为什么你会这么熟练啊！你和雪菜亲过多少次了啊！？你到底要把我甩开多远你才甘心啊！？",
                "是我，是我先，明明都是我先来的……接吻也好，拥抱也好，还是喜欢上那家伙也好"},
            {"小木曾雪菜",
                "为什么会变成这样呢…",
                "喜欢的他，不惜对我撒谎，向我转过身来。",
                "不惜舍弃自己的思念，向我补偿一切。",
                "跨越三年的感情，终于实现了。",
                "那样本应该已经满足了。",
                "只要我的心愿实现，应该就能和好了。",
                "但，为什么，会变成这样呢…"}
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
            danmuService = null;
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            danmuService = ((DanmuService.DanmuBinder)service).getService();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesMode(MODE_WORLD_READABLE);
        addPreferencesFromResource(R.xml.preferences);
        L.d();
        random = new Random();

        if (SharedPreferencesUtil.getInstence(null).getBoolean("general_enable", false)){
            Intent intent = new Intent(this, DanmuService.class);
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }

        findPreference("general_test").setOnPreferenceClickListener(this);
        findPreference("general_enable").setOnPreferenceChangeListener(this);
        findPreference("general_debug").setOnPreferenceChangeListener(this);

        findPreference("prefs_style_size").setOnPreferenceChangeListener(this);
        findPreference("prefs_style_color").setOnPreferenceChangeListener(this);
        findPreference("prefs_style_shadow").setOnPreferenceChangeListener(this);

        findPreference("about_app_version").setSummary(
                String.format("%s %s(%d)", BuildConfig.APPLICATION_ID, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()){
            case "general_test":
                String[] msg = testMsgs[random.nextInt(testMsgs.length)];
                QQMessage testMsg = new QQMessage();
                testMsg.setNickName(msg[0]);
                testMsg.setMsg(msg[random.nextInt(msg.length - 1) + 1]);
                if (danmuService != null)
                    danmuService.addDanmaku(testMsg);
                return true;
        }
        return false;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        switch (preference.getKey()){
            case "general_enable":
                Intent intent = new Intent(this, DanmuService.class);
                if ((Boolean) newValue){
                    if (danmuService == null)
                        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                }else{
                    if (danmuService != null){
                        stopService(intent);
                        danmuService = null;
                    }
                }
                return true;
            case "general_debug":
                if (danmuService!=null)
                    danmuService.showFPS((Boolean) newValue);
                    return true;
            case "prefs_style_size":
                if (danmuService!=null)
                    return danmuService.setTextSize(newValue);
            case "prefs_style_color":
                if (danmuService!=null)
                    return danmuService.setTextColor(newValue);
            case "prefs_style_shadow":
                if (danmuService!=null)
                    danmuService.textShadow = (Boolean) newValue;
        }
        return false;
    }
}
