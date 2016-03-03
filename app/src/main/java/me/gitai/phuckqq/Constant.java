package me.gitai.phuckqq;

import android.os.Environment;

import java.io.File;

import me.gitai.phuckqq.BuildConfig;

/**
 * Created by gitai on 16-1-3.
 */
public class Constant {
    public static final String MODULE_NAME                  = "kanban";
    public static final String MODULE_VERSION               = "v2";

    public static final String SECRET_CODE_ACTION           = "android.provider.Telephony.SECRET_CODE";

    public static final String PATH_DATA                    = Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + MODULE_NAME;
    public static final String PATH_DATA_LOG                = PATH_DATA + File.separator + "logs";
    public static final String PATH_DATA_CONFIG             = PATH_DATA + File.separator + "configs";
    public static final String PATH_DATA_SCRIPT             = PATH_DATA + File.separator + "script";

    public static final String KEY_ENABLE                   = "enable";
    public static final String KEY_SEND_ENABLE              = "enablesend";
    public static final String KEY_RES_LIST                 = "reslist";

    public static final String FILE_EXTENSION_LOG           = ".log";
    public static final String FILE_EXTENSION_CONFIG        = ".cfg";
}
