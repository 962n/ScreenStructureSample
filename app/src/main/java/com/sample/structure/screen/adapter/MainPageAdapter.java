package com.sample.structure.screen.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.sample.structure.screen.fragment.DummyFragment;
import com.sample.structure.screen.fragment.SwitchingFragment;

/**
 * MainActityのページアダプター
 */
public class MainPageAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_ITEMS = 4;

    public MainPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i) {
            case 0:
                fragment = new SwitchingFragment();
                break;
            case 1:
                fragment = new DummyFragment();
                break;
            case 2:
                fragment = new DummyFragment();
                break;
            case 3:
                fragment = new DummyFragment();
                break;
            default:
                fragment = new DummyFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title;
        switch (position) {
            case 0:
                title = "tab1";
                break;
            case 1:
                title = "tab2";
                break;
            case 2:
                title = "tab3";
                break;
            case 3:
                title = "tab4";
                break;
            default:
                title = "tab1";
                break;
        }
        return title;
    }

    /**
     * ViewPagerに設定されているFragmentを取得します
     * @param viewPager 本アダプタークラスを設定しているViewPagerクラス
     * @param position  ViewPagerに設定されている位置
     * @return          positionに設定されたフラグメントのインスタンス
     */
    public Fragment findFragmentByPosition(ViewPager viewPager,
                                           int position) {
        return (Fragment) instantiateItem(viewPager, position);
    }

}
