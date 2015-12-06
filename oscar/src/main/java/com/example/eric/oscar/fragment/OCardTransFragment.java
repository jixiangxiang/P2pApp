package com.example.eric.oscar.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.CardBean;
import com.example.eric.oscar.views.WrapScrollListView;

import java.util.ArrayList;
import java.util.List;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OCardTransFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OCardTransFragment extends BaseFragment implements View.OnClickListener {
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
    private List<CardBean> cardBeans;

    public OCardTransFragment() {
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
    public static OCardTransFragment newInstance(String param1, String param2) {
        OCardTransFragment fragment = new OCardTransFragment();
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
        return inflater.inflate(R.layout.fragment_ocard_trans, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        cardBeans = new ArrayList<>();
        cardBeans.add(new CardBean(50.00, 1));
        cardBeans.add(new CardBean(100.00, 1));
        cardBeans.add(new CardBean(200.00, 1));
        cardBeans.add(new CardBean(300.00, 1));
        cardBeans.add(new CardBean(500.00, 1));
        cardBeans.add(new CardBean(1000.00, 1));
        baseAdapter = new EBaseAdapter(getActivity(), cardBeans, R.layout.list_card_bar_item,
                new String[]{"bar", "count"}, new int[]{R.id.bar, R.id.count});
        cardBarList.setAdapter(baseAdapter);

    }

    @Override
    public void onClick(View v) {
    }

    private void initialize(View view) {
        cardIcon = (ImageView) view.findViewById(R.id.cardIcon);
        cardBarList = (WrapScrollListView) view.findViewById(R.id.cardBarList);
        totalMoney = (EditText) view.findViewById(R.id.totalMoney);
        transBtn = (Button) view.findViewById(R.id.transBtn);
    }
}
