package cn.com.infohold.p2papp.common;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import cn.com.infohold.p2papp.activity.BaseActivity;
import cn.com.infohold.p2papp.base.BaseFragment;
import cn.com.infohold.p2papp.bean.UserBean;
import cn.com.infohold.p2papp.common.gate.SignatureUtil;
import common.eric.com.ebaselibrary.util.StringUtils;

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
    public static final int REQUEST_SUCCESS = 0;
    public static final int NEED_LOGIN = -112004;
    public static final int NO_RELANAME = 100009;
    public static final String CIFSEQ = "2";

    /**
     * ***************************************
     * 接口列表服务
     * ****************************************
     */
    public static final String PROJECT_LIST = API_BASE + "router.project";
    public static final String SEND_VALID_CODE = API_BASE + "router.send_validatecode";
    public static final String CHECK_VALID_CODE = API_BASE + "router.check_validatecode";
    public static final String USER_REGIST = API_BASE + "router.userRegist";
    public static final String USER_LOGIN = API_BASE + "router.userLogin";
    public static final String ACCTPREVIEW = API_BASE + "router.acctpreview";
    public static final String PROJECTDETAILPER = API_BASE + "router.projectdetailper";
    public static final String PROJECTDETAILCUST = API_BASE + "router.projectdetailcust";
    public static final String INVESTRECORDS = API_BASE + "router.records";
    public static final String ACCTBALANCE = API_BASE + "router.acctbalance";
    public static final String REALNAMEAUTH = API_BASE + "router.appRealnameAuth";
    public static final String FETCHBANKS = API_BASE + "router.fetchBanks";
    public static final String FETCHBRANCHES = API_BASE + "router.fetchBranches";
    public static final String FETCHPROVINCES = API_BASE + "router.fetchProvinces";
    public static final String FETCHCITIES = API_BASE + "router.fetchCities";
    public static final String APPBOUNDCARD = API_BASE + "router.appBoundCard";
    public static final String INVESTPROJECT = API_BASE + "router.investproject";
    public static final String RESETPASSWORD = API_BASE + "router.resetPassword";
    public static final String TOACCTSET = API_BASE + "router.toacctset";
    public static final String ACCTSET = API_BASE + "router.acctset";
    public static final String TOWITHDRAW = API_BASE + "router.towithdraw";
    public static final String WITHDRAW = API_BASE + "router.withdraw";
    public static final String SECURITYPASSWORD = API_BASE + "router.security_password";
    public static final String MYINVEST = API_BASE + "router.myinvest";
    public static final String TRANSQUERY = API_BASE + "router.transquery";
    public static final String TOSECURITY = API_BASE + "router.tosecurity";
    public static final String SECURITYMOBILEEMAIL = API_BASE + "router.security_mobile_email";

    private ApiUtils() {

    }

    public static ApiUtils getInstance() {
        if (instance == null) {
            instance = new ApiUtils();
        }
        return instance;
    }

    public static String encryptForMD5(String password) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(password.getBytes());
            byte[] m = md5.digest();// 加密
            return bytesToHex(m);
        } catch (NoSuchAlgorithmException e) {
        }
        return password;
    }


    public static String digesPSW(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(password.getBytes());
            return bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
        }
        return password;
    }

    private static String bytesToHex(final byte[] bytes) {
        System.out.println(bytes);
        final StringBuilder buf = new StringBuilder(bytes.length * 2);
        for (final byte b : bytes) {
            final String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                buf.append("0");
            }
            buf.append(hex);
        }
        return buf.toString();
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

    /**
     * 获取请求类
     *
     * @param activity
     * @param params
     * @return
     */
    public Request getRequestByMethod(BaseFragment fragment, Map<String, String> params, String method) {
        request = new StringRequest(transGatewayUrl(fragment.getActivity(), method, params), fragment, fragment);
        return request;
    }

    public static String getLoginUserPhone(Context context) {
        String userinfo = (String) SharedPreferencesUtils.getParam(context, "userinfo", "");
        return JSONObject.parseObject(userinfo).getString("mobilephone");
    }

    public static boolean isLogin(Context context) {
        String userinfo = (String) SharedPreferencesUtils.getParam(context, "userinfo", "");
        return !StringUtils.isEmpty(userinfo);
    }

    public static String getLoginUserStatus(Context context) {
        String userinfo = (String) SharedPreferencesUtils.getParam(context, "userinfo", "");
        return JSONObject.parseObject(userinfo).getString("userstatus");
    }

    public static UserBean getLoginUser(Context context) {
        String userinfo = (String) SharedPreferencesUtils.getParam(context, "userinfo", "");
        UserBean user = JSONObject.parseObject(userinfo, UserBean.class);
        return user;
    }

    public static void updateUserStatus(Context context, String userStatus, String username, String idNo) {
        String userinfo = (String) SharedPreferencesUtils.getParam(context, "userinfo", "");
        UserBean user = JSONObject.parseObject(userinfo, UserBean.class);
        user.setUserstatus(userStatus);
        user.setIdno(idNo);
        user.setUsername(username);
        SharedPreferencesUtils.setParam(context, "userinfo", JSONObject.toJSONString(user));

    }

    public static void updateUserStatus(Context context, String userStatus) {
        String userinfo = (String) SharedPreferencesUtils.getParam(context, "userinfo", "");
        UserBean user = JSONObject.parseObject(userinfo, UserBean.class);
        user.setUserstatus(userStatus);
        SharedPreferencesUtils.setParam(context, "userinfo", JSONObject.toJSONString(user));
    }

    public static void updateUserPhone(Context context, String phone) {
        String userinfo = (String) SharedPreferencesUtils.getParam(context, "userinfo", "");
        UserBean user = JSONObject.parseObject(userinfo, UserBean.class);
        user.setMobilephone(phone);
        SharedPreferencesUtils.setParam(context, "userinfo", JSONObject.toJSONString(user));
    }

    public static void updateUseCard(Context context, String userStatus, String bankId, String bankNo) {
        String userinfo = (String) SharedPreferencesUtils.getParam(context, "userinfo", "");
        UserBean user = JSONObject.parseObject(userinfo, UserBean.class);
        user.setUserstatus(userStatus);
        user.setBankcardno(bankNo);
        user.setOpeningbank(bankId);
        SharedPreferencesUtils.setParam(context, "userinfo", JSONObject.toJSONString(user));

    }

}
