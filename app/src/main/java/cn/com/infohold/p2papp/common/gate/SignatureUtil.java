//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.com.infohold.p2papp.common.gate;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import cn.com.infohold.p2papp.common.SharedPreferencesUtils;
import cn.com.infohold.p2papp.common.XmlParse;
import common.eric.com.ebaselibrary.util.StringUtils;

public final class SignatureUtil {
    public String url;

    public SignatureUtil() {
    }

    public static String sign(Context context, String url_api, String method, Map<String, String> params) {
        String url = url_api;
        params.put("deviceid", (String) SharedPreferencesUtils.getParam(context, "deviceid", ""));
        params.put("imsi", getImsi(context));
        params.put("imei", getImei(context));
        params.put("appkey", (String) SharedPreferencesUtils.getParam(context, "app_key", ""));
        params.put("channel_id", (String) SharedPreferencesUtils.getParam(context, "app_channel", ""));
        params.put("method", method);
        String api_version = XmlParse.getPapers(context, method, "method");
        if (api_version.equals(method)) {
            api_version = "1";
        }
        params.put("api_version", api_version);
        params.put("ua", getUA(context));
        params.put("nonce", UUID.randomUUID().toString());
        params.put("timestamp", "" + System.currentTimeMillis() / 1000L);
        encode(params);
        Map sortedParams = MapUtil.sort(params);
        String paramString = URLUtil.map2string(sortedParams);
        LogUtils.i("param1---->" + paramString);
        try {
            String e = URLEncoder.encode(url.toLowerCase(), "utf-8") + URLEncoder.encode(paramString, "utf-8");
            e = e.replaceAll("\\+", "%20");
            e = e.replaceAll("\\!", "%21");
            e = e.replaceAll("\\~", "%7E");
            e = e.replaceAll("\\*", "%2A");
            LogUtils.i("baseString---->" + e);
            String signature = HmacSha.getSignature(e, (String) SharedPreferencesUtils.getParam(context, "app_secret", ""));
            signature = URLEncoder.encode(signature, "utf-8");
            params.put("sign", signature);
            LogUtils.i(url + params.toString());
            StringBuffer sb = new StringBuffer();
            Iterator var12 = params.entrySet().iterator();

            while (var12.hasNext()) {
                Entry entry = (Entry) var12.next();
                sb.append("&");
                sb.append((String) entry.getKey());
                sb.append("=");
                sb.append((String) entry.getValue());
            }

            LogUtils.i(url + sb.toString().replaceFirst("&", "?"));
        } catch (Throwable var13) {
            LogUtils.e("获取signature失败", var13);
        }

        return url_api + "?" + URLUtil.map2string(params);
    }

    public static void encode(Map<String, String> params) {
        String UTF8 = "utf-8";
        Iterator it = params.entrySet().iterator();

        while (it.hasNext()) {
            Entry kv = (Entry) it.next();
            String k = (String) kv.getKey();
            String v = (String) kv.getValue();
            LogUtils.i("param4---->" + k + " * " + v);

            try {
                v = URLEncoder.encode(v, "utf-8");
                v = v.replaceAll("\\+", "%20");
                v = v.replaceAll("\\!", "%21");
                v = v.replaceAll("\\~", "%7E");
                v = v.replaceAll("\\*", "%2A");
                params.put(k, v);
            } catch (Exception var7) {
                throw new RuntimeException("编码参数失败，请检查参数是否合法", var7);
            }
        }

    }

    public static String getImsi(Context context) {
        String imsi = SharedPreferencesUtils.getIMSI(context);
        if (StringUtils.isEmpty(imsi)) {
            TelephonyManager telMng = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telMng.getSimState() == 5) {
                imsi = telMng.getSubscriberId();
            }
        }

        return imsi;
    }

    public static String getImei(Context context) {
        String imei = SharedPreferencesUtils.getIMEI(context);
        if (StringUtils.isEmpty(imei)) {
            TelephonyManager telMng = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imei = telMng.getDeviceId();
        }

        return imei;
    }

    public static String getUA(Context context) {
        String ua = "";
        StringBuilder builder = new StringBuilder();
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int versionCode = 0;

        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (NameNotFoundException var7) {
            var7.printStackTrace();
        }

        builder.append(Build.BRAND).append("^").append(Build.MODEL).append("^").append("Android").append("^").append(VERSION.RELEASE).append("^").append(tm.getDeviceId()).append("^").append(tm.getSubscriberId()).append("^").append(versionCode);
        ua = builder.toString();
        LogUtils.i("ua---->" + ua);
        return ua;
    }
}
