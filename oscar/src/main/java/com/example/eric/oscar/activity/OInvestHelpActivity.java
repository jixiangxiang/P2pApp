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

public class OInvestHelpActivity extends BaseActivity implements AdapterView.OnItemClickListener {

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
        initTitleText(getString(R.string.title_activity_o_invest_help), BaseActivity.TITLE_CENTER);
        oscarServiceBeans = new ArrayList<OscarServiceBean>();
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "我的理财金投资到了哪里？"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "我的投资本金和收益由安全保障吗？"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "用奥斯卡怎么投资？"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "理财道具怎么使用？"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "我投资了之后会马上有收益吗？"));
        baseAdapter = new EBaseAdapter(this, oscarServiceBeans, R.layout.list_oscar_service_item,
                new String[]{"serviceIcon", "serviceName"}, new int[]{R.id.serviceIcon, R.id.serviceName});
        oscarHelpList.setAdapter(baseAdapter);
        descs.append(0, "一点财富”是商银信支付服务有限责任公司战略合作伙伴，平台所有投资标都与一点财富融资项目对接，享受同样的服务和保障。“一点财富”是北京吉信电子商务有限公司旗下的居间金融咨询平台，2014年于北京注册成立，注册资金5000万。官方网站：https://www.onewealth.com.cn/");
        descs.append(1, "1.一点财富每款理财产品均有指定签约担保公司担保，如出现逾期未还情况，30天内由担保公司进行本息全额代偿；\n" +
                "2.逾期罚息：\n" +
                "1-15天：0.5‰；16-30天：1‰；\n" +
                "所得罚息按该理财产品实际投资人份额进行赔偿分配。\n");
        descs.append(2, "系统提供的理财产品面向对象中包括“奥斯卡”，用户可以在填写投资金额后选择投资方式是“账号钱包”或者“奥斯卡”，按操作页面提示，在账号绑定的奥斯卡列表中选择要投资的卡片，提交后输入账号支付密码，即可完成用奥斯卡投资理财产品。");
        descs.append(3, "理财金券是本平台发行的理财道具，使用后相应金额即可转变成钱包收益。每一张理财金券都有使用条件，需要单笔投资金额达到使用限额条件，在进行理财投资操作时选择使用，并且同一笔理财最多只能使用一张理财金券。理财金券有有效期设定，过期后会自动消失。");
        descs.append(4, "使用奥斯卡资金进行理财投资的资金项目，在项目到期后，本金和收益会开返还投资人，其中本金部分返回原卡（奥斯卡），收益部分进入到用户账号的钱包资金，可以用于消费、再投资或提现使用。");
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
