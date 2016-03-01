# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Rikka\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-dontwarn com.mokee.volley.**
-dontwarn com.mokee.google.**
-dontwarn com.mokee.cloud.**
-dontwarn com.mokee.os.**
-dontwarn com.mokee.security.**

-keep public class me.gitai.fuckqq.xposed.*
-keep public class me.gitai.fuckqq.utils.XposedUtils{
	public static boolean isModuleEnabled();
	public static int getModuleVersion();
}

-printmapping mapping.txt