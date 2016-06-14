# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\developer_software\android-sdk-windows/tools/proguard/proguard-android.txt
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


#-keepattributes *Annotation*
-ignorewarning
-keep,allowobfuscation class * implements java.io.Serializable {
public void set*(...);
public *** get*(...);
public *** is*(...);
public *** has*(...);
public <init>(...);
}
#-keep,allowobfuscation class * extends cn.qbcbyb.library.model.BaseModel { public *; }
-keepclassmembers class * implements java.io.Serializable {
public void set*(...);
public *** get*(...);
public *** is*(...);
public *** has*(...);
public <init>(...);
}
#四大组件不能混淆
-keep public class * extends android.app.Activity {* ;}

-keep public class * extends android.app.Application {* ;}

-keep public class * extends android.app.Service

-keep public class * extends android.content.BroadcastReceiver

-keep public class * extends android.content.ContentProvider

-keep public class * extends android.app.backup.BackupAgentHelper

-keep class * extends android.os.Handler

-keep class * extends android.os.AsyncTask {* ;}

-keep public class * extends android.preference.Preference
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
#自定义控件不要混淆
-keep public class * extends android.view.View {* ;}
#adapter也不能混淆
-keep public class * extends android.widget.BaseAdapter
-keep class cn.trinea.android.** { *; }
-keepclassmembers class cn.trinea.android.** { *; }
-dontwarn cn.trinea.android.**

-keep public class * extends common.eric.com.ebaselibrary.model.EBaseModel
-keep public class * extends cn.com.infohold.p2papp.activity.BaseActivity {*;}
-keep public class common.eric.com.ebaselibrary.adapter.** {*;}
-keepclassmembers class common.eric.com.ebaselibrary.model.EBaseModel { *; }
-keepclassmembers class common.eric.com.ebaselibrary.adapter.EBaseAdapter {*;}
-keepclassmembers class common.eric.com.ebaselibrary.adapter.** {*;}

-keep class com.github.PhilJay.** {*;}
-keep class com.github.sd6352051.niftydialogeffects.** {*;}
-keep class com.nineoldandroids.** {*;}
-keep class com.lsjwzh.** {*;}
-keep class com.google.** {*;}
-keep class javax.annotation.** {*;}
-keep class com.facebook.** {*;}
-keepclassmembers class com.facebook.** { *; }
-keep class com.github.mikephil.charting.** {*;}
-keep class android.support.** {*;}
-keep class com.gitonway.lee.niftymodaldialogeffects.** {*;}
-keep class com.lsjwzh.widget.materialloadingprogressbar.** {*;}
-keep class com.mcxiaoke.volley.** {*;}
-keep class com.alibaba.** {*;}
-keep class com.android.volley.** {*;}
-keep class bolts.Task {*;}

-dontwarn com.alibaba.**
-dontwarn com.github.PhilJay.**
-dontwarn com.github.sd6352051.niftydialogeffects.**
-dontwarn android.support.**