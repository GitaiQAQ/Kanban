package me.gitai.phuckqq.data;

import android.os.Parcel;

import me.gitai.library.utils.StringUtils;

/**
 * Created by gitai on 16-3-2.
 */
public class MessageRecord extends ReflectedObject{
    public static final int EXTRA_STREAM_PTT_FLAG = 10001;
    public static final int MIN_VERSION_CODE_SUPPORT_IMAGE_MD5_TRANS = 2;
    public static final int MSG_TYPE_0x7F = -2006;
    public static final int MSG_TYPE_ACTIVITY = -4002;
    public static final int MSG_TYPE_AUTHORIZE_FAILED = -4005;
    public static final int MSG_TYPE_AUTOREPLY = -10000;
    public static final int MSG_TYPE_C2C_CHAT_FREQ_CALL_TIP = -1014;
    public static final int MSG_TYPE_C2C_KEYWORD_CALL_TIP = -1015;
    public static final int MSG_TYPE_C2C_MIXED = -30002;
    public static final int MSG_TYPE_DISCUSS_PUSH = -1004;
    public static final int MSG_TYPE_DISC_CREATE_CALL_TIP = -1016;
    public static final int MSG_TYPE_DISC_PTT_FREQ_CALL_TIP = -1017;
    public static final int MSG_TYPE_ENTER_TROOP = -4003;
    public static final int MSG_TYPE_FAILED_MSG = -2013;
    public static final int MSG_TYPE_FILE_RECEIPT = -3008;
    public static final int MSG_TYPE_FORWARD_IMAGE = -20000;
    public static final int MSG_TYPE_GAME_INVITE = -3004;
    public static final int MSG_TYPE_GAME_SHARE = -3005;
    public static final int MSG_TYPE_GROUPDISC_FILE = -2014;
    public static final int MSG_TYPE_LOCAL_COMMON = -4000;
    public static final int MSG_TYPE_LOCAL_URL = -4001;
    public static final int MSG_TYPE_LONG_MIX = -1036;
    public static final int MSG_TYPE_LONG_TEXT = -1037;
    public static final int MSG_TYPE_MEDIA_EMO = -2001;
    public static final int MSG_TYPE_MEDIA_FILE = -2005;
    public static final int MSG_TYPE_MEDIA_FUNNY_FACE = -2010;
    public static final int MSG_TYPE_MEDIA_MARKFACE = -2007;
    public static final int MSG_TYPE_MEDIA_MULTI09 = -2003;
    public static final int MSG_TYPE_MEDIA_MULTI513 = -2004;
    public static final int MSG_TYPE_MEDIA_PIC = -2000;
    public static final int MSG_TYPE_MEDIA_PTT = -2002;
    public static final int MSG_TYPE_MEDIA_SECRETFILE = -2008;
    public static final int MSG_TYPE_MEDIA_VIDEO = -2009;
    public static final int MSG_TYPE_MIX = -1035;
    public static final int MSG_TYPE_MULTI_TEXT_VIDEO = -4008;
    public static final int MSG_TYPE_MULTI_VIDEO = -2016;
    public static final int MSG_TYPE_MY_ENTER_TROOP = -4004;
    public static final int MSG_TYPE_NEW_FRIEND_TIPS = -1013;
    public static final int MSG_TYPE_NULL = -999;
    public static final int MSG_TYPE_ONLINE_FILE_REQ = -3007;
    public static final int MSG_TYPE_OPERATE_TIPS = -1041;
    public static final int MSG_TYPE_PC_PUSH = -3001;
    public static final int MSG_TYPE_PIC_AND_TEXT_MIXED = -3000;
    public static final int MSG_TYPE_PIC_QSECRETARY = -1032;
    public static final int MSG_TYPE_PLAY_TOGETHER_RESULT = -1038;
    public static final int MSG_TYPE_PTT_QSECRETARY = -1031;
    public static final int MSG_TYPE_PUBLIC_ACCOUNT = -3006;
    public static final int MSG_TYPE_QLINK_AP_CREATE_SUC_TIPS = -3011;
    public static final int MSG_TYPE_QLINK_FILE_TIPS = -3009;
    public static final int MSG_TYPE_QLINK_SEND_FILE_TIPS = -3010;
    public static final int MSG_TYPE_QZONE_NEWEST_FEED = -2015;
    public static final int MSG_TYPE_SHAKE_WINDOW = -2020;
    public static final int MSG_TYPE_SHIELD_MSG = -2012;
    public static final int MSG_TYPE_SINGLE_WAY_FRIEND_MSG = -2019;
    public static final int MSG_TYPE_STRUCT_MSG = -2011;
    public static final int MSG_TYPE_STRUCT_TROOP_NOTIFICATION = -2021;
    public static final int MSG_TYPE_SYSTEM_STRUCT_MSG = -2018;
    public static final int MSG_TYPE_TEXT = -1000;
    public static final int MSG_TYPE_TEXT_FRIEND_FEED = -1034;
    public static final int MSG_TYPE_TEXT_GROUPMAN_ACCEPT = -1021;
    public static final int MSG_TYPE_TEXT_GROUPMAN_ADDREQUEST = -1020;
    public static final int MSG_TYPE_TEXT_GROUPMAN_INVITE = -1023;
    public static final int MSG_TYPE_TEXT_GROUPMAN_REFUSE = -1022;
    public static final int MSG_TYPE_TEXT_QSECRETARY = -1003;
    public static final int MSG_TYPE_TEXT_RECOMMEND_CIRCLE = -1033;
    public static final int MSG_TYPE_TEXT_RECOMMEND_CONTACT = -1030;
    public static final int MSG_TYPE_TEXT_RECOMMEND_TROOP = -1039;
    public static final int MSG_TYPE_TEXT_RECOMMEND_TROOP_BUSINESS = -1040;
    public static final int MSG_TYPE_TEXT_SAFE = -1002;
    public static final int MSG_TYPE_TEXT_SYSTEM_ACCEPT = -1008;
    public static final int MSG_TYPE_TEXT_SYSTEM_ACCEPTANDADD = -1007;
    public static final int MSG_TYPE_TEXT_SYSTEM_ADDREQUEST = -1006;
    public static final int MSG_TYPE_TEXT_SYSTEM_ADDSUCCESS = -1010;
    public static final int MSG_TYPE_TEXT_SYSTEM_OLD_VERSION_ADDREQUEST = -1011;
    public static final int MSG_TYPE_TEXT_SYSTEM_REFUSE = -1009;
    public static final int MSG_TYPE_TEXT_VIDEO = -1001;
    public static final int MSG_TYPE_TROOP_MIXED = -30003;
    public static final int MSG_TYPE_TROOP_OBJ_MSG = -2017;
    public static final int MSG_TYPE_TROOP_TIPS_ADD_MEMBER = -1012;
    public static final int MSG_TYPE_TROOP_UNREAD_TIPS = -4009;
    public static final int MSG_VERSION_CODE = 3;
    public static final int MSG_VERSION_CODE_FOR_PICPTT = 3;
    public static final String QUERY_NEW_TABLE_FIELDS = "_id, extraflag, frienduin, isread, issend, istroop, NULL as msg, msgData, msgId, msgseq, msgtype, selfuin, senderuin, shmsgseq, time, versionCode, longMsgIndex, longMsgId, longMsgCount, isValid, msgUid, vipBubbleID, uniseq, sendFailCode, extStr, extInt, extLong";
    public static final String QUERY_OLD_TABLE_FIELDS = "_id, extraflag, frienduin, isread, issend, istroop, msg, NULL as msgData, msgId, msgseq, msgtype, selfuin, senderuin, shmsgseq, time, 0 as versionCode, NULL as longMsgIndex, NULL as longMsgId, NULL as longMsgCount, 1 as isValid, NULL as msgUid, NULL as vipBubbleID, 0 as uniseq, 0 as sendFailCode, NULL as extStr, 0 as extInt, 0 as extLong";
    public static final String[] QUERY_OLD_TABLE_FIELDS_ARRAY = new String[]{"_id", "extraflag", "frienduin", "isread", "issend", "istroop", "msg", "msgId", "msgseq", "msgtype", "selfuin", "senderuin", "shmsgseq", "time"};
    public static final int SEND_FAIL_CODE_DEFAULT = 0;

    protected int extInt = -1;
    protected int extLong = -1;
    protected String extStr = null;
    protected int extraflag = -1;
    protected String frienduin = null;
    protected boolean isValid = true;
    protected boolean isread = false;
    protected int issend = -1;
    protected int istroop = -1;
    protected int longMsgCount = -1;
    protected int longMsgId = -1;
    protected int longMsgIndex = -1;

    protected String msg = null;
    protected byte[] msgData = null;

    protected long msgId = -1;
    protected long msgUid = -1;
    protected long msgseq = -1;
    protected int msgtype = -1;
    protected String selfuin=null;
    protected int sendFailCode = -1;
    protected String senderuin=null;
    protected long shmsgseq = -1;
    protected long time = -1;
    protected long uniseq = -1;
    protected int versionCode = 3;
    protected long vipBubbleID = -1;

    public MessageRecord() {}

    public MessageRecord(Object obj) {
        super(obj);
    }

    public int getExtInt() {
        if (extInt > 0)return extInt;
        return extInt = getIntField("extInt");
    }

    public int getExtLong() {
        if (extLong > 0)return extLong;
        return extLong = getIntField("extLong");
    }

    public String getExtStr() {
        if (!StringUtils.isEmpty(extStr))return extStr;
        return extStr = getStringField("extStr");
    }

    public int getExtraflag() {
        if (extraflag > 0)return extraflag;
        return extraflag = getIntField("extraflag");
    }

    public String getFrienduin() {
        if (!StringUtils.isEmpty(frienduin))return frienduin;
        return frienduin = getStringField("frienduin");
    }

    public boolean isValid() {
        if (isValid)return isValid;
        return isValid = getBooleanField("isValid");
    }

    public boolean isread() {
        if (isread)return isread;
        return isread = getBooleanField("isread");
    }

    public int getIssend() {
        if (issend > 0)return issend;
        return issend = getIntField("issend");
    }

    public int getIstroop() {
        if (istroop > 0)return istroop;
        return istroop = getIntField("istroop");
    }

    public int getLongMsgCount() {
        if (longMsgCount > 0)return longMsgCount;
        return longMsgCount = getIntField("longMsgCount");
    }

    public boolean isLongMsg() {
        if (this.longMsgCount > 1) {
            return true;
        }
        return false;
    }

    public int getLongMsgId() {
        if (longMsgId > 0)return longMsgId;
        return longMsgId = getIntField("longMsgId");
    }

    public int getLongMsgIndex() {
        if (longMsgIndex > 0)return longMsgIndex;
        return longMsgIndex = getIntField("longMsgIndex");
    }

    public String getMsg() {
        if (!StringUtils.isEmpty(msg))return msg;
        return msg = getStringField("msg");
    }

    public byte[] getMsgData() {
        if (msgData != null && msgData.length > 0)return msgData;
        return msgData = (byte[])getField("msgData");
    }

    public long getMsgId() {
        if (msgId > 0) return msgId;
        return msgId = getLongField("msgId");
    }

    public long getMsgUid() {
        if (msgUid > 0) return msgUid;
        return msgUid = getLongField("msgUid");
    }

    public long getMsgseq() {
        if (msgseq > 0) return msgseq;
        return msgseq = getLongField("msgseq");
    }

    public int getMsgtype() {
        if (msgtype > 0) return msgtype;
        return msgtype = getIntField("msgtype");
    }

    public String getSelfuin() {
        if (!StringUtils.isEmpty(selfuin))return selfuin;
        return selfuin = getStringField("selfuin");
    }

    public int getSendFailCode() {
        if (sendFailCode > 0) return sendFailCode;
        return sendFailCode = getIntField("sendFailCode");
    }

    public String getSenderuin() {
        if (!StringUtils.isEmpty(senderuin))return senderuin;
        return senderuin = getStringField("senderuin");
    }

    public long getShmsgseq() {
        if (shmsgseq > 0) return shmsgseq;
        return shmsgseq = getLongField("shmsgseq");
    }

    public long getTime() {
        if (time > 0) return time;
        return time = getLongField("time");
    }

    public long getUniseq() {
        if (uniseq > 0) return uniseq;
        return uniseq = getLongField("uniseq");
    }

    public int getVersionCode() {
        if (versionCode > 0) return versionCode;
        return versionCode = getIntField("versionCode");
    }

    public long getVipBubbleID() {
        if (vipBubbleID > 0) return vipBubbleID;
        return vipBubbleID = getLongField("vipBubbleID");
    }



    public void setExtInt(int extInt) {
        this.extInt = extInt;
    }

    public void setExtLong(int extLong) {
        this.extLong = extLong;
    }

    public void setExtStr(String extStr) {
        this.extStr = extStr;
    }

    public void setExtraflag(int extraflag) {
        this.extraflag = extraflag;
    }

    public void setFrienduin(String frienduin) {
        this.frienduin = frienduin;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public void setIsread(boolean isread) {
        this.isread = isread;
    }

    public void setIssend(int issend) {
        this.issend = issend;
    }

    public void setIstroop(int istroop) {
        this.istroop = istroop;
    }

    public void setLongMsgCount(int longMsgCount) {
        this.longMsgCount = longMsgCount;
    }

    public void setLongMsgId(int longMsgId) {
        this.longMsgId = longMsgId;
    }

    public void setLongMsgIndex(int longMsgIndex) {
        this.longMsgIndex = longMsgIndex;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setMsgData(byte[] msgData) {
        this.msgData = msgData;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }

    public void setMsgUid(long msgUid) {
        this.msgUid = msgUid;
    }

    public void setMsgseq(long msgseq) {
        this.msgseq = msgseq;
    }

    public void setMsgtype(int msgtype) {
        this.msgtype = msgtype;
    }

    public void setSelfuin(String selfuin) {
        this.selfuin = selfuin;
    }

    public void setSendFailCode(int sendFailCode) {
        this.sendFailCode = sendFailCode;
    }

    public void setSenderuin(String senderuin) {
        this.senderuin = senderuin;
    }

    public void setShmsgseq(long shmsgseq) {
        this.shmsgseq = shmsgseq;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setUniseq(long uniseq) {
        this.uniseq = uniseq;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public void setVipBubbleID(long vipBubbleID) {
        this.vipBubbleID = vipBubbleID;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("--Dump MessageRecord--")
                .append(",selfUin:").append(getSelfuin())
                .append(",friendUin:").append(getFrienduin())
                .append(",senderUin:").append(getSenderuin())
                .append(",shmsgseq:").append(getShmsgseq())
                .append(",uid:").append(getMsgUid())
                .append(",time:").append(getTime())
                .append(",isRead:").append(isread())
                .append(",isSend:").append(getIssend())
                .append(",extraFlag:").append(getExtraflag())
                .append(",sendFailCode:").append(getSendFailCode())
                .append(",istroop:").append(getIstroop())
                .append(",msgType:").append(getMsgtype())
                .append(",msg:").append(getMsg())
                .append(",bubbleid:").append(getVipBubbleID())
                .append(",uniseq:").append(getUniseq());
        if (isLongMsg()) {
            stringBuilder.append(",longMsgId:").append(getLongMsgId())
                    .append(",longMsgCount:").append(getLongMsgCount())
                    .append(",longMsgIndex:").append(getLongMsgIndex());
        }
        return stringBuilder.append(",").append(super.toString()).toString();
    }

    @Override
    public int describeContents() {
        return super.describeContents();
    }

    public MessageRecord(Parcel in) {
        super(in);
        extInt = in.readInt();
        extLong = in.readInt();
        extStr = in.readString();
        extraflag = in.readInt();
        frienduin = in.readString();
        isValid = in.readByte() != 0;
        isread = in.readByte() != 0;
        issend = in.readInt();
        istroop = in.readInt();
        longMsgCount = in.readInt();
        longMsgId = in.readInt();
        longMsgIndex = in.readInt();

        msg = in.readString();
        //in.readByteArray(msgData);

        msgId = in.readLong();
        msgUid = in.readLong();
        msgseq = in.readLong();
        msgtype = in.readInt();
        selfuin = in.readString();
        sendFailCode = in.readInt();
        senderuin = in.readString();
        shmsgseq = in.readLong();
        time = in.readLong();
        uniseq = in.readLong();
        versionCode = in.readInt();
        vipBubbleID = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(getExtInt());
        dest.writeLong(getExtLong());
        dest.writeString(getExtStr());
        dest.writeInt(getExtraflag());
        dest.writeString(getFrienduin());
        dest.writeByte((byte)(isValid()?1:0));
        dest.writeByte((byte)(isread()?1:0));
        dest.writeInt(getIssend());
        dest.writeInt(getIstroop());
        dest.writeInt(getLongMsgCount());
        dest.writeInt(getLongMsgId());
        dest.writeInt(getLongMsgIndex());

        dest.writeString(getMsg());
        //dest.writeByteArray(getMsgData());

        dest.writeLong(getMsgId());
        dest.writeLong(getMsgUid());
        dest.writeLong(getMsgseq());
        dest.writeInt(getMsgtype());
        dest.writeString(getSelfuin());
        dest.writeInt(getSendFailCode());
        dest.writeString(getSenderuin());
        dest.writeLong(getShmsgseq());
        dest.writeLong(getTime());
        dest.writeLong(getUniseq());
        dest.writeInt(getVersionCode());
        dest.writeLong(getVipBubbleID());
    }

    public static final Creator<MessageRecord> CREATOR = new Creator<MessageRecord>() {
        @Override
        public MessageRecord createFromParcel(Parcel source) {
            return new MessageRecord(source);
        }

        @Override
        public MessageRecord[] newArray(int size) {
            return new MessageRecord[size];
        }
    };
}
