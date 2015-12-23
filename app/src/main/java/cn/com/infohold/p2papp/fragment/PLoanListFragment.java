package cn.com.infohold.p2papp.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.activity.BaseActivity;
import cn.com.infohold.p2papp.activity.PProjectDetailActivity;
import cn.com.infohold.p2papp.bean.LoanProjectBean;
import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PLoanListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PLoanListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "status";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Integer status;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ListView loanList;
    private EBaseAdapter baseAdapter;
    private List<LoanProjectBean> investProjectBeans;

    public PLoanListFragment() {
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
    public static PLoanListFragment newInstance(Integer param1, String param2) {
        PLoanListFragment fragment = new PLoanListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        investProjectBeans = new ArrayList<LoanProjectBean>();
        baseAdapter = new EBaseAdapter(getActivity(), investProjectBeans, R.layout.p_loan_project_item,
                new String[]{"preYield", "investableMoney", "limit"},
                new int[]{R.id.loanRates, R.id.loanMoney, R.id.loanLimit});
        loanList.setAdapter(baseAdapter);
        loanList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("status", 2);
                ((BaseActivity) getActivity()).toActivity(PProjectDetailActivity.class, bundle);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

        loanList = (ListView) view.findViewById(R.id.loanList);
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
