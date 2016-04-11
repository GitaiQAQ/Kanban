package me.gitai.phuckqq.data;

import android.os.Parcelable;
import android.os.Parcel;
import de.robv.android.xposed.XposedHelpers;

/**
 * Created by dphdjy on 16-3-2.
 */
public class ReflectedObject implements Parcelable{
    public static final int DETACHED = 1002;
    public static final int MANAGED = 1001;
    public static final int NEW = 1000;
    public static final int REMOVED = 1003;
    private long _id = -1;
    private int _status = 1000;

    private Object mObject = null;

    public ReflectedObject(Object mObject) {
        this.mObject = mObject;
    }

    public Object getObject() {
        return mObject;
    }

    public Object getField(String fieldName){
        return XposedHelpers.getObjectField(mObject, fieldName);
    }

    public boolean getBooleanField(String fieldName){
        return XposedHelpers.getBooleanField(mObject, fieldName);
    }

    public byte getByteField(String fieldName){
        return XposedHelpers.getByteField(mObject, fieldName);
    }

    public char getCharField(String fieldName){
        return XposedHelpers.getCharField(mObject, fieldName);
    }

    public double getDoubleField(String fieldName){
        return XposedHelpers.getDoubleField(mObject, fieldName);
    }

    public float getFloatField(String fieldName){
        return XposedHelpers.getFloatField(mObject, fieldName);
    }

    public int getIntField(String fieldName){
        return XposedHelpers.getIntField(mObject, fieldName);
    }

    public long getLongField(String fieldName){
        return XposedHelpers.getLongField(mObject, fieldName);
    }

    public short getShortField(String fieldName){
        return XposedHelpers.getShortField(mObject, fieldName);
    }

    public long getId() {
    	    if (_id > 0) return _id;
        return _id = getLongField("_id");
    }

    public int getStatus() {
        if (_status > 0) return _status;
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

    public static final Parcelable.Creator<ReflectedObject> CREATOR = new Creator<ReflectedObject>() {
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
