package me.gitai.phuckqq.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gitai on 16-3-2.
 */
public class ReflectedObject implements Parcelable{
    public static final int DETACHED = 1002;
    public static final int MANAGED = 1001;
    public static final int NEW = 1000;
    public static final int REMOVED = 1003;
    protected long _id = -1;
    protected int _status = 1000;

    private Object mObject = null;

    private boolean inXposed = false;

    public ReflectedObject() {}

    public ReflectedObject(Object mObject) {
        this.inXposed = true;
        this.mObject = mObject;
    }

    public Object getObject() {
        return mObject;
    }

    public String getStringField(String fieldName){
        if (!inXposed) return null;
        Object obj = de.robv.android.xposed.XposedHelpers.getObjectField(mObject, fieldName);
        return (obj == null ? null: new StringBuilder((String)obj).toString());
    }

    public Object getField(String fieldName){
        if (!inXposed) return null;
        return de.robv.android.xposed.XposedHelpers.getObjectField(mObject, fieldName);
    }

    public boolean getBooleanField(String fieldName){
        if (!inXposed) return false;
        return de.robv.android.xposed.XposedHelpers.getBooleanField(mObject, fieldName);
    }

    public byte getByteField(String fieldName){
        if (!inXposed) return 0;
        return de.robv.android.xposed.XposedHelpers.getByteField(mObject, fieldName);
    }

    public char getCharField(String fieldName){
        if (!inXposed) return '\u0000';
        return de.robv.android.xposed.XposedHelpers.getCharField(mObject, fieldName);
    }

    public double getDoubleField(String fieldName){
        if (!inXposed) return 0;
        return de.robv.android.xposed.XposedHelpers.getDoubleField(mObject, fieldName);
    }

    public float getFloatField(String fieldName){
        if (!inXposed) return 0;
        return de.robv.android.xposed.XposedHelpers.getFloatField(mObject, fieldName);
    }

    public int getIntField(String fieldName){
        if (!inXposed) return 0;
        return de.robv.android.xposed.XposedHelpers.getIntField(mObject, fieldName);
    }

    public long getLongField(String fieldName){
        if (!inXposed) return 0;
        return de.robv.android.xposed.XposedHelpers.getLongField(mObject, fieldName);
    }

    public short getShortField(String fieldName){
        if (!inXposed) return 0;
        return de.robv.android.xposed.XposedHelpers.getShortField(mObject, fieldName);
    }

    public long getId() {
    	    if (_id > 0) return _id;
        return _id = getLongField("_id");
    }

    public int getStatus() {
        if (_status == 1000) return _status;
        return _status = getIntField("_status");
    }

    public String getStatusMsg() {
    		switch (getStatus()) {
            case 1000: {
                return "NEW";
            }
            case 1001: {
                return "MANAGED";
            }
            case 1002: {
                return "DETACHED";
            }
            case 1003: {
                return "REMOVED";
            }
        }
        return null;
    }

    public void setId(long l) {
        this._id = l;
    }

    public void setStatus(int n) {
        this._status = n;
    }

    @Override
    public String toString() {
    		StringBuilder stringBuilder = new StringBuilder("--Dump ReflectedObject--");
        	stringBuilder
        	.append(",_id:").append(getId())
        	.append(",_status:").append(getStatusMsg());
        return stringBuilder.toString();
    }

    @Override
    public int describeContents() {
         return 0;
    }

    public ReflectedObject(Parcel in){
    		setId(in.readLong());
    		setStatus(in.readInt());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    		dest.writeLong(getId());
    		dest.writeInt(getStatus());
    }

    public static final Creator<ReflectedObject> CREATOR = new Creator<ReflectedObject>() {
        @Override
        public ReflectedObject createFromParcel(Parcel source) {
            return new ReflectedObject(source);
        }

        @Override
        public ReflectedObject[] newArray(int size) {
            return new ReflectedObject[size];
        }
    };
}
