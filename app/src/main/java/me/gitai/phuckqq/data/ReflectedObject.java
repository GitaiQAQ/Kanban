package me.gitai.phuckqq.data;

import de.robv.android.xposed.XposedHelpers;

/**
 * Created by dphdjy on 16-3-2.
 */
public class ReflectedObject {

    private final Object mObject;

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

    @Override
    public String toString() {
        return new StringBuilder("--Dump SessionInfo--")
                .toString();
    }
}
