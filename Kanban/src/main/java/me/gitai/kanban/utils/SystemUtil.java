package me.gitai.kanban.utils;

import android.os.Environment;
import me.gitai.library.utils.L;

public class SystemUtil {
	private static String packageName,externalStorageDirectory,qQDirectory,head,hd,thd,sSOhd;

	private static int qqType = 0;

	public static String QQ_PACKAGE_NAME_QQ = "";
	public static String QQ_PACKAGE_NAME_QQI = "com.tencent.mobileqqi";
	public static String QQ_PACKAGE_NAME_LITE = "com.tencent.qqlite";
	public static String QQ_PACKAGE_NAME_KDDI = "com.tencent.qq.kddi";

	public static int QQ_TYPE_DEFAULT = 0;
	public static int QQ_TYPE_QQ = 1;
	public static int QQ_TYPE_QQI = 2;
	public static int QQ_TYPE_LITE = 3;
	public static int QQ_TYPE_KDDI = 4;

	private static String[] PACKAGE_NAME_QQ = {"com.tencent.mobileqq", "com.tencent.mobileqq", "com.tencent.mobileqqi", "com.tencent.qqlite", "com.tencent.qq.kddi"};
	private static String[] DATA_QQ = {"/tencent/MobileQQ/", "/tencent/MobileQQ/", "/tencent/MobileQQi/", "/tencent/QQLite/", "/tencent/MobileQQi/"};
	private static String[] EX_HEAD = {".png", ".jpg_", ".png", ".png", ".png"};

	public static String getPackageName(){
		return getPackageName(qqType);
	}

	public static String getPackageName(int n){
		return PACKAGE_NAME_QQ[n];
	}

	public static void setPackageName(String str){
		packageName = str;
		switch(str){
			case "com.tencent.mobileqq":
				qqType = QQ_TYPE_QQ;
				break;
			case "com.tencent.mobileqqi":
				qqType = QQ_TYPE_QQI;
				break;
			case "com.tencent.qqlite":
				qqType = QQ_TYPE_LITE;
				break;
			case "com.tencent.qq.kddi":
				qqType = QQ_TYPE_KDDI;
				break;
			default:
				qqType = QQ_TYPE_DEFAULT;
				break;
		}
	}

	public static int getQQType(){
		L.d("qqType: " + qqType);
		if (qqType >= 0 && qqType <= DATA_QQ.length) {
			return qqType;
		}
		return 0;
	}

	public static String getExternalStorageDirectory(){
		if (externalStorageDirectory != null) return externalStorageDirectory;
		return externalStorageDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	public static String getQQDirectory(){
		if (qQDirectory != null) return qQDirectory;
		return qQDirectory = getExternalStorageDirectory() + getQQDirectoryPart();
	}

	public static String getQQDirectoryPart(){
		return DATA_QQ[getQQType()];
	}

	public static String getHeadExt(){
		return EX_HEAD[getQQType()];
	}

	public static String getHead(){
		if (head != null) return head;
		return head = getQQDirectory() + "head/";
	}

	public static String getHd(){
		if (hd != null) return hd;
		return hd = getHead() + "_hd/";
	}

	public static String getThd(){
		if (thd != null) return thd;
		return thd = getHead() + "_thd/";
	}

	public static String getSSOhd(){
		if (sSOhd != null) return sSOhd;
		return sSOhd = getHead() + "_SSOhd/";
	}

	public static boolean isExternalStorageMounted(){
		try {
			return Environment.getExternalStorageState().equals("mounted");
		}catch (Exception var0_2) {
		    return false;
		}
	}
}

