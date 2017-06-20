package com.example.leo.adsafelike;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;

    private boolean checkState;
    private Button mViewById;
    private ImageView im_logo_big;
    private ArrayList<Fragment> mObjects;
    private ViewPagerAdapter mPagerAdapter;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private TextView mTextView;
    private Button mButton;
    private ImageView logo;
    private CollapsingToolbarLayoutState state;

    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScaleUtils.scaleViewAndChildren(findViewById(R.id.activity_main), ScreenUtils.getRealScale(this), 0);
        initCollTollbar();
        mViewById = (Button) findViewById(R.id.btn);
        im_logo_big = (ImageView) findViewById(R.id.im_logo_big);
        mViewById.setSelected(checkState);
        mViewById.setText(checkState?"净网模式已开启":"点击开启净网模式");
        mViewById.setTextColor(getResources().getColor(checkState ?  R.color.nomal_write:R.color.nomal_color_bg ));
        im_logo_big.setSelected(checkState);
        mButton.setSelected(checkState);
        mViewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkState = !checkState;
                mViewById.setSelected(checkState);
                im_logo_big.setSelected(checkState);
                mButton.setSelected(checkState);
                mViewById.setText(checkState?"净网模式已开启":"点击开启净网模式");
                mViewById.setTextColor(getResources().getColor(checkState ?  R.color.nomal_write:R.color.nomal_color_bg ));
            }
        });
        mViewPager = (ViewPager) findViewById(R.id.vp);
        _initTabLayout();

        initListener();


    }

    private void initListener() {

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED;//标记为展开

                        System.out.println("leoyang 展开");
                        setToolBarVisable(false);

                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                        System.out.println("leoyang 折叠");

                        setToolBarVisable(true);

                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        if(state == CollapsingToolbarLayoutState.COLLAPSED){
                            System.out.println("leoyang 从折叠到中间");
                            setToolBarVisable(false);
                        }
                        System.out.println("leoyang 中间");

                        state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                    }
                }



            }
        });

    }









    private void initCollTollbar() {
        mAppBarLayout = (AppBarLayout) findViewById(R.id.main_appbar);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mTextView = (TextView) findViewById(R.id.main_toolbar_text);
        mButton = (Button) findViewById(R.id.main_toolbar_btn);
        logo = (ImageView) findViewById(R.id.main_toolbar_logo);
    }

    private void setToolBarVisable(boolean visable) {
        logo.setVisibility(visable ? View.VISIBLE : View.INVISIBLE);
        mButton.setVisibility(visable ? View.VISIBLE : View.INVISIBLE);
        mTextView.setVisibility(visable ? View.VISIBLE : View.INVISIBLE);
    }


    private void _initTabLayout() {
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        titles.add("One");
        titles.add("Two");
        titles.add("Three");
        for (String title : titles) {
            fragments.add(TabFragment.newInstance(title));
        }

        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.setFragments(fragments);
        mPagerAdapter.setTitles(titles);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
    }


}
