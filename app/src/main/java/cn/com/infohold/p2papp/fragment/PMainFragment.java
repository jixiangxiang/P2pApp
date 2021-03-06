package cn.com.infohold.p2papp.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.activity.BaseActivity;
import cn.com.infohold.p2papp.adapter.FunctionGridAdapter;
import cn.com.infohold.p2papp.adapter.ImagePagerAdapter;
import cn.com.infohold.p2papp.base.BaseFragment;
import cn.com.infohold.p2papp.enums.FunctionEnum;
import cn.com.infohold.p2papp.views.DotLayout;
import cn.com.infohold.p2papp.views.WrapScrollGridView;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link PMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PMainFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView seconds;
    private LinearLayout recommended;
    private ScrollView content;
    private ImageView descriptImg;
    private AutoScrollViewPager bannerPager;
    private WrapScrollGridView functionGrid;
    private TextView yield;
    private TextView hour;
    private TextView minute;
    private FunctionGridAdapter functionGridAdapter;
    private DotLayout dotLayout;

    private List<Integer> imageIdList;

    public PMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PMainFragment newInstance(String param1, String param2) {
        PMainFragment fragment = new PMainFragment();
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
        return inflater.inflate(R.layout.fragment_pmain, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        functionGridAdapter = new FunctionGridAdapter(getActivity(), FunctionEnum.values());
        functionGrid.setAdapter(functionGridAdapter);
        functionGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class toClass = FunctionEnum.values()[position].getFunctionClass();
                if (toClass != null) {
                    ((BaseActivity) getActivity()).toActivity(toClass);
                }
            }
        });

        imageIdList = new ArrayList<Integer>();
        imageIdList.add(R.mipmap.banner_1);
        imageIdList.add(R.mipmap.banner_1);
        imageIdList.add(R.mipmap.banner_1);
        imageIdList.add(R.mipmap.banner_1);
        bannerPager.setAdapter(new ImagePagerAdapter(getActivity(), imageIdList).setInfiniteLoop(true));
        dotLayout.setSize(imageIdList.size());
        bannerPager.setInterval(2000);
        bannerPager.startAutoScroll();
        bannerPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % imageIdList.size());
        bannerPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dotLayout.selectChildViewByIndex(position % imageIdList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initialize(View view) {
        seconds = (TextView) view.findViewById(R.id.seconds);
        recommended = (LinearLayout) view.findViewById(R.id.recommended);
        content = (ScrollView) view.findViewById(R.id.content);
        descriptImg = (ImageView) view.findViewById(R.id.descriptImg);
        bannerPager = (AutoScrollViewPager) view.findViewById(R.id.bannerPager);
        functionGrid = (WrapScrollGridView) view.findViewById(R.id.functionGrid);
        yield = (TextView) view.findViewById(R.id.yield);
        hour = (TextView) view.findViewById(R.id.hour);
        minute = (TextView) view.findViewById(R.id.minute);
        dotLayout = (DotLayout) view.findViewById(R.id.dotLayout);
    }

    @Override
    public void onPause() {
        super.onPause();
        // stop auto scroll when onPause
        bannerPager.stopAutoScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        // start auto scroll when onResume
        bannerPager.startAutoScroll();
    }
}
