package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.bean.InvestProjectBean;
import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class PInvestProjectActivity extends BaseActivity implements View.OnClickListener {

    private ImageView newInvest;
    private TextView colligate;
    private TextView yieldText;
    private LinearLayout yieldArea;
    private TextView limitText;
    private LinearLayout limitArea;
    private LinearLayout sortArea;
    private ListView investProjectList;

    private EBaseAdapter baseAdapter;
    private List<InvestProjectBean> investProjectBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinvest_project);
    }

    @Override
    protected void initView() {
        getToolbar().setBackgroundResource(R.mipmap.p_invest_list_title_bg);
        getToolbar().setNavigationIcon(R.mipmap.p_back);
        initTitleText(getString(R.string.title_activity_pinvest_project), BaseActivity.TITLE_CENTER);
        initialize();
        investProjectBeans = new ArrayList<InvestProjectBean>();
        investProjectBeans.add(new InvestProjectBean(9.00, 1000.00, 360, 1));
        investProjectBeans.add(new InvestProjectBean(9.00, 1000.00, 360, 1));
        investProjectBeans.add(new InvestProjectBean(9.00, 1000.00, 360, 1));
        investProjectBeans.add(new InvestProjectBean(9.00, 1000.00, 360, 1));
        investProjectBeans.add(new InvestProjectBean(9.00, 1000.00, 360, 1));
        investProjectBeans.add(new InvestProjectBean(9.00, 1000.00, 360, 0));
        investProjectBeans.add(new InvestProjectBean(9.00, 1000.00, 360, 0));
        investProjectBeans.add(new InvestProjectBean(9.00, 1000.00, 360, 0));
        investProjectBeans.add(new InvestProjectBean(9.00, 1000.00, 360, 0));
        investProjectBeans.add(new InvestProjectBean(9.00, 1000.00, 360, 0));
        investProjectBeans.add(new InvestProjectBean(9.00, 1000.00, 360, 0));
        baseAdapter = new EBaseAdapter(this, investProjectBeans, R.layout.p_invest_project_item,
                new String[]{"preYield", "investableMoney", "limit", "status"},
                new int[]{R.id.preIncome, R.id.investableMoney, R.id.loanLimit, R.id.investBtn});
        baseAdapter.setViewBinder(new EBaseAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object o, String s) {
                if (view instanceof ImageView && o instanceof Integer) {
                    ImageView iv = (ImageView) view;
                    Integer status = (Integer) o;
                    switch (status) {
                        case 0:
                            iv.setImageResource(R.mipmap.p_loaning_btn);
                            break;
                        case 1:
                            iv.setImageResource(R.mipmap.p_invest_btn);
                            break;
                    }
                    return true;
                }
                return false;
            }
        });
        investProjectList.setAdapter(baseAdapter);

        investProjectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toActivity(PProjectDetailActivity.class);
            }
        });
    }

    private void initialize() {
        newInvest = (ImageView) findViewById(R.id.newInvest);
        colligate = (TextView) findViewById(R.id.colligate);
        yieldText = (TextView) findViewById(R.id.yieldText);
        yieldArea = (LinearLayout) findViewById(R.id.yieldArea);
        limitText = (TextView) findViewById(R.id.limitText);
        limitArea = (LinearLayout) findViewById(R.id.limitArea);
        sortArea = (LinearLayout) findViewById(R.id.sortArea);
        investProjectList = (ListView) findViewById(R.id.investProjectList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}
