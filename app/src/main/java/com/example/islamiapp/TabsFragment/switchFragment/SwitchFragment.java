package com.example.islamiapp.TabsFragment.switchFragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.islamiapp.Base.BaseFragment;
import com.example.islamiapp.R;
import com.example.islamiapp.TabsFragment.switchFragment.ahadeth.QuotesFragment;
import com.example.islamiapp.TabsFragment.switchFragment.azkar.AzkarFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class SwitchFragment extends BaseFragment {

    protected SmartTabLayout tabsTitle;
    protected ViewPager viewPager;
    View view;
    FragmentPagerItemAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_switch, container, false);
        initView(view);
        prepareTabsLayout();

        return view;
    }

    public void prepareTabsLayout() {
        adapter = new FragmentPagerItemAdapter(getChildFragmentManager(),
                FragmentPagerItems.with(getContext())
                        .add(R.string.title_quotes, QuotesFragment.class)
                        .add(R.string.title_azkar, AzkarFragment.class)
                        .create());


        viewPager.setAdapter(adapter);
        tabsTitle
                .setViewPager(viewPager);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                viewPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    private void initView(View rootView) {
        tabsTitle = (SmartTabLayout) rootView.findViewById(R.id.tabs_title);
        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
    }
}