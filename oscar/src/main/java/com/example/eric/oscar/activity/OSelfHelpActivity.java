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

public class OSelfHelpActivity extends BaseActivity implements AdapterView.OnItemClickListener {

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
        initTitleText(getString(R.string.title_activity_o_self_help), BaseActivity.TITLE_CENTER);
        oscarServiceBeans = new ArrayList<OscarServiceBean>();
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "为什么要进行身份验证？"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "忘记了支付密码怎么办？"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "别人拿到了我的手机会不会造成资金损失？"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "我换了手机号码还能使用原来的用户账号吗？"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "为什么要绑定银行卡？"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "绑定的银行卡有什么要求？"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "怎么对钱包进行充值？"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "申请提现后，多久能到账？"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "提现手续费是多少？"));
        baseAdapter = new EBaseAdapter(this, oscarServiceBeans, R.layout.list_oscar_service_item,
                new String[]{"serviceIcon", "serviceName"}, new int[]{R.id.serviceIcon, R.id.serviceName});
        oscarHelpList.setAdapter(baseAdapter);
        descs.append(0, "为了保障用户的账号及资金安全，平台需要用户在注册后尽快完成身份验证，真实身份验证信息会是账号安全的基本保障，密码保护等相关操作都会与身份验证信息直接关联。完成了身份验证之后才可以进行奥斯卡绑定及银行卡绑定等后续与资金相关的操作。");
        descs.append(1, "用户支付密码忘记的情况下，可以在登录系统后通过当前用户手机申请验证码，再提供完整真实身份证号码信息，确认信息无误后提示用户重新设置支付密码。");
        descs.append(2, "不会，别人拿到了您的手机，虽然可以通过短信验证码修改系统登录密码，但是在无法得知您支付密码的情况下，也不能操作您的钱包资金，修改支付密码需要提供原支付密码或提供完整身份证号码信息才能完成，所以别人仅仅是拿到了您的手机，不会造成您账户资金损失。");
        descs.append(3, "可以使用，用户更换了手机号码，可以通过系统提供的“更换注册手机”来改变当前的登录手机号码，更换操作后，用户即可使用新手机号码登录账号，不影响任何使用。");
        descs.append(4, "用户账号绑定的银行卡是用于钱包资金提现使用。");
        descs.append(5, "\t1、\t平台要求绑定的银行卡开户人，必须与用户账号通过身份验证的真实姓名完全一致，否则无法绑定。\n" +
                "2、\t绑定的银行卡必须是页面选择列表中存在的银行所发行的借记卡，不支持信用卡及银行存折。\n。");
        descs.append(6, "1、\t平台当前只支持使用银联借记卡通过快捷支付对钱包进行充值，暂时不支持信用卡支付。\n" +
                "2、\t银行卡快捷支付要求用户银行卡在银行预留的手机号与当前操作的手机号一致，或银行预留的手机号也在操作人手边，方便接收银行端发送到银行预留手机号码上的验证信息，才能完成支付操作。早期发行的银行卡没有要求用户留存手机号码，无法完成快捷支付操作。\n。");
        descs.append(7, "系统收到用户提现申请后即对提现进行转账操作，由于不同银行处理速度不同，提现资金将会于1-2个工作日内到账（如遇双休日或法定节假日顺延）。");
        descs.append(8, "钱包资金余额提现到银行卡每笔收取2元手续费。");
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
