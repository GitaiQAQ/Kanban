package me.gitai.phuckqq.data;

import me.gitai.library.utils.StringUtils;

/**
 * Created by gitai on 16-3-2.
 */
public class SessionInfo extends ReflectedObject{
	private String uin;	//a
	private int uintype;	//a
	private String phonenum;	//e
	//private int entrance;
	private String uinname;	//d
	private String troop_uin;	//b

	public SessionInfo(Object obj) {
        super(obj);
    }

    public String getUin() {
        if (!StringUtils.isEmpty(uin))return uin;
        return uin = (String)getField("a");
    }

	public int getUintype() {
        if (uintype > 0)return uintype;
        return uintype = getIntField("a");
    }

    public String getPhonenum() {
        if (!StringUtils.isEmpty(phonenum))return phonenum;
        return phonenum = (String)getField("e");
    }

	/*public int getEntrance() {
        if (entrance > 0)return entrance;
        return entrance = getIntField("a");
    }*/

    public String getUinnamer() {
        if (!StringUtils.isEmpty(uinname))return uinname;
        return uinname = (String)getField("d");
    }

    public String getTroopUin() {
        if (!StringUtils.isEmpty(troop_uin))return troop_uin;
        return troop_uin = (String)getField("b");
    }

    @Override
    public String toString() {
        return new StringBuilder("--Dump SessionInfo--")
                .append(super.toString())
                .append(",uin:").append(getUin())
                .append(",uintype:").append(getUintype())
                .append(",uinname:").append(getUinnamer())
                .append(",phonenum:").append(getPhonenum())
                .append(",troop_uin:").append(getTroopUin())
                .toString();
    }
}