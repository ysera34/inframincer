package org.inframincer.lms.view;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.inframincer.lms.R;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GuideFragment.OnStartButtonClickListener {

    private static final String TAG = HomeActivity.class.getSimpleName();

    private FragmentManager mHomeFragmentManager;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_title));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mHomeFragmentManager = getSupportFragmentManager();
        mFragment = mHomeFragmentManager.findFragmentById(R.id.fragment_container);
        if (mFragment == null) {
            mHomeFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, GuideFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        mFragment = mHomeFragmentManager.findFragmentById(R.id.fragment_container);

        switch (item.getItemId()) {
            case R.id.nav_guide:
                if ((mFragment != null) && !(mFragment instanceof GuideFragment)) {
                    mHomeFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.fragment_container, GuideFragment.newInstance())
                            .commit();
                }
                break;
            case R.id.nav_form:
                if ((mFragment != null) && !(mFragment instanceof FormFragment)) {
                    mHomeFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                            .replace(R.id.fragment_container, FormFragment.newInstance())
                            .commit();
                }
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStartButtonClick() {
        mFragment = mHomeFragmentManager.findFragmentById(R.id.fragment_container);
        if ((mFragment != null) && !(mFragment instanceof FormFragment)) {
            mHomeFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                    .replace(R.id.fragment_container, FormFragment.newInstance())
                    .commit();
        }
    }
}
