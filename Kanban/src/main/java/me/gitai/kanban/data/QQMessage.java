package me.gitai.kanban.data;

import android.os.Parcel;

import me.gitai.library.utils.StringUtils;

/**
 * Created by gitai on 16-3-2.
 */
//com.tencent.mobileqq.app.message.QQMessageFacade$QQMessage
public class QQMessage extends MessageRecord{
    protected String actMsgContentValue = null;
    protected String action = null;
    protected int bizType = -1;
    protected int counter = -1; //unread message count
    protected String emoRecentMsg = null;
    protected long fileSize = -1;
    protected int fileType = -1;
    protected boolean hasReply = false;

    protected boolean isCacheValid = true;
    protected boolean isInWhisper = false;
    protected String latestNormalMsgString = null;
    protected String nickName = null;
    protected String pttUrl = null;
    protected long shareAppID = -1;

    protected int unReadNum = -1;

    private String summary = null;

    public QQMessage() {}

    public QQMessage(Object obj) {
        super(obj);
    }

    public CharSequence getMessageText() {
        if (getEmoRecentMsg() == null) {
            return getMsg();
        }
        return getEmoRecentMsg();
    }

    public String getActMsgContentValue() {
        if (!StringUtils.isEmpty(actMsgContentValue))return actMsgContentValue;
        return actMsgContentValue = getStringField("actMsgContentValue");
    }

    public String getAction() {
        if (!StringUtils.isEmpty(action))return action;
        return action = getStringField("action");
    }

    public int getBizType() {
        if (bizType > 0)return bizType;
        return bizType = getIntField("bizType");
    }

    public int getCounter() {
        if (counter > 0)return counter;
        return counter = getIntField("counter");
    }

    public String getEmoRecentMsg() {
        /*if (!StringUtils.isEmpty(emoRecentMsg))return emoRecentMsg;
        return emoRecentMsg = getStringField("emoRecentMsg");*/
        return null;
    }

    public long getFileSize() {
        if (fileSize > 0)return fileSize;
        return fileSize = getLongField("fileSize");
    }

    public int getFileType() {
        if (fileType > 0)return fileType;
        return fileType = getIntField("fileType");
    }

    public boolean isHasReply() {
        if (hasReply)return hasReply;
        return hasReply = getBooleanField("hasReply");
    }

    public boolean isCacheValid() {
        if (isCacheValid)return isCacheValid;
        return isCacheValid = getBooleanField("isCacheValid");
    }

    public boolean isInWhisper() {
        if (isInWhisper)return isInWhisper;
        return isInWhisper = (getField("isInWhisper") == null)?false:(Boolean)getField("isInWhisper");
    }

    public String getLatestNormalMsgString() {
        if (!StringUtils.isEmpty(latestNormalMsgString))return latestNormalMsgString;
        return latestNormalMsgString = getStringField("latestNormalMsgString");
    }

    public String getNickName() {
        if (!StringUtils.isEmpty(nickName))return nickName;
        return nickName = getStringField("nickName");
    }

    public String getPttUrl() {
        if (!StringUtils.isEmpty(pttUrl))return pttUrl;
        return pttUrl = getStringField("pttUrl");
    }

    public long getShareAppID() {
        if (shareAppID > 0)return shareAppID;
        return shareAppID = getLongField("shareAppID");
    }

    public int getUnReadNum() {
        if (unReadNum > 0)return unReadNum;
        return unReadNum = getIntField("unReadNum");
    }

    public void setActMsgContentValue(String actMsgContentValue) {
        this.actMsgContentValue = actMsgContentValue;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setBizType(int bizType) {
        this.bizType = bizType;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setEmoRecentMsg(String emoRecentMsg) {
        this.emoRecentMsg = emoRecentMsg;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public void setHasReply(boolean hasReply) {
        this.hasReply = hasReply;
    }

    public void setCacheValid(boolean cacheValid) {
        isCacheValid = cacheValid;
    }

    public void setInWhisper(boolean inWhisper) {
        isInWhisper = inWhisper;
    }

    public void setLatestNormalMsgString(String latestNormalMsgString) {
        this.latestNormalMsgString = latestNormalMsgString;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPttUrl(String pttUrl) {
        this.pttUrl = pttUrl;
    }

    public void setShareAppID(long shareAppID) {
        this.shareAppID = shareAppID;
    }

    public void setUnReadNum(int unReadNum) {
        this.unReadNum = unReadNum;
    }

    public String getSummary(){
        if (!StringUtils.isEmpty(summary)) return summary;
        return summary = ((nickName!=null&&nickName.length()<=8)?nickName:(nickName!=null?(nickName.substring(0, 6) + "..."):senderuin)) + ":" + getMessageText();
    }

    @Override
    public String toString() {
        return new StringBuilder("--Dump QQMessage--")
                .append(",actMsgContentValue:").append(getActMsgContentValue())
                .append(",action:").append(getAction())
                .append(",bizType:").append(getBizType())
                .append(",counter:").append(getCounter())
                .append(",emoRecentMsg:").append(getEmoRecentMsg())
                .append(",fileSize:").append(getFileSize())
                .append(",fileType:").append(getFileType())
                .append(",hasReply:").append(isHasReply())
                .append(",isCacheValid:").append(isCacheValid())
                .append(",isInWhisper:").append(isInWhisper())
                .append(",latestNormalMsgString:").append(getLatestNormalMsgString())
                .append(",nickName:").append(getNickName())
                .append(",pttUrl:").append(getPttUrl())
                .append(",shareAppID:").append(getShareAppID())
                .append(",unReadNum:").append(getUnReadNum())
                .append(",").append(super.toString())
                .toString();
    }

    @Override
    public int describeContents() {
        return super.describeContents();
    }

    public QQMessage(Parcel in) {
        super(in);
        actMsgContentValue = in.readString();
        action = in.readString();
        bizType = in.readInt();
        counter = in.readInt();
        //emoRecentMsg = in.readString();
        fileSize = in.readLong();
        fileType = in.readInt();
        hasReply = in.readByte() != 0;

        isCacheValid = in.readByte() != 0;
        isInWhisper = in.readByte() != 0;
        latestNormalMsgString = in.readString();
        nickName = in.readString();
        pttUrl = in.readString();
        shareAppID = in.readLong();

        unReadNum = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(getActMsgContentValue());
        dest.writeString(getAction());
        dest.writeInt(getBizType());
        dest.writeInt(getCounter());
        //dest.writeString(getEmoRecentMsg());
        dest.writeLong(getFileSize());
        dest.writeInt(getFileType());
        dest.writeByte((byte)(isHasReply()?1:0));

        dest.writeByte((byte)(isCacheValid()?1:0));
        dest.writeByte((byte)(isInWhisper()?1:0));
        dest.writeString(getLatestNormalMsgString());
        dest.writeString(getNickName());
        dest.writeString(getPttUrl());
        dest.writeLong(getShareAppID());

        dest.writeInt(getUnReadNum());
    }

    public static final Creator<QQMessage> CREATOR = new Creator<QQMessage>() {
        @Override
        public QQMessage createFromParcel(Parcel source) {
            return new QQMessage(source);
        }

        @Override
        public QQMessage[] newArray(int size) {
            return new QQMessage[size];
        }
    };
}
