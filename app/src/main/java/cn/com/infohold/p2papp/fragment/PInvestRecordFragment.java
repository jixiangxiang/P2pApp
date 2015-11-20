package cn.com.infohold.p2papp.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.activity.PProjectDetailActivity;
import cn.com.infohold.p2papp.bean.InvestRecordBean;
import cn.com.infohold.p2papp.views.WrapScrollListView;
import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PInvestRecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PInvestRecordFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private WrapScrollListView investRecord;
    private List<InvestRecordBean> investRecordBeanList;
    private EBaseAdapter baseAdapter;

    public PInvestRecordFragment() {
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
    public static PInvestRecordFragment newInstance(String param1, String param2) {
        PInvestRecordFragment fragment = new PInvestRecordFragment();
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
        return inflater.inflate(R.layout.fragment_pinvest_record, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        investRecordBeanList = new ArrayList<InvestRecordBean>();
        investRecordBeanList.add(new InvestRecordBean("01", "PDNF1209389123", "2015-12-11", 12000.00));
        investRecordBeanList.add(new InvestRecordBean("02", "PDNF1209389234", "2015-12-11", 13000.00));
        investRecordBeanList.add(new InvestRecordBean("03", "PDNF1209389032", "2015-12-11", 2000.00));
        investRecordBeanList.add(new InvestRecordBean("04", "PDNF1209389642", "2015-12-11", 4000.00));
        investRecordBeanList.add(new InvestRecordBean("05", "PDNF1209389012", "2015-12-11", 5000.00));
        investRecordBeanList.add(new InvestRecordBean("06", "PDNF1209389012", "2015-12-11", 12000.00));
        investRecordBeanList.add(new InvestRecordBean("07", "PDNF1209389012", "2015-12-11", 12000.00));
        investRecordBeanList.add(new InvestRecordBean("08", "PDNF1209389012", "2015-12-11", 12000.00));
        investRecordBeanList.add(new InvestRecordBean("09", "PDNF1209389012", "2015-12-11", 12000.00));
        investRecordBeanList.add(new InvestRecordBean("010", "PDNF1209389012", "2015-12-11", 12000.00));
        investRecordBeanList.add(new InvestRecordBean("11", "PDNF1209389012", "2015-12-11", 12000.00));
        baseAdapter = new EBaseAdapter(getActivity(), investRecordBeanList, R.layout.invest_record_item,
                new String[]{"index", "projectName", "investDate", "investMoney"},
                new int[]{R.id.index, R.id.projectName, R.id.investDate, R.id.investMoney});
        baseAdapter.setViewBinder(new EBaseAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object o, String s) {
                if (view instanceof TextView && o instanceof Double) {
                    TextView tv = (TextView) view;
                    Double money = (Double) o;
                    tv.setText(money + "元");
                    return true;
                }
                return false;
            }
        });
        investRecord.setAdapter(baseAdapter);

        investRecord.post(new Runnable() {
            @Override
            public void run() {
                ((PProjectDetailActivity) getActivity()).setViewPagerHeight(investRecord.getHeight());
            }
        });
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initialize(View view) {
        investRecord = (WrapScrollListView) view.findViewById(R.id.investRecord);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
