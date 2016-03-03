package me.gitai.phuckqq.data;

import me.gitai.phuckqq.util.StringUtils;

/**
 * Created by dphdjy on 16-3-2.
 */
public class Message extends MessageRecord{
    private String actMsgContentValue;
    private String action = null;
    private int bizType = -1;
    private int counter = 0;
    private CharSequence emoRecentMsg;
    private long fileSize = -1;
    private int fileType = -1;
    private boolean hasReply;

    private boolean isCacheValid = true;
    //private Boolean isInWhisper = false;
    private String latestNormalMsgString;
    private String nickName = null;
    private String pttUrl;
    private long shareAppID;

    private int unReadNum;

    public Message(Object obj) {
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
        return actMsgContentValue = (String)getField("actMsgContentValue");
    }

    public String getAction() {
        if (!StringUtils.isEmpty(action))return action;
        return action = (String)getField("action");
    }

    public int getBizType() {
        if (bizType > 0)return bizType;
        return bizType = getIntField("bizType");
    }

    public int getCounter() {
        if (counter > 0)return counter;
        return counter = getIntField("counter");
    }

    public CharSequence getEmoRecentMsg() {
        if (!StringUtils.isEmpty(emoRecentMsg))return emoRecentMsg;
        return emoRecentMsg = (CharSequence)getField("emoRecentMsg");
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

    /*public Boolean getIsInWhisper() {
        if (isInWhisper)return isInWhisper;
        return isInWhisper = getBooleanField("isInWhisper");
    }*/

    public String getLatestNormalMsgString() {
        if (!StringUtils.isEmpty(latestNormalMsgString))return latestNormalMsgString;
        return latestNormalMsgString = (String)getField("latestNormalMsgString");
    }

    public String getNickName() {
        if (!StringUtils.isEmpty(nickName))return nickName;
        return nickName = (String)getField("nickName");
    }

    public String getPttUrl() {
        if (!StringUtils.isEmpty(pttUrl))return pttUrl;
        return pttUrl = (String)getField("pttUrl");
    }

    public long getShareAppID() {
        if (shareAppID > 0)return shareAppID;
        return shareAppID = getLongField("shareAppID");
    }

    public int getUnReadNum() {
        if (unReadNum > 0)return unReadNum;
        return unReadNum = getIntField("unReadNum");
    }

    @Override
    public String toString() {
        return new StringBuilder("--Dump Message--,")
                .append(",actMsgContentValue:").append(getActMsgContentValue())
                .append(",action:").append(getAction())
                .append(",bizType:").append(getBizType())
                .append(",counter:").append(getCounter())
                .append(",emoRecentMsg:").append(getEmoRecentMsg())
                .append(",fileSize:").append(getFileSize())
                .append(",fileType:").append(getFileType())
                .append(",hasReply:").append(isHasReply())
                .append(",isCacheValid:").append(isCacheValid())
                //.append(",isInWhisper:").append(getIsInWhisper())
                .append(",latestNormalMsgString:").append(getLatestNormalMsgString())
                .append(",nickName:").append(getNickName())
                .append(",pttUrl:").append(getPttUrl())
                .append(",shareAppID:").append(getShareAppID())
                .append(",unReadNum:").append(getUnReadNum())
                .toString();
    }

}
