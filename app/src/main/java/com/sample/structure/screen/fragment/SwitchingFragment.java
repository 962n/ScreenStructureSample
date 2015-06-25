package com.sample.structure.screen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sample.structure.android.view.SlidingTabLayout;
import com.sample.structure.android.view.SlidingTabLayout.OnCurrentTabItemClickListener;
import com.sample.structure.screen.R;
import com.sample.structure.screen.utils.DebugLog;

/**
 * 子フラグメントを回転アニメーションで切り替えるためだけのフラグメント
 */
public class SwitchingFragment extends Fragment implements OnCurrentTabItemClickListener{

    private static final String TAG = SwitchingFragment.class.getSimpleName();

    private static final String FRAGMENT_TAG = "SwitchingFragment";

    private Fragment mCurrentFragment ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_switching, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        FragmentManager fragmentManager = this.getChildFragmentManager();
        if(savedInstanceState == null){
            mCurrentFragment = new GridFragment();
            fragmentManager.beginTransaction()
                           .replace(R.id.container,mCurrentFragment,FRAGMENT_TAG)
                           .commit();
        } else {
            mCurrentFragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.switch_menu,menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //TODO 切り替え処理を実装する
        if(item.getItemId() == R.id.action_switch){
            FragmentManager fragmentManager = this.getChildFragmentManager();

            Fragment currentFragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG);

            Fragment changeFragment;
            if(currentFragment instanceof GridFragment){
                changeFragment = new ListFragment();
            } else {
                changeFragment = new GridFragment();
            }
            mCurrentFragment = changeFragment;
            fragmentManager.beginTransaction()
//                    .setCustomAnimations(R.anim.enter_to_front,R.anim.exit_to_bottom)
                    .setCustomAnimations(R.anim.enter_scale_up, R.anim.exit_scale_down)
                    .replace(R.id.container, changeFragment, FRAGMENT_TAG)
                    .commit();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCurrentTabItemClick() {
        DebugLog.d(TAG, "onCurrentTabItemClick");
        if(mCurrentFragment instanceof OnCurrentTabItemClickListener){
            DebugLog.d(TAG, "find current fragment");
            ((SlidingTabLayout.OnCurrentTabItemClickListener)mCurrentFragment).onCurrentTabItemClick();
        }
    }
}
