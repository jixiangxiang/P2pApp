package com.example.eric.oscar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;

import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.OscarServiceBean;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.views.WrapScrollListView;

import java.util.ArrayList;
import java.util.List;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class OHelpActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private WrapScrollListView oscarHelpList;
    private List<OscarServiceBean> oscarServiceBeans;
    private EBaseAdapter baseAdapter;
    private SparseArray<String> descs = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ohelp);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_ohelp), BaseActivity.TITLE_CENTER);
        oscarServiceBeans = new ArrayList<OscarServiceBean>();
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "什么是奥斯卡？"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "为什么要绑定奥斯卡"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "怎么得到奥斯卡"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "绑定奥斯卡需要什么条件？？"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "为什么要绑定奥斯卡"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "如何绑定奥斯卡？"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "绑定的奥斯卡还能在别的地方使用吗？"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "关于奥斯卡充值"));
        baseAdapter = new EBaseAdapter(this, oscarServiceBeans, R.layout.list_oscar_service_item,
                new String[]{"serviceIcon", "serviceName"}, new int[]{R.id.serviceIcon, R.id.serviceName});
        oscarHelpList.setAdapter(baseAdapter);
        descs.append(1, "奥斯卡是“北京商银信商业信息服务有限责任公司” 发行的一张功能强大、服务体系完善的便民通用卡,可在网上进行线上购物,也可以在网上进行各项生活缴费业务,可在3000多家精选 商家进行刷卡。商银信公司于2012年获得中国人民银行颁发的支付业务许可证，能够为广大用户提供有安全保障的完善服务。");
        descs.append(2, "一个用户帐号可以方便的绑定多张持有的奥斯卡，绑定之后的多张奥斯卡可以方便的进行统一管理，不再需要记住冗长的卡号和每一张卡的密码。登录用户账号之后可以方便的直接查看所有绑定奥斯卡的余额信息，通过点击选取操作每一张卡片，消费、查账、转账等操作都会变得异常简便快捷。");
        descs.append(3, "电话购买\n" +
                "销售热线：400-610-7755 或 400-620-7575 按“1”\n" +
                "线下购买\n" +
                "工作时间：周一至周五 09:00-17:00\n" +
                "购买地址：北京市西城区平安里西大街28号中海国际中心五层\n" +
                "转账信息:\n" +
                "户 名：北京商银信商业信息服务有限责任公司客户备付金\n" +
                "开户行：招商银行北京分行营业部\n");
        descs.append(4, "用户账号需要完成身份验证，并设置了支付密码之后，才可以进行绑定奥斯卡操作，这样的限制条件是为了更好的保障您的奥斯卡资金安全，因为在您的奥斯卡与用户账号绑定之后，奥斯卡内的资金安全就由账号安全直接保障，所以要先完善您的账户信息。");
        descs.append(5, "一个用户帐号可以方便的绑定多张持有的奥斯卡，绑定之后的多张奥斯卡可以方便的进行统一管理，不再需要记住冗长的卡号和每一张卡的密码。登录用户账号之后可以方便的直接查看所有绑定奥斯卡的余额信息，通过点击选取操作每一张卡片，消费、查账、转账等操作都会变得异常简便快捷。");
        descs.append(6, "登录用户账号后，到达“绑定奥斯卡”操作界面，按要求输入卡号和卡片密码，该密码为奥斯卡实体卡片上刮开的印刷密码，如果曾经进行过修改操作，当前输入最新密码。提交后后台会判断该奥斯卡是否在商银信网站设置过奥斯卡支付密码，如果未曾设置，直接完成绑定，如果持卡人曾经设置过奥斯卡支付密码，接下来还会要求用户输入支付密码，通过验证完成绑定。");
        descs.append(7, "可以，奥斯卡在本系统中的绑定，只是方便在本手机端系统中的管理和使用，不会影响您的奥斯卡在实体店、网站的正常使用。");
        descs.append(8, "奥斯卡充值是指用户把个人钱包中的资金充值到奥斯卡中，充值系统会判断充值后目标奥斯卡内资金是否超过限额（实名卡：5000.00元；非实名卡：1000.00元），超出限额会导致充值失败。\n" +
                "充值后的资金不能再回退到个人钱包，此充值为单向操作。\n");
        oscarHelpList.setOnItemClickListener(this);
    }

    private void initialize() {
        oscarHelpList = (WrapScrollListView) findViewById(R.id.oscarHelpList);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, OHelpDescActivity.class);
        intent.putExtra("desc", descs.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
