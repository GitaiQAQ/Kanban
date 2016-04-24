package me.gitai.kanban;

import android.os.Environment;

import java.io.File;

/**
 * Created by gitai on 16-1-3.
 */
public class Constant {
    public static final String MODULE_NAME                  = "kanban";
    public static final String MODULE_VERSION               = "v2";

    public static final String SECRET_CODE_ACTION           = "android.provider.Telephony.SECRET_CODE";

    public static final String PATH_DATA                    = Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + MODULE_NAME;
    public static final String PATH_DATA_LOG                = PATH_DATA + File.separator + "logs";
    public static final String PATH_DATA_CRASH              = PATH_DATA + File.separator + "crash";
    public static final String PATH_DATA_CONFIG             = PATH_DATA + File.separator + "configs";
    public static final String PATH_DATA_SCRIPT             = PATH_DATA + File.separator + "script";

    public static final String ACTION_RECEIVER              = "me.gitai.kanban.message.reveiver";

    public static final String KEY_PACKAGENAME             = ACTION_RECEIVER + ".packageName";
    public static final String KEY_CONTACTS_COUNT          = ACTION_RECEIVER + ".contacts.count";
    public static final String KEY_UNREADS_COUNT           = ACTION_RECEIVER + ".unread.count";
    public static final String KEY_APP_PID                 = ACTION_RECEIVER + ".pid";
    public static final String KEY_NEEDTICKER              = ACTION_RECEIVER + ".needTicker";
    public static final String KEY_MESSAGES                 = ACTION_RECEIVER + ".messages";
    public static final String KEY_CURRENT_MESSAGE         = ACTION_RECEIVER + ".currentMessage";

    public static final String KEY_ENABLE                   = "enable";
    public static final String KEY_SEND_ENABLE              = "enablesend";
    public static final String KEY_RES_LIST                 = "reslist";

    public static final String FILE_EXTENSION_LOG           = ".log";
    public static final String FILE_EXTENSION_CONFIG        = ".cfg";
}
