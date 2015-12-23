package cn.com.infohold.p2papp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.activity.BaseActivity;
import cn.com.infohold.p2papp.activity.PProjectDetailActivity;
import cn.com.infohold.p2papp.base.BaseFragment;
import cn.com.infohold.p2papp.bean.TransFerringBean;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PTransListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PTransListFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "status";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Integer status;
    private String mParam2;

    private ListView loanList;
    private EBaseAdapter baseAdapter;
    private List<TransFerringBean> investProjectBeans;
    private int offset = 0;
    private int qrsize = 30;
    private boolean isOnCreate = false;

    public PTransListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PProjectDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PTransListFragment newInstance(Integer param1, String param2) {
        PTransListFragment fragment = new PTransListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isOnCreate = true;
        if (getArguments() != null) {
            status = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ploan_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        investProjectBeans = new ArrayList<TransFerringBean>();
        baseAdapter = new EBaseAdapter(getActivity(), investProjectBeans, R.layout.p_loan_project_item,
                new String[]{"preYield", "investableMoney", "limit"},
                new int[]{R.id.loanRates, R.id.loanMoney, R.id.loanLimit});
        loanList.setAdapter(baseAdapter);
        loanList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("status", 3);
                ((BaseActivity) getActivity()).toActivity(PProjectDetailActivity.class, bundle);
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                offset = 0;
                params = new HashMap<>();
                if (status == 2) {
                    params.put("transfer_status", "01");
                } else if (status == 3) {
                    params.put("transfer_status", "02");
                }
                params.put("mobilephone", ApiUtils.getLoginUserPhone(getActivity()));
                params.put("offset", String.valueOf(offset));
                params.put("qrsize", String.valueOf(qrsize));
                addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(PTransListFragment.this, params, ApiUtils.TRANSFERRING_ED), false);
            }
        });

    }

    private void initialize(View view) {
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        loanList = (ListView) view.findViewById(R.id.loanList);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        isCreated = false;
        JSONObject data = response.getData();
        int itemCount = (data.getInteger("total_count") - offset * qrsize);
        if (itemCount > 1) {
            if (offset == 0)
                investProjectBeans = JSONArray.parseArray(data.getJSONObject("detail").getJSONArray("stage").toJSONString(), TransFerringBean.class);
            else
                investProjectBeans.addAll(JSONArray.parseArray(data.getJSONObject("detail").getJSONArray("stage").toJSONString(), TransFerringBean.class));
        } else if (itemCount == 1) {
            if (offset == 0) {
                investProjectBeans = new ArrayList<>();
            }
            investProjectBeans.add(JSONObject.parseObject(data.getJSONObject("detail").getJSONObject("stage").toJSONString(), TransFerringBean.class));
        }
        baseAdapter.setmData(investProjectBeans);
        baseAdapter.notifyDataSetChanged();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isOnCreate) {
            params = new HashMap<>();
            if (status == 2) {
                params.put("transfer_status", "01");
            } else if (status == 3) {
                params.put("transfer_status", "02");
            }
            params.put("mobilephone", ApiUtils.getLoginUserPhone(getActivity()));
            params.put("offset", String.valueOf(offset));
            params.put("qrsize", String.valueOf(qrsize));
            addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.TRANSFERRING_ED), true);
        }
    }
}
