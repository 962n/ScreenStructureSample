package com.sample.structure.screen.activity;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.FrameLayout;

import com.sample.structure.android.view.SlidingTabLayout;
import com.sample.structure.screen.R;

import com.sample.structure.screen.adapter.MainPageAdapter;
import com.sample.structure.screen.fragment.NavigationDrawerFragment;
import com.sample.structure.screen.utils.DebugLog;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    //Pagerのタブ
    private SlidingTabLayout mSlidingTabLayout;

    private ViewPager mViewPager;

    private MainPageAdapter mMainPageAdapter;

    private Toolbar mActionBarToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("hogehgoe", "1");

        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        this.setSupportActionBar(mActionBarToolbar);
        Log.d("hogehgoe", "2");

        mNavigationDrawerFragment = new NavigationDrawerFragment();
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.navigation_drawer_layer, mNavigationDrawerFragment).
                commit();

//        mNavigationDrawerFragment = (NavigationDrawerFragment)
//                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        Log.d("hogehgoe", "mNavigationDrawerFragment = "+mNavigationDrawerFragment);
        mTitle = getTitle();
        Log.d("hogehgoe", "4");

        // Set up the drawer.
//        mNavigationDrawerFragment.setUp(
//                R.id.navigation_drawer_layer,
//                (DrawerLayout) findViewById(R.id.drawer_layout));

        mViewPager = (ViewPager) this.findViewById(R.id.viewpager);
        mMainPageAdapter = new MainPageAdapter(this.getSupportFragmentManager());
        mViewPager.setAdapter(mMainPageAdapter);
        mSlidingTabLayout = (SlidingTabLayout) this.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setCustomTabView(R.layout.slidingtab_item,R.id.tab_item_label);
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                int ColorList[] = { Color.RED,
                                    Color.YELLOW,
                                    Color.BLUE,
                                    Color.GREEN };
                int color = Color.BLACK;
                if(ColorList.length -1 >= position){
                    color = ColorList[position];
                }
                return color;
            }
            @Override
            public int getDividerColor(int position) {
                return Color.TRANSPARENT;
            }
        });
        mSlidingTabLayout.setViewPager(mViewPager);

        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                getSupportActionBar().setTitle(mMainPageAdapter.getPageTitle(i));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mSlidingTabLayout.setOnCurrentTabItemClickListener(new SlidingTabLayout.OnCurrentTabItemClickListener() {
            @Override
            public void onCurrentTabItemClick() {

                DebugLog.d(TAG, "onCurrentTabItemClick");
                //現在表示中のフラグメントを取得する
                Fragment fragment = mMainPageAdapter.findFragmentByPosition(mViewPager,mViewPager.getCurrentItem());
                if(fragment instanceof SlidingTabLayout.OnCurrentTabItemClickListener){
                    DebugLog.d(TAG, "find current fragment");
                    ((SlidingTabLayout.OnCurrentTabItemClickListener)fragment).onCurrentTabItemClick();
                }


            }
        });
        getSupportActionBar().setTitle(mMainPageAdapter.getPageTitle(0));

    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
//                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
//            getMenuInflater().inflate(R.menu.main, menu);
//            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    @Override
    public void onNavigationDrawerClosed() {
        getSupportActionBar().setTitle(mMainPageAdapter.getPageTitle(mViewPager.getCurrentItem()));
    }
}
