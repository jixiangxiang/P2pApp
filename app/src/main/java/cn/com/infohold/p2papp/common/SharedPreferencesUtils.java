package cn.com.infohold.p2papp.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences的一个工具类，调用setParam就能保存String, Integer, Boolean, Float,
 * Long类型的参数 同样调用getParam就能获取到保存在手机里面的数据
 *
 * @author xiaanming
 */
public class SharedPreferencesUtils {
    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "share_date";
    public static final String FAILURE_STRING = "___no_data___";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void setParam(Context context, String key, Object object) {

        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }

        editor.commit();
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam(Context context, String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 从<应用包动态配置文件>中取得<国际移动设备身份码IMEI>。
     *
     * @param context 应用上下文
     * @return 国际移动设备身份码IMEI
     */
    public static String getIMEI(Context context) {
        return getData(context, "IMEI");
    }

    /**
     * 从<应用包动态配置文件>中取得<国际移动用户识别码IMSI>。
     *
     * @param context 应用上下文
     * @return 国际移动用户识别码IMSI
     */
    public static String getIMSI(Context context) {
        return getData(context, "IMSI");
    }


    /**
     * 从<应用包动态配置文件>中取得数据。
     *
     * @param context 应用上下文
     * @param key     键
     * @return 值
     */
    public static String getData(Context context, String key) {
        /*SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE);
		return sp.getString(key, FAILURE_STRING);*/
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        String value = sp.getString(key, FAILURE_STRING);
        if (value.startsWith("0-0-0-0") && value.endsWith("0-0-0-0")) {
            String replaceValue = value.replaceAll("0-0-0-0", "");
            String convertValue = ApiUtils.convert(replaceValue, false);
            return convertValue;
        } else {
            addData(context, key, value);
        }
        return value;
    }

    /**
     * 加入数据到<应用包动态配置文件>中。
     *
     * @param context 应用上下文
     * @param key     键
     * @param value   值
     */
    public static void addData(Context context, String key, String value) {
		/*SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();*/
        String convertValue = ApiUtils.convert(value, true);
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, convertValue);
        editor.commit();
    }
}
