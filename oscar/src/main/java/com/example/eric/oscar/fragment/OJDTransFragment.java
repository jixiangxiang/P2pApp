package com.example.eric.oscar.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.activity.OTransListActivity;
import com.example.eric.oscar.bean.JdCardBean;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.SPUtils;
import com.example.eric.oscar.views.WrapScrollListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OJDTransFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OJDTransFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView cardIcon;
    private WrapScrollListView cardBarList;
    private EBaseAdapter baseAdapter;
    private EditText totalMoney;
    private Button transBtn;
    private List<JdCardBean> cardBeans = new ArrayList<>();
    private NumberPicker numberPicker;
    private AlertDialog numberAlert;
    private int position = 0;

    private StringRequest request;

    public OJDTransFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OSelfFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OJDTransFragment newInstance(String param1, String param2) {
        OJDTransFragment fragment = new OJDTransFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ojd_trans, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        baseAdapter = new EBaseAdapter(getActivity(), cardBeans, R.layout.list_jd_card_bar_item,
                new String[]{"cardFace", "selectCount"}, new int[]{R.id.cardFace, R.id.cardCount});
        cardBarList.setAdapter(baseAdapter);
        cardBarList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OJDTransFragment.this.position = position;
                initAlertDialog(cardBeans.get(position).getCardCount());
            }
        });
        transBtn.setOnClickListener(this);
        request = new StringRequest(Request.Method.POST, ApiUtils.JDCARD, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("sign", SPUtils.getString(getActivity(), "sign"));
                return map;
            }
        };
        addToRequestQueue(request, ApiUtils.JDCARD, true);
    }

    @Override
    public void onClick(View v) {
        if (v == transBtn) {
            Double money = Double.valueOf(totalMoney.getText().toString());
            if (money > 1000 || money == 0) {
                ((BaseActivity) getActivity()).showToastShort("兑换金额不能为0且不能超过1000");
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("cardBeans", JSONArray.toJSONString(cardBeans));
            bundle.putString("totalMoney", totalMoney.getText().toString());
            bundle.putInt("icon", R.mipmap.o_jd_icon);
            bundle.putInt("status", 1);
            bundle.putString("type", "JD");
            ((BaseActivity) getActivity()).toActivity(OTransListActivity.class, bundle);
        }
    }

    private void initialize(View view) {
        cardIcon = (ImageView) view.findViewById(R.id.cardIcon);
        cardBarList = (WrapScrollListView) view.findViewById(R.id.cardBarList);
        totalMoney = (EditText) view.findViewById(R.id.totalMoney);
        transBtn = (Button) view.findViewById(R.id.transBtn);

        numberPicker = new NumberPicker(getActivity());
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(100);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        numberAlert = new AlertDialog.Builder(getActivity())
                .setMessage("请选择张数").setCancelable(true)
                .setView(numberPicker)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cardBeans.get(position).setSelectCount(numberPicker.getValue());
                        baseAdapter.setmData(cardBeans);
                        baseAdapter.notifyDataSetChanged();
                        Double money = 0.0;
                        for (JdCardBean cardBean : cardBeans) {
                            money = money + cardBean.getCardFace() * cardBean.getSelectCount();
                        }
                        totalMoney.setText(String.valueOf(money));
                    }
                }).create();

    }

    private void initAlertDialog(int maxvalue) {
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(maxvalue);
        if (numberAlert != null && numberAlert.isShowing()) {
            numberAlert.dismiss();
        }
        Window window = numberAlert.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialog_animations);
        numberAlert.show();
    }

    @Override
    protected void doResponse(ResponseResult response) {
        cardBeans = JSONArray.parseArray(((JSONArray) response.getData()).toJSONString(), JdCardBean.class);
        baseAdapter.setmData(cardBeans);
        baseAdapter.notifyDataSetChanged();
    }
}
