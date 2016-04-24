package me.gitai.kanban.notification;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.Process;
import android.preference.PreferenceActivity;

import java.util.ArrayList;
import java.util.Random;

import me.gitai.kanban.Constant;
import me.gitai.kanban.data.QQMessage;

/**
 * Created by i@gitai.me on 16-4-23.
 */
public class MainActivity extends PreferenceActivity{
    private String[][] testMsgs = {
            {"阿库娅",
            " 汝，迷茫的家里蹲啊。不要太过自责……不想努力是世间的错，本性恶劣是环境的错，长得丑是遗传基因的错。不要责备自己，将责任推卸给他人便好……",
            "请放心，神会赦免一切。汝，要爱巨乳，汝，要爱贫乳；阿库西斯教的教义是容许一切。即使是同性恋者，即使是喜好非人兽耳少女者，即使是萝莉控，即使是尼特；只要对象并非不死者或恶魔少女，只要有爱且不犯法，全都可以得到赦免。",
            "汝，虔诚的教徒啊。为了不再受到恶魔所诱惑，记住这句咒语吧。“厄里斯的胸部是垫出来的”。今后若是你的心又受到诱惑，就记得咏唱这句咒语。若是遇见其他受到诱惑的人，告诉他们这句咒语也是好事一桩。",
            "阿库西斯教教义第七项：汝，勿要忍耐。想喝的时候就喝，想吃的时候就吃便好。因为明天并不见得还能吃得到。"},
            {"天野远子",
            "人就是切断了母亲和自己的联系，才能诞生在这个世上。如果不试着斩断过去，就无法迈向未来！",
            "有时必须破坏原有的一切，受尽伤害，才有办法了解某些事，见到某些风景，体会到某些心情。",
            "她相信着希望，相信着未来，继续翻开着故事的下一页。",
            "当夜幕降临，黑暗笼罩全身，在一片漆黑的路上独自前进，也许会感疲惫，也许会感胆怯，只要抬头仰望夜空中闪烁的星光。那清澈的光辉必定会驻留在我们心中，给予我们继续前进的勇气与力量。"},
            {"千反田爱瑠","我很好奇！"},
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
            "现在一般人都把这怜悯称为一种道德，他们对那种种了不起的不幸、丑陋和失败丝毫不懂得尊重。"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Random random = new Random();
        QQMessage testMsg = new QQMessage();
        String[] msg = testMsgs[random.nextInt(testMsgs.length)];
        testMsg.setNickName(msg[0]);
        testMsg.setMsg(msg[random.nextInt(msg.length - 1) + 1]);

        Intent intent =new Intent(this, NotificationReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.KEY_PACKAGENAME, BuildConfig.APPLICATION_ID);
        bundle.putInt(Constant.KEY_CONTACTS_COUNT, 3);
        bundle.putInt(Constant.KEY_UNREADS_COUNT, 10);
        bundle.putInt(Constant.KEY_APP_PID, Process.myPid());
        bundle.putBoolean(Constant.KEY_NEEDTICKER, false);
        bundle.putParcelable(Constant.KEY_CURRENT_MESSAGE, testMsg);
        bundle.putParcelableArrayList(Constant.KEY_MESSAGES, null);
        intent.putExtras(bundle);
        sendBroadcast(intent);
    }
}
