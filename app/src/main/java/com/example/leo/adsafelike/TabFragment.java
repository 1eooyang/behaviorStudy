package com.example.leo.adsafelike;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created leoyang  on 2017/4/21.
 */
public class TabFragment extends Fragment {

    public static final String FRAG_KEY = "FragKey";


    RecyclerView mRvList;

    private ListAdapter mAdapter;


    public static TabFragment newInstance(String title) {
        TabFragment fragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FRAG_KEY, title);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, null);
        mRvList = (RecyclerView) view.findViewById(R.id.rv_list);
        ViewHelper.initRecyclerView(getContext(), mRvList, true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            String title = getArguments().getString(FRAG_KEY);
            String[] titles = new String[100];
            for (int i = 0; i < 100; i++) {
                titles[i] = title + " " + i;
            }
            mAdapter = new ListAdapter(getContext(), titles);
            mRvList.setAdapter(mAdapter);
        }
    }
}
