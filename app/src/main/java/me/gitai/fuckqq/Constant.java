package me.gitai.fuckqq;

/**
 * Created by gitai on 16-1-3.
 */
public class Constant {
    public static final int MODULE_VERSION               = 0;

    public static final String SECRET_CODE_ACTION           = "android.provider.Telephony.SECRET_CODE";

    public static final String INSTALLER_PACKAGE_NAME       = BuildConfig.APPLICATION_ID;
    public static final String PATH_DATA                    = "/data/data/" + INSTALLER_PACKAGE_NAME + "/";
    public static final String PATH_DATA_LOG                = PATH_DATA + "logs/";

    public static final String COMPONENTNAME_MAIN           = "me.gitai.smscodehelper.MainPreferences";
    public static final String ACTION_XPOSED                = "me.gitai.smscodehelper.action.Xposed";

    public static final String KEY_CLIP_LABEL               = "SMSCode";
    public static final String KEY_BUNDLE_SMS_CODE          = "smscode";
    public static final String KEY_SMS_PDUS                 = "pdus";
    public static final String KEY_SMS_DATA                 = "messagedata";

    public static final String KEY_GENERAL_RUN              = "general_enable";
    public static final String KEY_GENERAL_HIDDEN_ICON      = "general_hidden_icon";
    public static final String KEY_GENERAL_GUESS            = "general_guess";
    public static final String KEY_GENERAL_TEST             = "general_test";

    public static final String KEY_PARSE_TYPE               = "parse_type";
    public static final String KEY_PARSE_KEYWORDS           = "parse_keywords";
    public static final String KEY_PARSE_KEYWORDS_TEXT      = "验证码|校验码|动态码|确认码|随机码|验证|校验|验证密码|动态密码|校验密码|随机密码|确认密码|激活码|兑换码|认证码|认证号码|认证密码|交易码|交易密码|授权码|操作码|密码|提取码|安全代码";
    public static final String KEY_PARSE_AMBIGUITIES        = "parse_ambiguities";
    public static final String KEY_PARSE_AMBIGUITIES_TEXT   = "google|facebook|steam|microsoft";
    public static final String KEY_PARSE_REGEXS_PROVIDER    = "parse_provider_regexs";
    public static final String KEY_PARSE_REGEXS_CAPTCHAS    = "parse_captchas_regexs";

    public static final String KEY_TASK_COPY                = "task_copy";
    public static final String KEY_TASK_CLIPBOARD_CHECK     = "task_clipboard_check";
    public static final String KEY_TASK_NOTIFICATION        = "task_notification";
    public static final String KEY_TASK_INTERCEPT           = "task_intercept";

    public static final String KEY_ABOUT_HELP_FEEDBACK      = "parse_captchas_regexs";
    public static final String KEY_ABOUT_RATE_APP           = "about_rate_app";
    public static final String KEY_ABOUT_DONATE             = "about_donate";
    public static final String KEY_ABOUT_LICENSE            = "about_license";
    public static final String KEY_ABOUT_VERSION            = "about_version";
}
