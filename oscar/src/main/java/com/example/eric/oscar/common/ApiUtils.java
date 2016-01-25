package com.example.eric.oscar.common;

/**
 * Created by eric on 2015/12/23.
 */
public class ApiUtils {

    public static final Integer REQUEST_SUCCESS = 0;
    public static final String BASE_URL = "http://211.157.145.67:6081/app/";
    public static final String QINIU_URL = "http://7xq99q.com2.z0.glb.qiniucdn.com/";
    public static final String SIGN = "111111";
    public static final String TOKEN = "111111";
    public static int NEED_LOGIN = 10012;

    public static final String REGIST = BASE_URL + "ac/registration";
    public static final String LOGIN = BASE_URL + "ac/login";
    public static final String BINDLIST = BASE_URL + "os/bindlist";
    public static final String BIND = BASE_URL + "os/bind";
    public static final String PREBIND = BASE_URL + "os/prebind";
    public static final String BAL = BASE_URL + "os/bal";
    public static final String CRAMZ = BASE_URL + "os/cramz";
    public static final String ACAMZ = BASE_URL + "os/acamz";
    public static final String JDCARD = BASE_URL + "os/jdcard";
    public static final String CRJD = BASE_URL + "os/crjd";
    public static final String ACJD = BASE_URL + "os/acjd";
    public static final String SETPP = BASE_URL + "ac/setpp";//设置支付密码
    public static final String ACCTINFO = BASE_URL + "ac/i";//个人中心
    public static final String EDITLP = BASE_URL + "ac/editlp";//修改登陆密码
    public static final String FOGLP = BASE_URL + "ac/foglp";//
    public static final String FOGSLP = BASE_URL + "ac/fogslp";//
    public static final String SMS = BASE_URL + "ac/sms";//
    public static final String FOGPP = BASE_URL + "ac/fogpp";//
    public static final String FOGSPP = BASE_URL + "ac/fogspp";//
    public static final String EACCT = BASE_URL + "ac/eacct";//
    public static final String ESACCT = BASE_URL + "ac/esacct";//
    public static final String ASSETS = BASE_URL + "ac/assets";//
    public static final String OSCAR = BASE_URL + "ac/oscar";//
    public static final String BANKCARD = BASE_URL + "ac/bankcard";//
    public static final String WALREC = BASE_URL + "ac/walrec";//资金记录
    public static final String RECHGREC = BASE_URL + "ac/rechgrec";//充值记录
    public static final String FROZREC = BASE_URL + "ac/frozrec";//充值记录
    public static final String BCINFO = BASE_URL + "ac/bcinfo";//
    public static final String ADDBC = BASE_URL + "ac/addbc";//
    public static final String AUTHEN = BASE_URL + "ac/authen";//
    public static final String CROIL = BASE_URL + "os/croil";// 创建加油卡订单
    public static final String ACOIL = BASE_URL + "os/acoil";// 激活加油卡订单
    public static final String INVLIST = BASE_URL + "iv/invlist";// 理财列表
    public static final String INVINFO = BASE_URL + "iv/invinfo";// 理财列表
    public static final String WALBAL = BASE_URL + "wa/bal";// 钱包余额
    public static final String CPLIS = BASE_URL + "wa/cplis";// 钱包余额
    public static final String CRTELE = BASE_URL + "os/crtele";//充手机
    public static final String ACTELE = BASE_URL + "os/actele";//
}
