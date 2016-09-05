package com.example.eric.oscar.common;

/**
 * Created by eric on 2015/12/23.
 */
public class ApiUtils {

    public static final Integer REQUEST_SUCCESS = 0;
    //public static final String BASE_URL = "http://211.157.145.67:6081/app/";
    public static final String BASE_URL = "https://mtest.allscore.com/app/";
    public static final String QINIU_URL = "http://7xq99q.com2.z0.glb.qiniucdn.com/";
    public static final String SIGN = "111111";
    public static final String TOKEN = "111111";
    public static final String PWD_REGEX = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
    public static int NEED_LOGIN = 10001;

    public static final String REGIST = BASE_URL + "ac/registration";
    public static final String LOGIN = BASE_URL + "ac/login";
    public static final String BINDLIST = BASE_URL + "os/ratelist";
    public static final String BIND = BASE_URL + "os/bind";
    public static final String PREBIND = BASE_URL + "os/prebind";
    public static final String BAL = BASE_URL + "os/bal";
    public static final String CRAMZ = BASE_URL + "os/cramz";
    public static final String ACAMZ = BASE_URL + "os/acamz";
    public static final String JDCARD = BASE_URL + "os/jdcard";
    public static final String CRJD = BASE_URL + "os/crjd";
    public static final String ACJD = BASE_URL + "os/acjd";
    public static final String PRERECHG = BASE_URL + "os/prerechg";
    public static final String RECHG = BASE_URL + "os/rechg";
    public static final String OSDEL = BASE_URL + "os/del";
    public static final String LOGOUT = BASE_URL + "ac/logout";


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
    public static final String BANKCARD = BASE_URL + "bk/list";//
    public static final String WALREC = BASE_URL + "ac/walrec";//资金记录
    public static final String RECHGREC = BASE_URL + "ac/rechgrec";//充值记录
    public static final String FROZREC = BASE_URL + "ac/frozrec";//充值记录
    public static final String BCINFO = BASE_URL + "ac/bcinfo";//
    public static final String ADDBC = BASE_URL + "bk/bind";//
    public static final String AUTHEN = BASE_URL + "ac/authen";//
    public static final String CROIL = BASE_URL + "os/croil";// 创建加油卡订单
    public static final String ACOIL = BASE_URL + "os/acoil";// 激活加油卡订单
    public static final String INVLIST = BASE_URL + "iv/invlist";// 理财列表
    public static final String INVINFO = BASE_URL + "iv/invinfo";// 理财列表
    public static final String WALBAL = BASE_URL + "wa/bal";// 钱包余额
    public static final String CPLIS = BASE_URL + "wa/cplis";// 钱包余额
    public static final String CRTELE = BASE_URL + "os/crtele";//充手机
    public static final String ACTELE = BASE_URL + "os/actele";//
    public static final String MMYINV = BASE_URL + "iv/myinv";//
    public static final String CPLISt = BASE_URL + "wa/cplist";//
    public static final String ACINV = BASE_URL + "iv/acinv";//
    public static final String PRETOCARD = BASE_URL + "os/pretocard";//
    public static final String TOCARD = BASE_URL + "os/tocard";//
    public static final String CRINV = BASE_URL + "iv/crinv";//创建投资
    public static final String TRANSLIST = BASE_URL + "os/translist";
    public static final String PREADD = BASE_URL + "bk/prebind";
    public static final String AVATAR = BASE_URL + "ac/avatar";
    public static final String PROV = BASE_URL + "bk/prov";
    public static final String CITY = BASE_URL + "bk/city";
    public static final String BKBN = BASE_URL + "bk/bn";

    public static final String MCLIST = BASE_URL + "mc/mclist";//
    public static final String SLIST = BASE_URL + "mc/slist";//
    public static final String DSMS = BASE_URL + "bk/dsms";//
    public static final String REDSMS = BASE_URL + "bk/redsms";//
    public static final String DEPOSIT = BASE_URL + "bk/deposit";//
    public static final String CASH = BASE_URL + "bk/cash";//
    public static final String CASHRATE = BASE_URL + "bk/cashrate";//
    public static final String MSGLIST = BASE_URL + "nt/list";//
    public static final String MSGDEL = BASE_URL + "nt/del";//
    public static final String MSGINFO = BASE_URL + "nt/info";//
    public static final String MCINFO = BASE_URL + "mc/mcinfo";//
}
