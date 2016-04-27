package me.gitai.kanban.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Avatar{

	public static String DATA_FILES = "/data/data/com.tencent.mobileqq/files/";

     public static String getHead(int n, String string){
     	StringBuilder builder = new StringBuilder(256);
		String string2 = toMD5(string);
		String string3 = toMD5(string2 + string);
		String string4 = toMD5(string3 + string);

		/*if (n == 4) {
            if (SystemUtil.isExternalStorageMounted()) {
                builder.append(SystemUtil.getThd());
            } else {
                builder.append(DATA_FILES + "head/_thd/");
            }
        } else */if (SystemUtil.isExternalStorageMounted()) {
            builder.append(SystemUtil.getHd());
        } else {
            builder.append(DATA_FILES + "head/_hd/");
        }
        if (n == 101) {
            builder.append("discussion_");
        }else if(n == 4){
            builder.append("troop_");
        }else if(n == -56){
            builder.append("troop_sys_b_");
        }else if(n == -55){
            builder.append("sys_");
        }else if(n == 101){
            builder.append("dis_e_");
        }else if(n == 32){
            //builder.append("stranger_").append(String.valueOf(n2)).append("_");
        }
        builder.append(string4);
        builder.append(SystemUtil.getHeadExt());
        return builder.toString();
     }

     /*public String getHeadPath(int n, String string, int n2){
     	StringBuilder builder = new StringBuilder(16);
     	switch(n){
     		case -56:
     			builder.append("troop_sys_b_");
     		case -55:
     			builder.append("sys_");
     		case 101:
     			builder.append("dis_e_");
     		case 4:
     			builder.append("troop_");
     		case 32:
     			builder.append("stranger_").append(String.valueOf(n2)).append("_");
     		default:
     	}
     	builder.append(string);
		return string4;
	}*/

	public static String toMD5(String string){
		byte[] arrby;
		try {
			arrby = string.getBytes("ISO8859_1");
		}catch (UnsupportedEncodingException var2_4) {
			arrby = string.getBytes();
		}
	    byte[] arrby2 = getMD5(arrby, 0, arrby.length);
        String string2 = "";
        for (int i = 0; i < 16; ++i) {
            string2 = String.valueOf(string2) + byteHEX(arrby2[i]);
        }
        return string2;
	}

    public static byte[] getMD5(byte[] arrby, int n, int n2) {
        if (arrby == null || n2 == 0 || n < 0) {
            return null;
        }
        byte[] arrby2 = sysGetBufferMd5(arrby, n, n2);
        if (arrby2 != null) {
            return arrby2;
        }
        /*try {
            byte[] arrby3;
            arrby2 = arrby3 = getBufferMd5(arrby);
        }catch (Exception var6_6) {
            var6_6.printStackTrace();
        }*/
        /*if (arrby2 != null) {
            this.digest = arrby2;
            return this.digest;
        }*/
        return null;
    }

    public static byte[] sysGetBufferMd5(byte[] arrby, int n, int n2) {
        if (arrby == null || n2 == 0) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(arrby, n, n2);
            byte[] arrby2 = messageDigest.digest();
            return arrby2;
        }
        catch (NoSuchAlgorithmException var3_5) {
            var3_5.printStackTrace();
            return null;
        }
    }

	public static String byteHEX(byte by) {
        char[] arrc = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] arrc2 = new char[]{arrc[15 & by >>> 4], arrc[by & 15]};
        return new String(arrc2);
    }
}