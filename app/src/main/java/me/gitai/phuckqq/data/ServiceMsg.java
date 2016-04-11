package me.gitai.phuckqq.data;

import me.gitai.phuckqq.util.StringUtils;
import java.util.HashMap;
import android.os.Bundle;

/**
 * Created by gitai on 16-3-12.
 */
public class ServiceMsg extends ReflectedObject{
    private String tag = "ToServiceMsg";// ToServiceMsg || FromServiceMsg merge sign
    //public IBaseActionListener actionListener;
    private int appId;
    private int appSeq = -1;
    public HashMap attributes = new HashMap();
    public Bundle extraData = new Bundle();
    //private MsfCommand msfCommand = MsfCommand.unknown; //import com.tencent.mobileqq.msf.sdk.MsfCommand;
    private String serviceCmd;
    private int ssoSeq = -1;
    private String uin;
    private byte[] wupBuffer = new byte[0];

    // FromServiceMsg only
    private String errorMsg = "";
    private int flag;
    private byte fromVersion = 1;
    private byte[] msgCookie = new byte[0];
    private int resultCode;

    // ToServiceMsg only
    private boolean needResp = true;
    private long sendTimeout = -1L;
    private String serviceName;
    private long timeout = -1L;
    private byte toVersion = 1;
    private byte uinType = 0;

    public ServiceMsg(Object obj) {
        super(obj);
        if(this.tag.equals("ToServiceMsg")) {
            extraData.putByte("version", toVersion);
        }else{
            extraData.putByte("version", fromVersion);
        }
    }

    public ServiceMsg(String tag,Object obj) {
        super(obj);
        if(!StringUtils.isEmpty(tag))this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public int getAppId() {
        if (appId > 0)return appId;
        return appId = getIntField("appId");
    }

    public int getAppSeq() {
        if (appSeq > -1)return appSeq;
        return appSeq = getIntField("appSeq");
    }

    public HashMap getAttributes() {
        if (attributes != null && attributes.size() > 0)return attributes;
        return attributes = (HashMap)getField("attributes");
    }

    public Bundle getExtraData() {
        if (extraData != null)return extraData;
        return extraData = (Bundle)getField("extraData");
    }

    /*public MsfCommand getMsfCommand() {
        if (msfCommand != null)return msfCommand;
        return msfCommand = (MsfCommand)getField("msfCommand");
    }*/

    public String getServiceCmd() {
        if (!StringUtils.isEmpty(serviceCmd))return serviceCmd;
        return serviceCmd = (String)getField("serviceCmd");
    }

    public int getSsoSeq() {
        if (ssoSeq > -1)return ssoSeq;
        return ssoSeq = getIntField("ssoSeq");
    }

    public String getUin() {
        if (!StringUtils.isEmpty(uin))return uin;
        return uin = (String)getField("uin");
    }

    public byte[] getWupBuffer() {
        if (wupBuffer != null)return wupBuffer;
        return wupBuffer = (byte[])getField("wupBuffer");
    }

    public void putWupBuffer(byte[] paramArrayOfByte) {
        wupBuffer = paramArrayOfByte;
    }

    // FromServiceMsg only
    public String getErrorMsg() {
        if (!StringUtils.isEmpty(errorMsg))return errorMsg;
        return errorMsg = (String)getField("errorMsg");
    }

    public int getFlag() {
        if (flag > 0)return flag;
        return flag = getIntField("flag");
    }

    public byte getFromVersion() {
        if (fromVersion != 1)return fromVersion;
        return fromVersion = getByteField("fromVersion");
    }

    public byte[] getMsgCookie() {
        if (msgCookie != null)return msgCookie;
        return msgCookie = (byte[])getField("msgCookie");
    }

    public int getResultCode() {
        if (resultCode > 0)return resultCode;
        return resultCode = getIntField("resultCode");
    }

    // ToServiceMsg only
    public boolean isNeedResp() {
        if (needResp)return needResp;
        return needResp = getBooleanField("needResp");
    }

    public long getSendTimeout() {
        if (sendTimeout > 0)return sendTimeout;
        return sendTimeout = getLongField("sendTimeout");
    }

    public String getServiceName() {
        if (!StringUtils.isEmpty(serviceName))return serviceName;
        return serviceName = (String)getField("serviceName");
    }

    public long getTimeout() {
        if (timeout > 0)return timeout;
        return timeout = getLongField("timeout");
    }

    public byte getToVersion() {
        if (toVersion != 1)return toVersion;
        return toVersion = getByteField("toVersion");
    }

    public byte getUinType() {
        if (uinType != 1)return uinType;
        return uinType = getByteField("uinType");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("--Dump " + getTag() + "--")
                .append(",appId:").append(getAppId())
                .append(",appSeq:").append(getAppSeq())
                .append(",attributes:").append(getAttributes())
                .append(",extraData:").append(getExtraData())
                //.append(",msfCommand:").append(getMsfCommand())
                .append(",serviceCmd:").append(getServiceCmd())
                .append(",ssoSeq:").append(getSsoSeq())
                .append(",uin:").append(getUin())
                .append(",wupBuffer:").append(getWupBuffer());

        // FromServiceMsg only
        if ("FromServiceMsg".equals(getTag())) {
            sb.append(",errorMsg:").append(getErrorMsg())
                .append(",flag:").append(getFlag())
                .append(",fromVersion:").append(getFromVersion())
                .append(",msgCookie:").append(getMsgCookie())
                .append(",resultCode:").append(getResultCode());
        }

        // ToServiceMsg only
        if ("ToServiceMsg".equals(getTag())) {
            sb.append(",needResp:").append(isNeedResp())
                .append(",sendTimeout:").append(getSendTimeout())
                .append(",serviceName:").append(getServiceName())
                .append(",timeout:").append(getTimeout())
                .append(",toVersion:").append(getToVersion())
                .append(",uinType:").append(getUinType());
        }

        return sb.toString();
    }

}
