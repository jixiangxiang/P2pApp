package cn.com.infohold.p2papp.common;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

import cn.com.infohold.p2papp.activity.BaseActivity;
import cn.com.infohold.p2papp.common.gate.SignatureUtil;

/**
 * Created by eric on 2015/12/3.
 */
public class ApiUtils {

    public static final String url_api = "http://182.48.115.38:8088/platform/api";
    private StringRequest request;
    private static ApiUtils instance = null;

    /**
     * ***************************************
     * 基础服务API(base)
     * ****************************************
     */
    private final static String API_BASE = "ih.p2p.";
    public final static String APP_KEY = "610147";
    public final static String APP_SECRET = "XjieYvpcSdVgrhTlHytDImKwA9W3zPFU";
    public final static String APP_CHANNEL = "10002";
    public static final Integer REQUEST_SUCCESS = 0;

    /**
     * ***************************************
     * 接口列表服务
     * ****************************************
     */
    public static final String PROJECT_LIST = API_BASE + "router.project";
    public static final String SEND_VALID_CODE = API_BASE + "router.send_validatecode";
    public static final String CHECK_VALID_CODE = API_BASE + "router.check_validatecode";
    public static final String USER_REGIST = API_BASE + "router.userRegist";

    private ApiUtils() {

    }

    public static ApiUtils getInstance() {
        if (instance == null) {
            instance = new ApiUtils();
        }
        return instance;
    }

    public static String convert(String inStr, boolean vert) {
        String flag = "";
        if (vert) {// 加密
            flag = "0-0-0-0"; // 如果是0000开头和结尾的需要解密
        }
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't' ^ 'a');
        }
        String s = new String(a);
        return flag + s + flag;
    }

    /**
     * 获取网关验签的请求串
     *
     * @param context
     * @param method
     * @param params
     * @return
     */
    public static String transGatewayUrl(Context context, String method, Map<String, String> params) {
        return SignatureUtil.sign(context, url_api, method, params);
    }

    /**
     * 发送短信验证码
     *
     * @param activity
     * @param params
     * @return
     */
    public Request getRequestByMethod(BaseActivity activity, Map<String, String> params, String method) {
        request = new StringRequest(transGatewayUrl(activity, method, params), activity, activity);
        return request;
    }

}
