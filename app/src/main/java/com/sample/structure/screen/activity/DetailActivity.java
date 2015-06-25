package com.sample.structure.screen.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.sample.structure.screen.R;
import com.sample.structure.screen.fragment.DetailPagerFragment;


public class DetailActivity extends ActionBarActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null){
            Fragment fragment = new DetailPagerFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(DetailPagerFragment.KEY_PAGE_POSITION,getIntent().getIntExtra(DetailPagerFragment.KEY_PAGE_POSITION,0));
            fragment.setArguments(bundle);

            this.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container,fragment)
                    .commit();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_to_front, R.anim.exit_to_right);
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }
}
